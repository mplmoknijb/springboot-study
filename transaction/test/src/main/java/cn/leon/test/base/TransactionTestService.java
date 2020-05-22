package cn.leon.test.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionTestService {
    @Autowired
    private User1Service user1Service;

    @Autowired
    private User2Service user2Service;

    @Async
    public void test1(){
        user1Service.add();
    }


}
