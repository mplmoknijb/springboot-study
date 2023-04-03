package cn.leon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class Config {

    /**
     * 消费分布式事务消息
     */
    @Bean
    public Consumer<String> order() {
        return order -> {
            log.info("接收的普通消息为：{}", order);
        };
    }
}
