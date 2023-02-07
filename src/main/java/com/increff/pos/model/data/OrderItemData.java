package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemData {

    private Integer orderItemId;
    private Integer orderId;
    private String barcode;
    private String product;
    private Double price;
    private Double total;
    private Integer quantity;
}
