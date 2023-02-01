package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditOrderItemForm {
    private String orderCode;
    private String barcode;
    private Integer quantity;
}
