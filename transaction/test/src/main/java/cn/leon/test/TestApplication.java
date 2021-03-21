package cn.leon.test;

import cn.leon.test.base.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EntityScan(basePackages = "cn.leon.entity")
@EnableAsync
public class TestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
    @Autowired
    private User2Service user2Service;

    @Override
    public void run(String... args) throws Exception {
        user2Service.async();
    }
}
