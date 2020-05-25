package cn.leon.lock.model;

import cn.leon.lock.strategy.LockStrategy;

public class SelfLock<T> {

    private LockStrategy<T> strategy;

    public SelfLock(LockStrategy strategy){
        this.strategy = strategy;
    }

    public T  lock(LockInfo info){
        return strategy.lock(info);
    }

    public void unlock(T t){
        strategy.unlock(t);
    }
}
