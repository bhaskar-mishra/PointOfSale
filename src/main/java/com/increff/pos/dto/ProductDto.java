package com.increff.pos.dto;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;


    public void addProduct(ProductForm productForm) throws ApiException{
        DtoUtils.validateProductForm(productForm);
        DtoUtils.normalizeProductForm(productForm);
        BrandPojo brandPojo = brandService.selectByBrandCategory(productForm.getBrand(),productForm.getCategory());
        if(brandPojo==null){
            throw new ApiException("incorrect brand category");
        }
        productService.add(DtoUtils.convertProductFormToPojo(productForm,brandPojo.getId()));
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo> productPojoList = productService.getAll();
        List<ProductData> productDataList = new ArrayList<>();
        for(ProductPojo productPojo : productPojoList){
            BrandPojo brandPojo = brandService.getBrandCategoryById(productPojo.getBrandCategoryId());

            productDataList.add(DtoUtils.convertProductPojoToData(productPojo,brandPojo.getBrand(),brandPojo.getCategory()));
        }

        return productDataList;
    }

    public ProductData getProductByBarcode(String barcode) throws ApiException{
        if(barcode==null || barcode.toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }
        barcode = barcode.toLowerCase().trim();
        ProductPojo productPojo = productService.selectByBarcode(barcode);
        if(productPojo==null){
            throw new ApiException("invalid barcode");
        }

        BrandPojo brandPojo = brandService.getBrandCategoryById(productPojo.getBrandCategoryId());


       return DtoUtils.convertProductPojoToData(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }

    public void updateProduct(ProductUpdateForm productUpdateForm) throws ApiException{
        DtoUtils.validateProductEditForm(productUpdateForm);
        DtoUtils.normalizeProductEditForm(productUpdateForm);
        productService.updateProduct(productUpdateForm);
    }

}
