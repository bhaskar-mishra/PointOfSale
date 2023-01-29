package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ProductPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter @Setter
    private String barcode;

    @Getter @Setter
    private Integer brandCategoryId;

    @Getter @Setter
    private String product;

    @Getter @Setter
    private Double MRP;

    @Getter @Setter
    private String brand;

    @Getter @Setter
    private String category;


}
