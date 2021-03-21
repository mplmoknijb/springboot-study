package com.leon.axon.infrastructure.event.handler;

import com.leon.axon.infrastructure.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventHandler {

  @EventHandler
  public void on(ProductCreatedEvent event) {
    log.info("Product create Event handle");
  }
}
