package cn.leon.locktest;

import cn.leon.core.annotation.Lock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LockTestApplication.class)
class LockTestApplicationTests {
    @Autowired
    private LockDemo lockDemo;
    private  ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(80));


    @Test
    public void contextLoads() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockDemo.syncBiz();
            }
        };
        for (int i = 0; i < 9; i++) {
            threadPoolExecutor.execute(runnable);
        }
        threadPoolExecutor.shutdown();
    }

}
