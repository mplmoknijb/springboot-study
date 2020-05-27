package cn.leon.lock.strategy;

import cn.leon.lock.model.LockInfo;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public interface LockStrategy<T> {

    void unlock(T t);

    T lock(LockInfo info, long time, TimeUnit unit);

    T lock(LockInfo info);
}
