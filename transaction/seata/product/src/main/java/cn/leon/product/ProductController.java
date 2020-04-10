package cn.leon.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/reduce-stock")
    public Boolean reduceStock(@RequestBody ProductDto productDto) {
        log.info("[reduceStock] 收到减少库存请求, 商品:{}, 价格:{}", productDto.getProductId(),
                productDto.getAmount());
        try {
            productService.reduceStock(productDto.getProductId(), productDto.getAmount());
            // 正常扣除库存，返回 true
            return true;
        } catch (Exception e) {
            // 失败扣除库存，返回 false
            return false;
        }
    }

}
