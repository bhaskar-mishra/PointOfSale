package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "product_table")
public class ProductPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String barcode;

    @NotNull
    @Column(name = "brand_category_id")
    private Integer brandCategoryId;

    @NotNull
    private String product;


    @NotNull
    private Double mrp;

    @NotNull
    private String brand;

    @NotNull
    private String category;


}
