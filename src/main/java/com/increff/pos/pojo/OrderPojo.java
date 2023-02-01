package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "order_table")
public class OrderPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private String status = (Status.PENDING).name();

    @NotNull
    @Column(name = "placed_time")
    private String placedTime;

    @NotNull
    @Column(unique = true)
    private String orderCode;//orderCode;

    public OrderPojo(){
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = new Date();
         placedTime = formatter.format(date);
    }

    public void setStatus(){
        status = (Status.COMPLETE).name();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        placedTime = formatter.format(date);
    }

}


