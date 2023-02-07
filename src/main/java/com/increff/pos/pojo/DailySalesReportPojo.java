package com.increff.pos.pojo;

import com.sun.istack.NotNull;
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

    @NotNull
    @Column(unique = true)
    String date;

    @NotNull
    Integer invoicedOrdersCount;

    @NotNull
    Integer invoicedItemsCount;

    @NotNull
    Double revenue;
}
