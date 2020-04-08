package cn.leon.rocket.producer;

import cn.leon.rocket.ExtRocketTemplate;
import cn.leon.rocket.RocketConfig;
import cn.leon.rocket.RocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SelfProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ExtRocketTemplate extRocketTemplate;

    final static String destination = "test-producer-group";

    public void syncSend(RocketMessage rocketMessage) {
        Message<RocketMessage> build = MessageBuilder.withPayload(rocketMessage).build();
        extRocketTemplate.syncSend(RocketConfig.TOPIC, build);
    }

    public void batchSend(List<Integer> rocketMessageList) {
        List<Message> messages = new ArrayList(rocketMessageList.size());
        rocketMessageList.forEach(rocketMessage -> {
            messages.add(MessageBuilder.withPayload(rocketMessageList).build());
        });
        extRocketTemplate.syncSend(RocketConfig.TOPIC, messages, 1400);
    }

    /**
     * delay
     *
     * @param id
     * @param delayLevel
     */
    public SendResult syncSendDelay(Integer id, int delayLevel) {
        return extRocketTemplate.syncSend(RocketConfig.TOPIC, MessageBuilder.withPayload(id).build(), 1400, delayLevel);
    }

    public void asyncSendDelay(Integer id, int delayLevel, SendCallback sendCallback) {
        extRocketTemplate.asyncSend(RocketConfig.TOPIC, MessageBuilder.withPayload(id).build(), sendCallback, 1400, delayLevel);
    }
//
//    /**
//     * 事务
//     */
//    public TransactionSendResult transactionSend(RocketMessage rocketMessage) {
//        Message<RocketMessage> message = MessageBuilder.withPayload(rocketMessage).build();
//        return rocketMQTemplate.sendMessageInTransaction(destination, RocketConfig.TOPIC, message, rocketMessage);
//    }
//
//    @RocketMQTransactionListener(txProducerGroup = destination)
//    public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
//        @Override
//        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//            // ... local transaction process, return rollback, commit or unknown
//            log.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
//            return RocketMQLocalTransactionState.UNKNOWN;
//        }
//
//        @Override
//        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
//            // ... check transaction status and return rollback, commit or unknown
//            log.info("[checkLocalTransaction][回查消息：{}]", msg);
//            return RocketMQLocalTransactionState.COMMIT;
//        }
//    }

}
