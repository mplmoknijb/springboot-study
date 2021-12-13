package cn.leon.webflux.controller;

import cn.leon.webflux.client.FeignClient;
import cn.leon.webflux.model.TestDTO;
import cn.leon.webflux.service.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class RequestController {


    @Autowired
    private WebClientService webClientService;

    @Autowired
    private FeignClient feignClient;

    @GetMapping("/req")
    public Mono<TestDTO> req() throws Exception {
//        Mono<ServerHttpRequest> request = ReactiveRequestContextHolder.getRequest();
//        return request.flatMap(req -> {
////            log.info(req.toString());
//            return webClientService.invoke();
//        });
        return webClientService.invoke();
    }


    @GetMapping("echo")
    public Mono<String> invoke(String context){
        return feignClient.echo(context);
    }
}
