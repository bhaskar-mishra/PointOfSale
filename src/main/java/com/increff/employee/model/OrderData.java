package com.increff.employee.model;

import com.increff.employee.pojo.Status;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderData {

    private int orderId;
    private Status status;
    private String time;
}
