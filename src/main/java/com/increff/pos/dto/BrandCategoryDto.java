package com.increff.pos.dto;

import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.BrandsReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
        List<BrandPojo> brandPojoList = brandService.getAllBrandCategories();
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(DtoUtils.convertBrandPojoToData(brandPojo));
        }

        return brandCategoryDataList;
    }


    public List<BrandCategoryData> getCategoriesForBrand(String brand) throws ApiException{
        if(brand==null){
            throw new ApiException("invalid brand");
        }
        brand = brand.toLowerCase().trim();
        List<BrandPojo> brandPojoList =  brandService.getCategoriesForBrand(brand);
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandCategoryDataList.add(DtoUtils.convertBrandPojoToData(brandPojo));
        }
        return brandCategoryDataList;
    }


    private void normalize(BrandForm brandForm) {
        brandForm.setBrand(brandForm.getBrand().toLowerCase().trim());
        brandForm.setCategory(brandForm.getCategory().toLowerCase().trim());
    }

}
