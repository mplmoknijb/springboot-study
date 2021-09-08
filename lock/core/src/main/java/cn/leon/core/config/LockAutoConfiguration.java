package cn.leon.core.config;

import cn.leon.core.model.LockProperties;
import cn.leon.core.model.SelfLock;
import cn.leon.core.model.SingleNodeProperties;
import cn.leon.core.model.ZkInfoProperties;
import cn.leon.core.strategy.LockStrategy;
import cn.leon.core.strategy.RedisLockStrategy;
import cn.leon.core.strategy.ZkLockStrategy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.ZooKeeper;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

@Configuration
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnProperty(value = "lock.enabled", havingValue = "true")
public class LockAutoConfiguration {
    private final LockProperties lockProperties;

    public LockAutoConfiguration(LockProperties lockProperties) {
        this.lockProperties = lockProperties;
    }

    @Configuration
    @EnableConfigurationProperties(ZkInfoProperties.class)
    @ConditionalOnProperty(value = "lock.type", havingValue = "zookeeper")
    @ConditionalOnClass({CuratorFrameworkFactory.class, InterProcessMutex.class, ZooKeeper.class})
    public class ZkAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean(LockStrategy.class)
        public LockStrategy lockStrategy(ZkInfoProperties zkInfoProperties) {
            return new ZkLockStrategy(zkInfoProperties);
        }

        @Bean
        @ConditionalOnMissingBean(SelfLock.class)
        public SelfLock<InterProcessMutex> selfLock(LockStrategy<InterProcessMutex> lockStrategy) {
            return new SelfLock<InterProcessMutex>(lockStrategy);
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "lock.type", havingValue = "redis")
    @ConditionalOnClass({Redisson.class, RedisOperations.class})
    @AutoConfigureBefore(RedissonAutoConfiguration.class)
    @AutoConfigureAfter(RedisAutoConfiguration.class)
    @EnableConfigurationProperties({SingleNodeProperties.class, RedisProperties.class, RedissonProperties.class})
    public class RedisAutoConfiguration {
        @Bean
        @ConditionalOnMissingBean(LockStrategy.class)
        public LockStrategy lockStrategy(SingleNodeProperties singleNodeProperties) {
            return new RedisLockStrategy(singleNodeProperties);
        }

        @Bean(destroyMethod = "shutdown")
        public RedissonClient redissonClient(LockStrategy<RLock> lockStrategy) {
            if (lockStrategy instanceof RedisLockStrategy) {
                return ((RedisLockStrategy) lockStrategy).getRedissonClient();
            }
            return null;
        }

        @Bean
        @ConditionalOnMissingBean(SelfLock.class)
        public SelfLock<RLock> selfLock(LockStrategy<RLock> lockStrategy) {
            return new SelfLock<RLock>(lockStrategy);
        }
    }

    @Bean
    public GlobalScanner GLobalScanner(SelfLock<?> selfLock) {
        return new GlobalScanner(lockProperties, selfLock, "test");
    }
}
