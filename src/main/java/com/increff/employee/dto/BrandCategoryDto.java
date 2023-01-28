package com.increff.employee.dto;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.BrandsReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BrandCategoryDto {

    @Autowired
    BrandService brandService;


    public void addBrandCategory(BrandForm brandForm) throws ApiException {
        normalize(brandForm);
        BrandPojo brandPojo = convertFormToPojo(brandForm);
        brandService.add(brandPojo);
    }

    public List<BrandCategoryData> getAllBrandCategories() throws ApiException{
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(convertPojoToData(brandPojo));
        }

        return brandCategoryDataList;
    }

    public List<BrandCategoryData> getBrandsReport(BrandsReportForm brandsReportForm) throws ApiException{
        normalize(brandsReportForm);
        List<BrandPojo> brandPojoList = brandService.getBrandsReport(brandsReportForm);
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(convertPojoToData(brandPojo));
        }
        return brandCategoryDataList;
    }

    public List<String> getCategoriesForBrand(String brand) throws ApiException{
        brand = brand.toLowerCase().trim();
        return brandService.getCategoriesForBrand(brand);
    }

    public Integer getBrandCategoryId(String brand,String category) throws ApiException{
        brand = brand.toLowerCase().trim();
        category = category.toLowerCase().trim();
        return brandService.getBrandCategoryId(brand,category);
    }

    public void updateBrandCategory(Integer id,BrandForm brandForm) throws ApiException{
        normalize(brandForm);
        brandService.updateBrandCategory(id,brandForm);
    }

    public  BrandPojo convertFormToPojo(BrandForm brandForm){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());

        return brandPojo;
    }

    public  BrandCategoryData convertPojoToData(BrandPojo brandPojo)
    {
        BrandCategoryData brandCategoryData = new BrandCategoryData();
        brandCategoryData.setId(brandPojo.getId());
        brandCategoryData.setBrand(brandPojo.getBrand());
        brandCategoryData.setCategory(brandPojo.getCategory());
        return brandCategoryData;
    }

    private void normalize(BrandForm brandForm) {
        brandForm.setBrand(brandForm.getBrand().toLowerCase().trim());
        brandForm.setCategory(brandForm.getCategory().toLowerCase().trim());
    }

    private void normalize(BrandsReportForm brandsReportForm){
        brandsReportForm.setBrand(brandsReportForm.getBrand().toLowerCase().trim());
        brandsReportForm.setCategory(brandsReportForm.getCategory().toLowerCase().trim());
    }
}
