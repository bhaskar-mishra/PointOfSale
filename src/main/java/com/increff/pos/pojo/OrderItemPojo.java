package com.increff.pos.pojo;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item_table",uniqueConstraints = { @UniqueConstraint(columnNames = { "order_id", "product_id" }) })
public class OrderItemPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;


    @Column(name = "product_id",nullable = false)
    private Integer productId;


    @Column(name = "order_id",nullable = false)
    private Integer orderId;

    @Column(nullable = false)
    private Integer quantity;


    @Column(name = "selling_price",nullable = false)
    private Double sellingPrice;

}
