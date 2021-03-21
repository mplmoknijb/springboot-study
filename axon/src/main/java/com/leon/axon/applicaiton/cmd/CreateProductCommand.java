package com.leon.axon.applicaiton.cmd;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductCommand {
    private String id;
    private String name;
    private long price;
    private int stock;
}
