package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "inventory_table")
public class InventoryPojo extends AbstractDatePojo {
    @Id
    @Column(name = "product_id",nullable = false,unique = true)
    private Integer productId;
    @Column(nullable = false)
    private Integer quantity;

}
