package cn.leon.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;


/**
 * Redis默认是守护进程的-只能在本地使用localhost访问
 * 开启IP访问需要配置
 */

public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    //闭锁，等待准备资源加载完毕调用await触发
    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        //资源加载完后，闭锁-1
        latch.countDown();
    }
}
