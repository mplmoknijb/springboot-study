package cn.leon.lock;

import cn.leon.lock.model.LockInfo;
import cn.leon.lock.strategy.LockStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class SelfLockApplicationTests {
    private static ThreadPoolExecutor executor;
    private LockStrategy lockStrategy;
    @BeforeClass
    public static void contextLoads() {
        executor = new ThreadPoolExecutor(
                500,
                700,
                200,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>(1000));
    }


    @AfterClass
    public static void after(){
        executor.shutdown();
    }

    @Test
    public void lockTest(){
        LockInfo test = LockInfo.builder().name("test").leaseTime(3000).build();
        int count = 0;
        Object lock = lockStrategy.lock(test, 5000, TimeUnit.MILLISECONDS);
        count++;
        System.out.println(count);
        lockStrategy.unlock(lock);

    }
    @Test
    public void unLockTest(){}

}
