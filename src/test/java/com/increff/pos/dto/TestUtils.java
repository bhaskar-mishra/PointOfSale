package com.increff.pos.dto;

import com.increff.pos.model.BrandForm;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.ProductForm;

public class TestUtils {

    protected static void setBrandForm(BrandForm brandForm,String brand,String category){
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
    }

    protected static void setProductForm(ProductForm productForm,String brand,String category,String barcode, String product, Double price){
        productForm.setProduct(product);
        productForm.setBarcode(barcode);
        productForm.setMrp(price);
        productForm.setBrand(brand);
        productForm.setCategory(category);
    }

    protected  static void setProductEditForm(ProductEditForm productEditForm,String product,String barcode,Double mrp){
        productEditForm.setBarcode(barcode);
        productEditForm.setProduct(product);
        productEditForm.setMRP(mrp);
    }
}
