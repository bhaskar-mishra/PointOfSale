package com.increff.pos.pojo;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item_table")
public class OrderItemPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;


    @NotNull
    private String barcode;

    @NotNull
    private Integer orderId;

    @NotNull
    private String product;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double price;

    @NotNull
    private String randomKey;
}
