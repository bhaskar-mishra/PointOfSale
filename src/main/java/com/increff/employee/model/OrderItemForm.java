package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

public class OrderItemForm {

    @Getter @Setter
    private int orderId;
    @Getter @Setter
    private String barcode;
    @Getter @Setter
    private int quantity;
    @Getter @Setter
    private double mrp;
}
