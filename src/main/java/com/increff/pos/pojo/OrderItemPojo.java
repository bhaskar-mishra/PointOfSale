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

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    private Integer quantity;

    @NotNull
    @Column(name = "selling_price")
    private Double sellingPrice;

}
