//package cn.leon;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.messaging.support.MessageBuilder;
//
//@Slf4j
////@EnableBinding(DataSink.class)
//public class Producer {
//
//    @Autowired
//    private DataSink dataSink;
//
//    public void send(String message) {
//        //该data可以为对象也可以为字符串
//        dataSink.testOutput().send(MessageBuilder.withPayload(message).build());
//        for (int i = 0; i < 1000; i++) {
//            log.info("======================================Test============================");
//        }
//    }
//}
