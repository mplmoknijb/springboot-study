package cn.leon.test.base;

import cn.leon.test.dao.User2Dao;
import cn.leon.test.entity.User2Entity;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Slf4j
public class User2Service {
    @Autowired
    private User2Dao user2Dao;
    @Autowired
    private User1Service user1Service;

    @Transactional(rollbackFor = RuntimeException.class)
    public void add() {
        user2Dao.insert(User2Entity.builder().name("2").build());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void addException() {
        user2Dao.insert(User2Entity.builder().name("2").build());
        Assert.notNull(null, "error");
    }

    public void async() {
        log.info("step 1 : {}", Thread.currentThread().getName());
        user2Dao.insert(User2Entity.builder().name("2").build());
        user1Service.async(() -> user1Service.saveUser());

    }
}
