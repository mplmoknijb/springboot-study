package cn.leon.locktest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LockDemo implements CommandLineRunner {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(2));

    @Autowired
    private LockService lockService;

    @Override
    public void run(String... args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockService.syncBiz();
            }
        };
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(runnable);
        }
    }
}
