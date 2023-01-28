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

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    public void addProduct(ProductForm productForm) throws ApiException{
        normalize(productForm);
        productService.add(convertFormToPojo(productForm));
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo> productPojoList = productService.getAll();
        List<ProductData> productDataList = new ArrayList<>();
        for(ProductPojo productPojo : productPojoList){
            productDataList.add(convertPojoToData(productPojo));
        }

        return productDataList;
    }

    public void updateProductWithGivenBarcode(String barcode,ProductEditForm productEditForm) throws ApiException{
        barcode = barcode.toLowerCase().trim();
        normalize(productEditForm);
        validateProductEditForm(barcode,productEditForm);
        productService.updateProduct(barcode,productEditForm);
    }

    private   ProductPojo convertFormToPojo(ProductForm productForm) throws ApiException {
        System.out.println("reaching convert inside product DTO");
        ProductPojo productPojo = new ProductPojo();
        Integer brandCategoryId = 0;

        brandCategoryId = brandService.getBrandCategoryId(productForm.getBrand(), productForm.getCategory());
        productPojo.setProduct(productForm.getProduct());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setMRP(productForm.getMrp());
        productPojo.setBrand(productForm.getBrand());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setBrandCategoryId(brandCategoryId);
        return productPojo;
    }

    private   ProductData convertPojoToData(ProductPojo productPojo){
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setProduct(productPojo.getProduct());
        productData.setMrp(productPojo.getMRP());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(productPojo.getBrand());
        productData.setCategory(productPojo.getCategory());
        return productData;
    }

    private void validateProductEditForm(String barcode, ProductEditForm productEditForm) throws ApiException {
        ProductPojo productPojo = productService.selectByBarcode(barcode);
        System.out.println("inside validateProductEditForm : "+productEditForm.getMRP());
        if (productEditForm.getMRP() == null || productEditForm.getMRP().equals(0)) {
            productEditForm.setMRP(productEditForm.getMRP());
        }

        if(productEditForm.getProduct()==null || productEditForm.getProduct().equals("")){
            productEditForm.setProduct(productPojo.getProduct());
        }
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
