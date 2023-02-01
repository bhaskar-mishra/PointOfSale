package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.rmi.server.UID;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "order_table")
public class OrderPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    private String status = "PENDING";

    @NotNull
    @Column(name = "placed_time")
    private ZonedDateTime placedTime;

    @NotNull
    @Column(unique = true, name = "order_code")
    private String orderCode;


}


