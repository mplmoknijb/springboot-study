package com.leon.axon.domain.handler;

import com.leon.axon.applicaiton.cmd.CreateOrderCommand;
import com.leon.axon.domain.aggregate.Order;
import com.leon.axon.domain.aggregate.Product;
import com.leon.axon.infrastructure.model.OrderProduct;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
public class OrderHandler {


    @Autowired
    private Repository<Order> repository;

    @Autowired
    private Repository<Product> productRepository;


    @CommandHandler
    public void handle(CreateOrderCommand command) throws Exception {
        Map<String, OrderProduct> products = new HashMap<>();
        command.getProducts().forEach((productId,number)->{
            log.debug("Loading product information with productId: {}",productId);
            Aggregate<Product> aggregate = productRepository.load(productId);
            products.put(productId,
                    new OrderProduct(productId,
                            aggregate.invoke(Product -> Product.getName()),
                            aggregate.invoke(Product -> Product.getPrice()),
                            number));
        });
        repository.newInstance(() -> new Order(command.getOrderId(), command.getUsername(), products));
    }
}
