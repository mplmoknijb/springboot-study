package cn.leon.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("localhost");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        /**
         * <p>
         *     queueDeclare(),创建一个随机队列名称非持久的、唯一的、自动删除的队列，且队列名称由服务器随机产生
         * </p>
         *
         */
        String QUEUE_NAME = channel.queueDeclare().getQueue();
        // 交换器一共四种类型：direct、topic、headers、fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // todo 发送消息
        String message = "It's Sent By leon again";
        // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        // 参数1 exchange ：交换器
        // 参数2 routingKey ： 路由键
        // 参数3 props ： 消息的其他参数
        // 参数4 body ： 消息体
        channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
