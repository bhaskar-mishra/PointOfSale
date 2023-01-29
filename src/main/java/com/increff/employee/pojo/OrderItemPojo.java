package com.increff.employee.pojo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderItemPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer orderItemId;

    @Getter @Setter
    private String barcode;
    @Getter @Setter
    private Integer orderId;
    @Getter @Setter
    private String product;
    @Getter @Setter
    private Integer quantity;
    @Getter @Setter
    private Double price;
    @Getter @Setter
    private String randomKey;
}
