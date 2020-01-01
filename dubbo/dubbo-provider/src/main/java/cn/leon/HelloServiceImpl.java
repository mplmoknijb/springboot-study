package cn.leon;


import com.alibaba.dubbo.config.annotation.Service;

@Service
@org.springframework.stereotype.Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        return "ok";
    }
}
