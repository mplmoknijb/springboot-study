package cn.leon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mujian
 * @date 2019-4-25 0025
 **/
@RestController
public class KafkaController {

    @Autowired
    private ProducerService service;

    @PostMapping("/send")
    public void send(@RequestBody MessageForm form) {
        service.sendMessage(form);
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }
}
