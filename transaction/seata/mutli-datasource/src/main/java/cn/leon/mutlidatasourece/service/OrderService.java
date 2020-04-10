package cn.leon.mutlidatasourece.service;

import cn.leon.mutlidatasourece.OrderDto;
import cn.leon.mutlidatasourece.dao.OrderDao;
import com.baomidou.dynamic.datasource.annotation.DS;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mujian
 */
@Slf4j
@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private AccountService accountService;

    @Resource
    private ProductService productService;

    @DS(value = "order-ds")
    @GlobalTransactional
    public Integer createOrder(Long userId, Long productId, Integer price) throws Exception {
        // 购买数量，暂时设置为 1。
        Integer amount = 1;

        log.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // 扣减库存
        productService.reduceStock(productId, amount);

        // 扣减余额
        accountService.reduceBalance(userId, price);

        // 保存订单
        OrderDto order = new OrderDto();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setPayAmount(amount * price);
        orderDao.saveOrder(order);
        log.info("[createOrder] 保存订单: {}", order.getId());

        // 返回订单编号
        return order.getId();
    }

}
