package cn.leon.test.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;
@Slf4j
@Service
public class AsyncService {
    @Transactional
    public void save(Supplier supplier){
        supplier.get();

        log.info("step 3 : {}", Thread.currentThread().getName());
        throw new RuntimeException();
    }
}
