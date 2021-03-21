package cn.leon.test.base;

import cn.leon.test.dao.User1Dao;
import cn.leon.test.entity.User1Entity;
import cn.leon.test.entity.User2Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@Service
@Slf4j
public class User1Service {
    @Autowired
    private User1Dao user1Dao;
    @Autowired
    private User2Service user2Service;
    @Autowired
    private AsyncService asyncService;

    @Async
    @Transactional(rollbackFor = RuntimeException.class)
    public void add() {
        user2Service.add();
        addException();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void addException() {
        user1Dao.insert(User1Entity.builder().name("1").build());
        try {
            Assert.notNull(null, "error");
        } catch (IllegalArgumentException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Async
    public void async(Supplier supplier) {
        log.info("step 2 : {}", Thread.currentThread().getName());
        asyncService.save(supplier);

    }

    public int  saveUser(){
        return user1Dao.insert(User1Entity.builder().name("2").build());
    }


}
