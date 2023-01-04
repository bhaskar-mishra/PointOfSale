package com.increff.employee.pojo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderItemPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int orderItemId;

    @Getter @Setter
    private int orderId;
    @Getter @Setter
    private int productId;
    @Getter @Setter
    private int quantity;
    @Getter @Setter
    private double mrp;
}
