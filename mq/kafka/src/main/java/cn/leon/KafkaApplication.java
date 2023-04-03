package cn.leon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private StreamBridge streamBridge;

    @Override
    public void run(String... args) throws Exception {
        try {
            boolean send_ok = streamBridge.send("order-out-0", "this is a test msg");
            if (send_ok) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("下单成功, 发消息异常: ", e.getCause());
        }
        throw new RuntimeException("消息未发送成功");
//        producerService.send("test message");
//        for (int i = 0; i < 10; i++) {
//            MessageForm form = new MessageForm();
//            form.setTopic("test");
//            form.setContent("----------》test");
//            producerService.send("----------》test");
//        }
    }
}
