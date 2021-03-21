package com.leon.axon.config;

import com.leon.axon.domain.aggregate.Order;
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
public class OrderConfig {
  @Autowired private EventStore eventStore;

  @Bean
  @Scope("prototype")
  public Order orderAggregate() {
    return new Order();
  }

  @Bean
  public AggregateFactory<Order> orderAggregateAggregateFactory() {
    SpringPrototypeAggregateFactory<Order> aggregateFactory =
        new SpringPrototypeAggregateFactory<>();
    aggregateFactory.setPrototypeBeanName("order");
    return aggregateFactory;
  }

  @Bean
  public Repository<Order> orderAggregateRepository() {
    EventSourcingRepository<Order> repository =
        new EventSourcingRepository<Order>(orderAggregateAggregateFactory(), eventStore);
    return repository;
  }
}
