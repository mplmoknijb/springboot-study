package cn.leon.locktest;

import cn.leon.core.annotation.Lock;
import cn.leon.core.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockService{
    private int i = 10;

    @Lock(type = LockTypeEnum.REDIS, waitTime = 5000L, leaseTime = 3000L)
    public void syncBiz() {
        if (i >= 0) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            log.info(Thread.currentThread().getName() + ":  i = {}  ", i);
        }
        i--;
    }
}
