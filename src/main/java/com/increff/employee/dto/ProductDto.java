package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductEditForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    public  ProductPojo convert(ProductForm productForm) throws ApiException {
        System.out.println("reaching convert inside product DTO");
        ProductPojo productPojo = new ProductPojo();
        Integer brandCategoryId = 0;
        System.out.println(brandService);
        try {
             brandCategoryId = brandService.getBrandCategoryId(productForm.getBrand(), productForm.getCategory());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("brandPojo inside convert: "+brandCategoryId);
        productPojo.setProduct(productForm.getProduct());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setMRP(productForm.getMrp());
        productPojo.setBrand(productForm.getBrand());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setBrandCategoryId(brandCategoryId);
        return productPojo;
    }

    public  ProductData convert(ProductPojo productPojo){
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setProduct(productPojo.getProduct());
        productData.setMrp(productPojo.getMRP());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(productPojo.getBrand());
        productData.setCategory(productPojo.getCategory());
        return productData;
    }

    public void validateProductEditForm(String barcode, ProductEditForm productEditForm) throws ApiException {
        ProductPojo productPojo = productService.selectByBarcode(barcode);
        System.out.println("inside validateProductEditForm : "+productEditForm.getMRP());
        if (productEditForm.getMRP() == null || productEditForm.getMRP().equals(0)) {
            productEditForm.setMRP(productEditForm.getMRP());
        }

        if(productEditForm.getProduct()==null || productEditForm.getProduct().equals("")){
            productEditForm.setProduct(productPojo.getProduct());
        }
    }
}
