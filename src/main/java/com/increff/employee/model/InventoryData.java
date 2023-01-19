package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryData {
    private String barcode;
    private String product;
    private Integer quantity;
}
