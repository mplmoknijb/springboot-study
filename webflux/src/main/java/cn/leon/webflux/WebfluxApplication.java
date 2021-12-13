package cn.leon.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableReactiveFeignClients
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }
}
