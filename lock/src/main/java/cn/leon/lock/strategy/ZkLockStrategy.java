package cn.leon.lock.strategy;

import cn.leon.lock.model.LockException;
import cn.leon.lock.model.LockInfo;
import cn.leon.lock.model.ZkInfoProperties;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ZkLockStrategy implements LockStrategy<InterProcessMutex>, InitializingBean, DisposableBean {

    private CuratorFramework curatorFramework;
    private final ConcurrentHashMap<Thread, Map<String, InterProcessMutex>> holder = new ConcurrentHashMap();
    private AtomicBoolean aBoolean = new AtomicBoolean(false);
    public static final String PREFIX = "/";

    public ZkLockStrategy(ZkInfoProperties properties) {
        CuratorFrameworkFactory.Builder biild = CuratorFrameworkFactory.builder()
                .connectString(properties.getAddress())
                .retryPolicy(new ExponentialBackoffRetry(properties.getRetrySleepTimeMs(), properties.getRetryCount()))
                .sessionTimeoutMs(properties.getSessionTimeOutMs())
                .connectionTimeoutMs(properties.getConnectionTimeOutMs())
                .namespace(properties.getNameSpace());
        val authInfo = properties.getAuthInfo();
        if (Objects.nonNull(authInfo)) {
            biild.authorization(authInfo.getScheme(), authInfo.getScheme().getBytes(Charset.forName("UTF-8")));
        }
        curatorFramework = biild.build();
    }

    @Override
    public void unlock(InterProcessMutex lock) {
        try {
            lock.release();
        } catch (Exception e) {
            throw new LockException("lock release failed");
        } finally {
            if (!lock.isOwnedByCurrentThread()) {
                holder.remove(Thread.currentThread());
            }
        }
    }

    @Override
    public void lock(LockInfo info, long time, TimeUnit unit) {
        Assert.isTrue(time > 0, "must greater than 0");
        InterProcessMutex lock = getLock(info);
        try {
            aBoolean.set(lock.acquire(time, unit));
        } catch (Exception e) {
            aBoolean.set(false);
            throw new LockException("acquire lock failed", e);
        } finally {
            if (aBoolean.get()) {
                cacheLockRecord(info, lock);
            }
        }
    }

    private void cacheLockRecord(LockInfo info, InterProcessMutex lock) {
        Map<String, InterProcessMutex> cacheLock = null;
        if (holder.containsKey(Thread.currentThread())) {
            cacheLock = holder.get(Thread.currentThread());
            cacheLock.putIfAbsent(lockKey(info), lock);
        } else {
            cacheLock = Maps.newConcurrentMap();
            cacheLock.put(lockKey(info), lock);
            holder.putIfAbsent(Thread.currentThread(), cacheLock);
        }
    }

    private String lockKey(LockInfo info) {
        StringBuffer buffer = new StringBuffer()
                .append(Thread.currentThread().getId())
                .append(":")
                .append(info.getName());
        return buffer.toString();
    }

    private String lockKey(String name) {
        return name.startsWith(PREFIX) ? name : PREFIX.concat(name);
    }

    @SneakyThrows
    private InterProcessMutex getLock(LockInfo info) {
//        return curatorFramework.create()
//                .creatingParentsIfNeeded()
//                .withMode(CreateMode.EPHEMERAL)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .forPath(key);
        InterProcessMutex lock = null;
        val map = holder.get(Thread.currentThread());
        if (!CollectionUtils.isEmpty(map)) {
            lock = map.get(lockKey(info));
        }
        if (Objects.isNull(lock)) {
            lock = new InterProcessMutex(curatorFramework, info.getName());
        }
        return lock;
    }


    @Override
    public void destroy() throws Exception {
        curatorFramework.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        curatorFramework.start();
    }
}
