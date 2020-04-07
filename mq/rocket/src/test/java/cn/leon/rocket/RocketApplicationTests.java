package cn.leon.rocket;

import cn.leon.rocket.producer.SelfProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RocketApplicationTests {
    @Autowired
    private SelfProducer selfProducer;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendTest() {

        selfProducer.syncSend(new RocketMessage(1));
    }
}
