package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

public class DtoUtils {


    //METHODS INVOKED FROM BRAND DTO
    protected static void validateBrandForm(BrandForm brandForm) throws ApiException {
        if(brandForm==null){
            throw new ApiException("Invalid form");
        }

        if(brandForm.getBrand()==null && brandForm.getCategory()==null){
            throw new ApiException("Invalid brand category");
        }

        if(brandForm.getBrand()==null || brandForm.getBrand().toLowerCase().trim().equals("")){
            throw new ApiException("invalid brand");
        }

        if(brandForm.getCategory()==null || brandForm.getCategory().toLowerCase().trim().equals("")){
            throw new ApiException("invalid category");
        }
    }

    protected static BrandPojo convertBrandFormToPojo(BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());

        return brandPojo;
    }

    protected static BrandCategoryData convertBrandPojoToData(BrandPojo brandPojo) throws ApiException {
        BrandCategoryData brandCategoryData = new BrandCategoryData();
        brandCategoryData.setId(brandPojo.getId());
        brandCategoryData.setBrand(brandPojo.getBrand());
        brandCategoryData.setCategory(brandPojo.getCategory());
        return brandCategoryData;
    }


    //METHODS INVOKED FROM PRODUCT DTO
    protected static void validateProductForm(ProductForm productForm) throws ApiException{
        if(productForm==null){
            throw new ApiException("productForm is null");
        }

        if(productForm.getBrand()==null || productForm.getBrand().toLowerCase().trim().equals("")){
            throw new ApiException("invalid brand category");
        }

        if(productForm.getCategory()==null || productForm.getCategory().toLowerCase().trim().equals("")){
            throw new ApiException("invalid brand category");
        }

        if(productForm.getProduct()==null || productForm.getProduct().toLowerCase().trim().equals("")){
            throw new ApiException("invalid product");
        }

        if(productForm.getCategory()==null || productForm.getCategory().toLowerCase().equals("")){
            throw new ApiException("invalid category");
        }

        if(productForm.getBarcode()==null || productForm.getBarcode().toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(productForm.getMrp()==null){
            throw new ApiException("MRP can't be null");
        }

        try{
            Double mrp = Double.parseDouble((""+productForm.getMrp()));
        }catch (NumberFormatException exception){
            throw new ApiException("MRP has to be numeric");
        }

        if(productForm.getMrp().compareTo(0.0)<=0){
            throw new ApiException("MRP should be a positive numeric value");
        }
    }

    protected static ProductPojo convertProductFormToPojo(ProductForm productForm,Integer brandCategoryId) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProduct(productForm.getProduct());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setBrand(productForm.getBrand());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setBrandCategoryId(brandCategoryId);
        return productPojo;
    }

    protected static ProductData convertProductPojoToData(ProductPojo productPojo){
        ProductData productData = new ProductData();
        productData.setProduct(productPojo.getProduct());
        productData.setMrp(productPojo.getMrp());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(productPojo.getBrand());
        productData.setCategory(productPojo.getCategory());
        return productData;
    }

    protected static void validateProductEditForm(ProductEditForm productEditForm) throws ApiException{
        if(productEditForm==null){
            throw new ApiException("invalid request");
        }

        if(productEditForm.getBarcode()==null || productEditForm.getBarcode().toLowerCase().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(productEditForm.getProduct()==null || productEditForm.getProduct().toLowerCase().trim().equals("")){
            throw new ApiException("product invalid");
        }

        if(productEditForm.getMRP()==null){
            throw new ApiException("mrp should be a positive numeric value");
        }

        try{
            Double mrp = Double.parseDouble(""+productEditForm.getMRP());
        }catch (Exception exception){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }

        if(productEditForm.getMRP().compareTo(0.0)<=0){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }
    }


    //METHODS INVOKED FROM INVENTORY DTO

    protected static void validateInventoryForm(InventoryForm inventoryForm) throws ApiException{
        if(inventoryForm==null){
            throw new ApiException("invalid request (form is null)");
        }

        if(inventoryForm.getBarcode()==null || inventoryForm.getBarcode().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(inventoryForm.getQuantity()==null){
            throw new ApiException("quantity unknown");
        }

        try {
            Integer quantity = Integer.parseInt((""+inventoryForm.getQuantity()));
        }catch (Exception exception){
            throw new ApiException("quantity should be a positive numeric value");
        }

        if(inventoryForm.getQuantity()<=0){
            throw new ApiException("quantity should be a positive numeric value");
        }
    }

    protected static InventoryData convertInventoryPojoToData(String product, InventoryPojo inventoryPojo,String barcode){
        InventoryData inventoryData = new InventoryData();
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setProduct(product);
        inventoryData.setBarcode(barcode);
        return inventoryData;
    }

    protected static InventoryPojo convertInventoryFormToPojo(InventoryForm inventoryForm,Integer id){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(id);
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        inventoryPojo.setBarcode("");
        inventoryPojo.setProduct("");
        return inventoryPojo;
    }


}
