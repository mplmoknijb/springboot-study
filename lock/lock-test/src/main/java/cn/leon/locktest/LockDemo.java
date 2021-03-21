package cn.leon.locktest;

import cn.leon.core.annotation.Lock;
import cn.leon.core.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LockDemo  implements CommandLineRunner {
    private int i = 0;
    private List<Integer> list = new ArrayList<>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(80));
    @Lock(type = LockTypeEnum.ZOOKEEPER, waitTime = 3, leaseTime = 3)
    public void syncBiz() {
        i += 1;
        list.add(i);
        log.info(Thread.currentThread().getName() + ":  i = {} , size = {} ", i, list.size());
    }


    @Override
    public void run(String... args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                syncBiz();
            }
        };
        for (int i = 0; i < 9; i++) {
            threadPoolExecutor.execute(runnable);
        }
    }
}
