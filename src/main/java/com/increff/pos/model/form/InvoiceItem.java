package com.increff.pos.model.form;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceItem {
    private Double price;
    private Integer quantity;
    private String name;
}
