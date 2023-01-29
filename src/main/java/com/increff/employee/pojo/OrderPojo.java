package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class OrderPojo extends AbstractDatePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer orderId;

    @Getter

    private String status = (Status.PENDING).name();
    @Getter
    private String time;

    @Getter @Setter
    private String randomKeyForId;

    public OrderPojo(){
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = new Date();
         time = formatter.format(date);
    }

    public void setStatus(){
        status = (Status.COMPLETE).name();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        time = formatter.format(date);
    }

}


