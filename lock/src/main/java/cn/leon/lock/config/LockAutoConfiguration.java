package cn.leon.lock.config;

import cn.leon.lock.model.LockProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnProperty(value = "lock.enabled",havingValue = "true")
public class LockAutoConfiguration {




}
