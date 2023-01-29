package com.increff.employee.pojo;

import com.increff.employee.util.MyKey;

import javax.persistence.*;

@Entity
public class BrandPojo extends AbstractDatePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String brand;

    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
