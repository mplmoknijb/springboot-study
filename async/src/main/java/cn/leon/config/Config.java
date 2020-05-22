package cn.leon.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池
 */
@EnableAsync
@Configuration
public class Config implements AsyncConfigurer {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private static final int CORE_POOL_SIZE = 5;

    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private static final int MAX_POOL_SIZE = 20;

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private static final int QUEUE_CAPACITY = 10;


    /**
     * 通过重写getAsyncExecutor方法，制定默认的任务执行由该方法产生
     * <p>
     * 配置类实现AsyncConfigurer接口并重写getAsyncExcutor方法，并返回一个ThreadPoolTaskExevutor
     * 这样我们就获得了一个基于线程池的TaskExecutor
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);// 线程池维护线程的最少数量
        executor.setMaxPoolSize(MAX_POOL_SIZE);// 线程池维护线程的最大数量
        executor.setQueueCapacity(QUEUE_CAPACITY);// 线程池所使用的缓冲队列
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

    @Bean
    public Executor getMine() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //     >=  CorePoolSize
        //加入缓存队列Queue
        //核心线程数(一直存活的线程包括闲置，如果没有设置allowCoreThreadTimeOut这个属性为true)
        executor.setCorePoolSize(1);
        //该线程池中线程总数最大值
        executor.setMaxPoolSize(1);
        //缓存队列容量
        executor.setQueueCapacity(1);
        executor.setThreadNamePrefix("Mine========================>");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
