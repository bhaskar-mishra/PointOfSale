package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemForm {
    private String orderCode;
    private String barcode;
    private Integer quantity;
    private Double price;
}
