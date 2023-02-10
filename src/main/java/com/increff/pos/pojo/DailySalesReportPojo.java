package com.increff.pos.pojo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
@Table(name = "pos_day_sales")
public class DailySalesReportPojo extends AbstractDatePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true,nullable = false)
    String date;

    @Column(name = "invoiced_orders_count",nullable = false)
    Integer invoicedOrdersCount;

    @Column(name = "invoiced_items_count",nullable = false)
    Integer invoicedItemsCount;

    @Column(nullable = false)
    Double revenue;
}
