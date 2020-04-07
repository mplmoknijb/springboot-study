package cn.leon.rocket.producer;

import cn.leon.rocket.ExtRocketTemplate;
import cn.leon.rocket.RocketConfig;
import cn.leon.rocket.RocketMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SelfProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void syncSend(RocketMessage rocketMessage) {
        Message<RocketMessage> build = MessageBuilder.withPayload(rocketMessage).build();
        rocketMQTemplate.syncSend(RocketConfig.TOPIC, build);
    }


}
