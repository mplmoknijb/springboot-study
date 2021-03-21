package cn.leon.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.CharsetUtil;
import org.springframework.scheduling.annotation.Async;

import java.net.URI;

/**
 * http client test
 */
public class HttpClient {
    public static void newClient(String host, int port) {
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
                                    channel.pipeline().addLast(new ReadTimeoutHandler(1));
                                    channel.pipeline().addLast(new WriteTimeoutHandler(1));
                                    channel.pipeline().addLast(new HttpResponseDecoder());
                                    channel.pipeline().addLast(new HttpRequestEncoder());
                                    channel.pipeline().addLast(new HttpClientHandler());
                                }
                            });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class HttpClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            URI uri = new URI("/get");
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
            request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            ctx.writeAndFlush(request);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            System.out.println("msg -> " + msg);
            if (msg instanceof FullHttpResponse) {
                FullHttpResponse response = (FullHttpResponse) msg;
                ByteBuf buf = response.content();
                String result = buf.toString(CharsetUtil.UTF_8);
                System.out.println("response -> " + result);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
             new Thread(new Runnable() {
                @Override
                public void run() {
                    newClient("localhost", 8081);
                }
            }).start();
        }
    }
}
