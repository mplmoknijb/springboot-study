package cn.leon;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author mujian
 * @date 2019-4-25 0025
 **/
@Component
@Slf4j
public class Consumer {


    @KafkaListener(topics = {"test-consumer-group"})
    public void listen(ConsumerRecord<?, ?> record){

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            System.out.println("---->这是一个消费"+record);
            System.out.println("---->"+message);

        }

    }
}

