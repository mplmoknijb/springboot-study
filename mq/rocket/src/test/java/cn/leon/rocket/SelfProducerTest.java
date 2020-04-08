package cn.leon.rocket;

import cn.leon.rocket.producer.SelfProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketApplication.class)
class SelfProducerTest {

    @Autowired
    private SelfProducer selfProducer;

    @Test
    public void sendTest() {
        selfProducer.syncSend(new RocketMessage(1));
    }

    @Test
    void batchSend() {
        selfProducer.batchSend(new ArrayList(3) {{
            add(1);
            add(2);
            add(3);
        }});
    }

    @Test
    void syncSendDelay() {
        for (int i = 1; i < 19; i++) {
            selfProducer.syncSendDelay(1, i);
        }
    }

    @Test
    void asyncSendDelay() {
        for (int i = 1; i < 19; i++) {
            selfProducer.syncSendDelay(1, i);
        }

    }

    @Test
    void transactionSend() {
//        selfProducer.transactionSend(new RocketMessage(1));
    }
}