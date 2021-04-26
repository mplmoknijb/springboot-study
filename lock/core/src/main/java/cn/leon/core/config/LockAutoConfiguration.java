package cn.leon.core.config;

import cn.leon.core.model.LockProperties;
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

    @Bean
    public GlobalScanner GLobalScanner(SelfLock<?> selfLock) {
        return new GlobalScanner(lockProperties, selfLock, "test");
    }
}
