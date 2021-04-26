package cn.leon.locktest;

import cn.leon.core.annotation.Lock;
import cn.leon.core.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockService{
    private int i = 10;

    @Lock(type = LockTypeEnum.ZOOKEEPER, waitTime = 30000, leaseTime = 1000)
    public void syncBiz() {
        if (i >= 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + ":  i = {}  ", i);
        }
        i--;
    }
}
