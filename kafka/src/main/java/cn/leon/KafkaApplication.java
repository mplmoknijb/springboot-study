package cn.leon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    ProducerService service;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            MessageForm form = new MessageForm();
            form.setTopic("test");
            form.setContent("----------》test");
            service.sendMessage(form);
        }
    }
}
