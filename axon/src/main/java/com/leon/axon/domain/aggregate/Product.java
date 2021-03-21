package com.leon.axon.domain.aggregate;

import com.leon.axon.applicaiton.cmd.CreateProductCommand;
import com.leon.axon.infrastructure.event.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Data
@Aggregate
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @AggregateIdentifier
    private String id;
    private String name;
    private int stock;
    private long price;

    @CommandHandler
    public Product(CreateProductCommand command) {
        apply(new ProductCreatedEvent(command.getId(),command.getName(),command.getPrice(),command.getStock()));
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }
}
