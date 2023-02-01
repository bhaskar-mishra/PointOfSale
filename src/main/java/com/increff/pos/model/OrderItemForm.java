package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

public class OrderItemForm {

    @Getter @Setter
    private String randomKey;
    @Getter @Setter
    private String barcode;
    @Getter @Setter
    private Integer quantity;
    @Getter @Setter
    private Double price;
}
