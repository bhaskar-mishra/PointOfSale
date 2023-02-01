package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.ProductForm;
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
        normalize(productForm);
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
            productDataList.add(DtoUtils.convertProductPojoToData(productPojo));
        }

        return productDataList;
    }

    public ProductData getProductByBarcode(String barcode) throws ApiException{
        if(barcode==null || barcode.toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        ProductPojo productPojo = productService.selectByBarcode(barcode);
        if(productPojo==null){
            throw new ApiException("invalid barcode");
        }

       return DtoUtils.convertProductPojoToData(productPojo);
    }

    public void updateProduct(ProductEditForm productEditForm) throws ApiException{
        DtoUtils.validateProductEditForm(productEditForm);
        normalize(productEditForm);
        productService.updateProduct(productEditForm);
    }





    private void normalize(ProductForm productForm){
        productForm.setProduct(productForm.getProduct().toLowerCase().trim());
        productForm.setBrand(productForm.getBrand().toLowerCase().trim());
        productForm.setCategory(productForm.getCategory().toLowerCase().trim());
        productForm.setBarcode(productForm.getBarcode().toLowerCase().trim());
    }

    private void normalize(ProductEditForm productEditForm){
        productEditForm.setProduct(productEditForm.getProduct().toLowerCase().trim());
    }
}
