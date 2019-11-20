package cn.leon.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {

    @Autowired
    private ByeService byeService;

    @GetMapping("/bye")
    public String bye() {
        return byeService.bye();
    }
}
