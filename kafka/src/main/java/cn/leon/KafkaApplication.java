package cn.leon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private ProducerService service;

    @Override
    public void run(String... args) throws Exception {
        MessageForm form = new MessageForm();
        StringBuilder builder = new StringBuilder("==============================>111111111111111111");
        form.setTopic("test-consumer-group");
        for (int i = 0; i < 100; i++) {
            form.setContent(String.valueOf(builder.append(i)));
            service.sendMessage(form);
        }
    }
}
