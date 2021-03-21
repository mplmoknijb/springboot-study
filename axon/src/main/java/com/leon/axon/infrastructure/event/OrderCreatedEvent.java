package com.leon.axon.infrastructure.event;

import com.leon.axon.infrastructure.model.OrderId;
import com.leon.axon.infrastructure.model.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

  @TargetAggregateIdentifier private OrderId orderId;
  private String username;
  private Map<String, OrderProduct> products;
}
