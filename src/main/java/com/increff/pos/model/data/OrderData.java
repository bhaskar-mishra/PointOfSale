package com.increff.pos.model.data;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
public class OrderData {

    private Integer orderId;
    private String orderCode;
    private String status;
    private ZonedDateTime time;
}
