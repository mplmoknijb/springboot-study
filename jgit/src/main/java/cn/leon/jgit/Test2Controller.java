package cn.leon.jgit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {

    @GetMapping("/echo")
    public void echo(){
        System.out.println("Requesting echo");
    }

}
