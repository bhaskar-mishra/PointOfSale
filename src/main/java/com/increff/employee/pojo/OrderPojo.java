package com.increff.employee.pojo;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class OrderPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Getter

    private Status status = Status.PENDING;
    @Getter
    private String time;

    public OrderPojo(){
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         Date date = new Date();
         time = formatter.format(date);
    }

    public void setStatus(){
        status = Status.COMPLETE;
    }

}


