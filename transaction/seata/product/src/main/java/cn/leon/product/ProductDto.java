package cn.leon.product;

import lombok.Data;

@Data
public class ProductDto {
    /**
     * 商品编号
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer amount;

}
