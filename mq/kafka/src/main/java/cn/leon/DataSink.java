//package cn.leon;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//
//public interface DataSink {
//
//    String TEST = "test";
//    String TEST_OUT = "test-out";
//
//    @Input(TEST)
//    SubscribableChannel testInput();
//
//    @Output(TEST)
//    MessageChannel testOutput();
//
//}
