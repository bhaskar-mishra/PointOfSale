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
        DtoUtils.validateBrandForm(brandForm);
        normalize(brandForm);
        BrandPojo brandPojo = DtoUtils.convertBrandFormToPojo(brandForm);
        brandService.add(brandPojo);
    }

    public BrandCategoryData getBrandCategoryById(Integer id) throws ApiException{
        if(id==null){
            throw new ApiException("invalid request : id null");
        }
        BrandPojo brandPojo = brandService.getBrandCategoryById(id);
        if(brandPojo==null){
            throw new ApiException("Invalid brand category id");
        }
        return DtoUtils.convertBrandPojoToData(brandPojo);
    }

    public void updateBrandCategory(Integer id,BrandForm brandForm) throws ApiException{
        DtoUtils.validateBrandForm(brandForm);
        normalize(brandForm);
        BrandPojo pojo = DtoUtils.convertBrandFormToPojo(brandForm);
        brandService.updateBrandCategory(id,pojo);
    }


    public List<String> getAllBrands() throws ApiException{
        return brandService.getAllBrands();
    }

    public List<String> getAllCategories() throws ApiException{
        return  brandService.getAllCategories();
    }


    public List<BrandCategoryData> getAllBrandCategories() throws ApiException{
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(DtoUtils.convertBrandPojoToData(brandPojo));
        }

        return brandCategoryDataList;
    }

    public List<BrandCategoryData> getBrandsReport(BrandsReportForm brandsReportForm) throws ApiException{
        validate(brandsReportForm);
        normalize(brandsReportForm);
        List<BrandPojo> brandPojoList = brandService.getBrandsReport(brandsReportForm);
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(DtoUtils.convertBrandPojoToData(brandPojo));
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
