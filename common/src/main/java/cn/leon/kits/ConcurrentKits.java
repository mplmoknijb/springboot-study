package cn.leon.kits;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/6 10:47
 */
@Component
public class ConcurrentKits {
    public ThreadPoolExecutor getThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                500,
                700,
                200,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>());
        return executor;
    }
}
