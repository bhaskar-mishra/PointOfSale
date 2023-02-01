package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderData {

    private Integer orderId;
    private String randomKey;
    private String status;
    private String time;
}
