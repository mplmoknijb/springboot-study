package cn.leon.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.leon.order.OrderService;
public class ProductMoudleService {

    @GetMapping
    public void test(){
        return OrderService.getOrder();
    }
}
