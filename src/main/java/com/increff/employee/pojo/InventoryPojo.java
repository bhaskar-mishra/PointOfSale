package com.increff.employee.pojo;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String barcode;

    @NotNull
    private Integer quantity;

    @NotNull
    private String product;
}
