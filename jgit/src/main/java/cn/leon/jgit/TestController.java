package cn.leon.jgit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TestController {

    @PostMapping("/alpha")
    public TestDTO alpha(){
        System.out.println("Requesting alpha");
        return TestDTO.builder().aa("aa").bb("bb").build();
    }

    @GetMapping("/beta")
    public void beta(){
        System.out.println("Requesting beta");
    }
}
