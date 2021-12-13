package cn.leon.webflux.client;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "jgit",url = "http://127.0.0.1:8088/")
public interface FeignClient {

    @GetMapping("echo")
    Mono<String> echo(String context);
}
