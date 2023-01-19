package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class SalesReportForm {
    private String startDate;
    private String endDate;
    private String brand;
    private String category;
}
