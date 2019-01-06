package cn.leon.costumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
    /**
     * <p>
     *
     * @param message
     * @RabbitListener 注解定义对队列的监听
     * </P>
     */
    @RabbitListener(queues = "spring-boot-simple")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
