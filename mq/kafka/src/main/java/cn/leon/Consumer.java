package cn.leon;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @date 2019-4-25 0025
 **/
@Slf4j
//@EnableBinding(DataSink.class)
public class Consumer {

//
//    @StreamListener(DataSink.TEST)
//    public void receiveMethod(Message<String> message) throws Exception {
////        log.debug(message.getPayload());
////        throw new RuntimeException("消费失败");
////        channel.basicReject(deliveryTag, false);
//        System.out.println(message);
//
//    }
//    @KafkaListener(topics = {"test"})
//    public void listen(ConsumerRecord<?, ?> record) {
//
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//
//            Object message = kafkaMessage.get();
//            System.out.println("---->这是一个消费" + record);
//            System.out.println("---->" + message);
//
//        }
//
//    }

}

