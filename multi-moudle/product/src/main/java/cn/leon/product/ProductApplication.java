package cn.leon.product;

import cn.leon.order.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(OrderService.getOrder());
    }
}
