package cn.leon.locktest;

import cn.leon.core.annotation.Lock;
import cn.leon.core.enums.LockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LockDemo {
    private int i = 0;
    private List<Integer> list = new ArrayList<>();

    @Lock(type = LockTypeEnum.ZOOKEEPER, waitTime = 5, leaseTime = 3)
    public void syncBiz() {
        i += 1;
        list.add(i);
        log.info(Thread.currentThread().getName() + ":  i = {} , size = {} ", i, list.size());
    }
}
