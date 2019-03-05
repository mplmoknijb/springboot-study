package cn.leon;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference(version = "1.0.0")
    HelloService helloService;

    @GetMapping("hello")
    public String sayHello(String name) {
        return helloService.hello(name);
    }
}
