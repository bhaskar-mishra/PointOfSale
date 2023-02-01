package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceItem {
    private Double price;
    private Integer quantity;
    private String name;
}
