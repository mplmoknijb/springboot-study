package cn.leon.rocket.producer;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class SelfProducer implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

//    @Autowired
//    private ExtRocketTemplate extRocketTemplate;

    @Autowired
    private StreamBridge streamBridge;


    final static String destination = "test-producer-group";

    public void syncSend(RocketMessage rocketMessage) {
        Message<RocketMessage> build = MessageBuilder.withPayload(rocketMessage).build();
        rocketMQTemplate.syncSend(RocketConfig.TOPIC, build);
    }

    public void batchSend(List<Integer> rocketMessageList) {
        List<Message> messages = new ArrayList(rocketMessageList.size());
        rocketMessageList.forEach(rocketMessage -> {
            messages.add(MessageBuilder.withPayload(rocketMessageList).build());
        });
        rocketMQTemplate.syncSend(RocketConfig.TOPIC, messages, 1400);
    }

    /**
     * delay
     *
     * @param id
     * @param delayLevel
     */
    public SendResult syncSendDelay(Integer id, int delayLevel) {
        return rocketMQTemplate.syncSend(RocketConfig.TOPIC, MessageBuilder.withPayload(id).build(), 1400, delayLevel);
    }

    public void asyncSendDelay(Integer id, int delayLevel, SendCallback sendCallback) {
//        rocketMQTemplate.asyncSend(RocketConfig.TOPIC, MessageBuilder.withPayload(id).build(), sendCallback, 1400, delayLevel);
    }

    /**
     * 事务
     */
    public TransactionSendResult transactionSend(RocketMessage rocketMessage) {
        Message<RocketMessage> message = MessageBuilder.withPayload(rocketMessage).build();
//        return rocketMQTemplate.sendMessageInTransaction(destination,message, RocketConfig.TOPIC);
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(rocketMQTemplate.getProducer().getNamesrvAddr());
//        syncSendDelay(1,1);

//        transactionSend(RocketMessage.builder().id(1).build());
        streamBridge.send("send-in-0","test");
    }

    @Bean
        //这里接收rabbitmq的条件是参数为Consumer 并且 方法名和supplier方法名相同
        //这里的返回值是一个匿名函数 返回类型是consumer 类型和提供者的类型一致
        //supplier发送的exchange是 send-in-0 这里只需要用send方法名即可
    Consumer<String> send() {

        return str -> {

            System.out.println("我是消费者，我收到了消息："+str);
        };
    }

    @RocketMQTransactionListener
    public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // ... local transaction process, return rollback, commit or unknown
            log.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            // ... check transaction status and return rollback, commit or unknown
            log.info("[checkLocalTransaction][回查消息：{}]", msg);
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

}
