package cn.leon.core.config;

import cn.leon.core.model.LockProperties;
import cn.leon.core.model.LockProxy;
import cn.leon.core.model.SelfLock;
import cn.leon.core.model.ZkInfoProperties;
import cn.leon.core.strategy.LockStrategy;
import cn.leon.core.strategy.ZkLockStrategy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnProperty(value = "lock.enabled", havingValue = "true")
public class LockAutoConfiguration {
    private final LockProperties lockProperties;

    public LockAutoConfiguration(LockProperties lockProperties) {
        this.lockProperties = lockProperties;
    }

    @Bean
    public LockProxy lockProxy(SelfLock selfLock) {
        return new LockProxy(lockProperties, selfLock);
    }

    @Configuration
    @ConditionalOnProperty(value = "lock.type", havingValue = "zk")
    @ConditionalOnClass({CuratorFrameworkFactory.class, InterProcessMutex.class, ZooKeeper.class})
    @EnableConfigurationProperties(ZkInfoProperties.class)
    public class zkAutoConfiguration {

        @Bean
        public LockStrategy lockStrategy(ZkInfoProperties zkInfoProperties) {
            return new ZkLockStrategy(zkInfoProperties);
        }

        @Bean
        @ConditionalOnMissingBean
        public SelfLock<InterProcessMutex> selfLock(LockStrategy<InterProcessMutex> lockStrategy) {
            return new SelfLock<>(lockStrategy);
        }
    }

}
