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
    private Integer id;

    private String barcode;

    @NotNull
    private Integer quantity;

    private String product;
}
