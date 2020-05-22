package cn.leon.lock.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties()
@ConditionalOnProperty(value = "lock.enabled",havingValue = "true")
public class LockAutoConfiguration {



}
