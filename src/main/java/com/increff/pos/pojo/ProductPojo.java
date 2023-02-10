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

    @Column(unique = true,nullable = false)
    private String barcode;


    @Column(name = "brand_category_id",nullable = false)
    private Integer brandCategoryId;

    @Column(nullable = false)
    private String product;


    @Column(nullable = false)
    private Double mrp;


}
