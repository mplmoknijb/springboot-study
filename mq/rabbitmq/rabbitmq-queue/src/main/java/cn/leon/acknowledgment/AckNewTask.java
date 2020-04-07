package cn.leon.acknowledgment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckNewTask {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("localhost");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 开启持久化
        boolean durable = true;
        // todo 1.指定一个队列,开启持久化
        /**
         * <P>注: 已经存在的队列，我们无法修改其属性</P>
         * 必须发送端和接收端同时开启持久化
         */
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        // 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "leon:" + i;
            // todo 2.将消息标记为持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}