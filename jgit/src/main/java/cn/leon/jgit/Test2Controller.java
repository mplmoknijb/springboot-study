package cn.leon.jgit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Test2Controller {

    @GetMapping("/echo")
    public String echo(String context){
        System.out.println(context);
        return context;
    }
}
