package cn.leon.core.strategy;

import cn.leon.core.model.LockInfo;
import cn.leon.core.model.SingleNodeProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLockStrategy implements LockStrategy<RLock> {
    @Getter
    private RedissonClient redissonClient;


    public RedisLockStrategy(SingleNodeProperties singleNodeProperties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(singleNodeProperties.getAddress())
                .setDatabase(singleNodeProperties.getDatabase());
        this.redissonClient = Redisson.create(config);
    }

    @Override
    public void unlock(RLock rLock) {
        rLock.unlock();
    }

    @Override
    public RLock lock(LockInfo info, long time, TimeUnit unit) {
//        RLock[] rLocks = getLocks(info);
        RLock lock = redissonClient.getLock(info.getName());
//        RedissonRedLock redissonRedLock = new RedissonRedLock(lock);
        boolean locked = false;
        try {
            if (info.getLeaseTime() >= 0) {
                locked = lock.tryLock(info.getWaitTime(), info.getLeaseTime(), TimeUnit.MILLISECONDS);
            } else {
                locked = lock.tryLock(info.getWaitTime(), TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locked ? lock : null;
    }

//    private RLock[] getLocks(LockInfo info) {
//        RLock[] rLocks = io.vavr.collection.List.ofAll(redissonClients)
//                .map(client -> client.getLock(info.getName()))
//                .toJavaArray(RLock[]::new);
//        return rLocks;
//    }

    @Override
    public RLock lock(LockInfo info) {
        return lock(info, info.getWaitTime(), TimeUnit.MILLISECONDS);
    }
}
