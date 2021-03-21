package com.leon.axon.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedEvent {
    private String id;
    private String name;
    private long price;
    private int stock;
}
