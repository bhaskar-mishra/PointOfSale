package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductEditForm {

    private String product;
    private String barcode;
    private Double MRP;
}
