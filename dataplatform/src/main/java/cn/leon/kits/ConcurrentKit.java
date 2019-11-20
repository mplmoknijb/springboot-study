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
public class ConcurrentKit {
    public ThreadPoolExecutor getThreadPool(Integer core,Integer max,Integer keepAliveTime) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core,
                max,
                keepAliveTime,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>());
        return executor;
    }
}
