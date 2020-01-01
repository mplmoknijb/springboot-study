package cn.leon;

import cn.leon.costumer.Receiver;
import cn.leon.producer.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {
    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Test
    public void send() throws Exception {
        sender.send();
    }
}
