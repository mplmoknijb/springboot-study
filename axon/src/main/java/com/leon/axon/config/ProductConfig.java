package com.leon.axon.config;

import com.leon.axon.domain.aggregate.Product;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ProductConfig {

    @Autowired
    private EventStore eventStore;

    @Bean
    @Scope("prototype")
    public Product Product() {
        return new Product();
    }

    @Bean
    public AggregateFactory<Product> ProductAggregateFactory() {
        SpringPrototypeAggregateFactory<Product> aggregateFactory =
                new SpringPrototypeAggregateFactory<>();
        aggregateFactory.setPrototypeBeanName("product");
        return aggregateFactory;
    }

    @Bean
    public Repository<Product> ProductRepository() {
        EventSourcingRepository<Product> repository =
                new EventSourcingRepository<Product>(
                        ProductAggregateFactory(), eventStore);
        return repository;
    }
}
