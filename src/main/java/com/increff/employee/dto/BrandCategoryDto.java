package com.increff.employee.dto;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.stereotype.Service;


@Service
public class BrandCategoryDto {
    public static BrandPojo convert(BrandForm brandForm){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());

        return brandPojo;
    }

    public static BrandCategoryData convert(BrandPojo brandPojo)
    {
        BrandCategoryData brandCategoryData = new BrandCategoryData();
        brandCategoryData.setId(brandPojo.getId());
        brandCategoryData.setBrand(brandPojo.getBrand());
        brandCategoryData.setCategory(brandPojo.getCategory());
        return brandCategoryData;
    }
}
