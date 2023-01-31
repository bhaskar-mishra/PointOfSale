package com.increff.employee.dto;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.BrandsReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class BrandCategoryDto {

    @Autowired
    BrandService brandService;


    public List<String> getAllBrands() throws ApiException{
        return brandService.getAllBrands();
    }

    public List<String> getAllCategories() throws ApiException{
        return  brandService.getAllCategories();
    }

    public void addBrandCategory(BrandForm brandForm) throws ApiException {
        validate(brandForm);
        normalize(brandForm);
        BrandPojo brandPojo = convertFormToPojo(brandForm);
        brandService.add(brandPojo);
    }

    public BrandCategoryData getBrandCategoryById(Integer id) throws ApiException{
        if(id==null){
            throw new ApiException("invalid request : id null");
        }
        BrandPojo brandPojo = brandService.getBrandCategoryById(id);
        return convertPojoToData(brandPojo);
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
        validate(brandsReportForm);
        normalize(brandsReportForm);
        List<BrandPojo> brandPojoList = brandService.getBrandsReport(brandsReportForm);
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(convertPojoToData(brandPojo));
        }
        return brandCategoryDataList;
    }

    public List<String> getCategoriesForBrand(String brand) throws ApiException{
        if(brand==null){
            throw new ApiException("invalid brand");
        }
        brand = brand.toLowerCase().trim();
        return brandService.getCategoriesForBrand(brand);
    }

    public Integer getBrandCategoryId(String brand,String category) throws ApiException{
        if(brand==null || category==null){
            throw new ApiException("Invalid Brand Category");
        }
        brand = brand.toLowerCase().trim();
        category = category.toLowerCase().trim();
        return brandService.selectByBrandCategory(brand,category).getId();
    }

    public void updateBrandCategory(Integer id,BrandForm brandForm) throws ApiException{
        validate(brandForm);
        normalize(brandForm);
        BrandPojo pojo = convertFormToPojo(brandForm);
        brandService.updateBrandCategory(id,pojo);
    }

    public  BrandPojo convertFormToPojo(BrandForm brandForm) throws ApiException {
        validate(brandForm);
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());

        return brandPojo;
    }

    public  BrandCategoryData convertPojoToData(BrandPojo brandPojo) throws ApiException {
        validate(brandPojo);
        BrandCategoryData brandCategoryData = new BrandCategoryData();
        brandCategoryData.setId(brandPojo.getId());
        brandCategoryData.setBrand(brandPojo.getBrand());
        brandCategoryData.setCategory(brandPojo.getCategory());
        return brandCategoryData;
    }

    private void validate(BrandPojo brandPojo) throws ApiException{
        if(brandPojo==null){
            throw new ApiException("pojo null");
        }
        if(brandPojo.getBrand()==null || brandPojo.getBrand().equals("")){
            throw new ApiException("invalid brand");
        }
        if(brandPojo.getCategory()==null || brandPojo.getCategory().equals("")){
            throw new ApiException("invalid category");
        }
    }

    private void validate(BrandForm brandForm) throws ApiException{
        if(brandForm==null){
            throw new ApiException("Invalid form");
        }

        if(brandForm.getBrand()==null && brandForm.getCategory()==null){
            throw new ApiException("Invalid brand category");
        }

        if(brandForm.getBrand()==null || brandForm.getBrand().equals("")){
            throw new ApiException("invalid brand");
        }

        if(brandForm.getCategory()==null || brandForm.getCategory().equals("")){
            throw new ApiException("invalid category");
        }
    }

    private void validate(BrandsReportForm brandsReportForm) throws ApiException{
        if(brandsReportForm.getBrand()==null && brandsReportForm.getCategory()==null){
            throw new ApiException("invalid input");
        }

        if(brandsReportForm.getBrand().equals("") && brandsReportForm.getCategory().equals("")){
            throw new ApiException("no input provided");
        }

        if(brandsReportForm.getCategory()==null){
            brandsReportForm.setCategory("");
        }

        if(brandsReportForm.getBrand()==null){
            brandsReportForm.setBrand("");
        }
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
