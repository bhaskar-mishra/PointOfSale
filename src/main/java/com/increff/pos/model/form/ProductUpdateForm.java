package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductUpdateForm {

    private String product;
    private String barcode;
    private Double MRP;
}
