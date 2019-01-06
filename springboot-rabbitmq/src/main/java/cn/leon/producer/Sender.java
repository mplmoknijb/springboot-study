package cn.leon.producer;

import cn.leon.configuration.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * <p>
     * 注入AmqpTemplate 接口的实例来实现消息的发送
     * </P>
     */
    public void send() {
        System.out.println("This is sent by leon");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, "Hello!");
    }
}
