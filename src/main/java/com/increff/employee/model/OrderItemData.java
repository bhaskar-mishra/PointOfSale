package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemData {

    private Integer orderItemId;
    private Integer orderId;
    private String barcode;
    private String product;
    private Integer quantity;
}
