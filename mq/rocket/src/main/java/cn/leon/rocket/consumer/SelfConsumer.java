package cn.leon.rocket.consumer;

import cn.leon.rocket.RocketConfig;
import cn.leon.rocket.RocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketConfig.TOPIC,
        consumerGroup = "test-consumer-group" + RocketConfig.TOPIC)
public class SelfConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        log.info("ID ：{},消息：{}", Thread.currentThread().getId(), message);
    }
}
