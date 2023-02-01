package com.increff.pos.helper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalesReportHelper {
    private String category;
    private Integer quantity;
    private Double revenue;
    private String brand;

    public SalesReportHelper(String category,Integer quantity,Double revenue){
        this.category = category;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public SalesReportHelper(String brand,Integer quantity,Double revenue,boolean dummy)
    {
        this.brand = brand;
        this.quantity = quantity;
        this.revenue = revenue;
    }
}
