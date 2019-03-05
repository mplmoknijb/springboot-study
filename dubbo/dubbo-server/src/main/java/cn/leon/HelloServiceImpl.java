package cn.leon;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version =" 1.0.0")
public class HelloServiceImpl implements HelloService{

    @Override
    public String hello(String name) {
        return name;
    }
}
