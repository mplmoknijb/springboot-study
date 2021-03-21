package com.leon.axon.applicaiton.cmd;

import com.leon.axon.infrastructure.model.OrderId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateCommand {
    private OrderId orderId;
    private String username;
    private Map<String, Integer> products;
}
