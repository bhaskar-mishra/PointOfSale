package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class SchedulerPojo extends AbstractDatePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Getter @Setter
    String date;
    @Getter @Setter
    Integer invoiced_orders_count;
    @Getter @Setter
    Integer invoiced_items_count;
    @Getter @Setter
    Double revenue;
}
