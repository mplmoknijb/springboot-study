package cn.leon.webflux.controller;

import cn.leon.webflux.model.TestDTO;
import cn.leon.webflux.service.WebClientService;
import cn.leon.webflux.util.ReactiveRequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class RequestController {


    @Autowired
    private WebClientService webClientService;

    @GetMapping("/req")
    public Mono<TestDTO> req() {
        Mono<ServerHttpRequest> request = ReactiveRequestContextHolder.getRequest();
        return request.flatMap(req -> {
            log.info(req.toString());
            return webClientService.invoke();
        });
    }
}
