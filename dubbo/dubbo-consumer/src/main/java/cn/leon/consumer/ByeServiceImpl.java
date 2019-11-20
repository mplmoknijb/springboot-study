package cn.leon.consumer;

import org.springframework.stereotype.Service;

import cn.leon.HelloService;
import com.alibaba.dubbo.config.annotation.Reference;

@Service
public class ByeServiceImpl implements ByeService {

    @Reference
    HelloService helloService;

    @Override
    public String bye() {
        return helloService.hello();
    }
}
