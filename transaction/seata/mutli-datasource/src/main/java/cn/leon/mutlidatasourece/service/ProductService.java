package cn.leon.mutlidatasourece.service;

import cn.leon.mutlidatasourece.dao.ProductDao;
import com.baomidou.dynamic.datasource.annotation.DS;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @DS(value = "product-ds")
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 开启新事物
    public void reduceStock(Long productId, Integer amount) throws Exception {
        log.info("[reduceStock] 当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(productId, amount);

        log.info("[reduceStock] 开始扣减 {} 库存", productId);
        // 扣减库存
        int updateCount = productDao.reduceStock(productId, amount);
        // 扣除成功
        if (updateCount == 0) {
            log.warn("[reduceStock] 扣除 {} 库存失败", productId);
            throw new Exception("库存不足");
        }
        // 扣除失败
        log.info("[reduceStock] 扣除 {} 库存成功", productId);
    }

    private void checkStock(Long productId, Integer requiredAmount) throws Exception {
        log.info("[checkStock] 检查 {} 库存", productId);
        Integer stock = productDao.getStock(productId);
        if (stock < requiredAmount) {
            log.warn("[checkStock] {} 库存不足，当前库存: {}", productId, stock);
            throw new Exception("库存不足");
        }
    }

}
