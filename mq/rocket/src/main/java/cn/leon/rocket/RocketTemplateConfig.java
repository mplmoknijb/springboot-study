package cn.leon.rocket;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketTemplateConfig {

    @Bean
    public RocketMQTemplate rocketMQTemplate(){
        return new RocketMQTemplate();
    }
}
