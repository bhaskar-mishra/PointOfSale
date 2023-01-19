package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm {

    private String barcode;
    private String brand;
    private String category;
    private Integer brandCategoryId;
    private String product;
    private Double mrp;
}