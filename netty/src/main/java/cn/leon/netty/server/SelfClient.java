package cn.leon.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class SelfClient {
    public static void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(

                            new ChannelInitializer<Channel>() {

                                @Override
                                protected void initChannel(Channel channel)
                                        throws Exception {
//                                    channel.pipeline().addLast(new HttpResponseDecoder());
//                                    channel.pipeline().addLast(new HttpRequestEncoder());
                                    channel.pipeline().addLast(new SelfClientHandler());
                                }
                            });

            ChannelFuture future = bootstrap.connect("localhost", 80).sync();
            future.channel().closeFuture().sync();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class SelfClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("测试", CharsetUtil.UTF_8));

        }
    }

    public static void main(String[] args) {
        start();
    }
}
