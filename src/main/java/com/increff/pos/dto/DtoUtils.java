package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.*;
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
        productPojo.setBrand("");
        productPojo.setCategory("");
        productPojo.setBrandCategoryId(brandCategoryId);
        return productPojo;
    }

    protected static ProductData convertProductPojoToData(ProductPojo productPojo,String brand,String category){
        ProductData productData = new ProductData();
        productData.setProduct(productPojo.getProduct());
        productData.setMrp(productPojo.getMrp());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(brand);
        productData.setCategory(category);
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

    protected static void normalizeProductForm(ProductForm productForm){
        productForm.setProduct(productForm.getProduct().toLowerCase().trim());
        productForm.setBrand(productForm.getBrand().toLowerCase().trim());
        productForm.setCategory(productForm.getCategory().toLowerCase().trim());
        productForm.setBarcode(productForm.getBarcode().toLowerCase().trim());
    }

    protected static void normalizeProductEditForm(ProductEditForm productEditForm){
        productEditForm.setProduct(productEditForm.getProduct().toLowerCase().trim());
        productEditForm.setBarcode(productEditForm.getBarcode().toLowerCase().trim());
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
        inventoryPojo.setProductId(id);
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

    protected static void normalizeInventoryForm(InventoryForm inventoryForm){
        inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());
    }

    //ORDER DTO INVOKES THESE METHODS
    protected static OrderData convertOrderPojoToData(OrderPojo orderPojo) throws ApiException{
        OrderData orderData = new OrderData();
        orderData.setOrderId(orderPojo.getOrderId());
        orderData.setOrderCode(orderPojo.getOrderCode());
        orderData.setTime(orderPojo.getPlacedTime());
        if(orderPojo.getStatus().equals("PENDING")){
            orderData.setStatus("PENDING");
        }else {
            orderData.setStatus("PLACED");
        }
        return orderData;
    }


    //ORDER ITEM DTO INVOKES THESE METHODS

    protected static void validateOrderItemForm(OrderItemForm orderItemForm) throws ApiException{
        if(orderItemForm==null){
            throw new ApiException("invalid request : no input");
        }

        if(orderItemForm.getOrderCode()==null
                || orderItemForm.getOrderCode().trim().equals("")
                ||orderItemForm.getOrderCode().trim().split(" ").length!=1){
            throw new ApiException("invalid orderCode");
        }

        if(orderItemForm.getBarcode()==null || orderItemForm.getBarcode().toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(orderItemForm.getQuantity()==null){
            throw new ApiException("quantity unknown");
        }

        try{
            Integer quantity = Integer.parseInt(""+orderItemForm.getQuantity());
        }catch (Exception exception){
            throw new ApiException("quantity should be a positive numeric value");
        }

        if(orderItemForm.getQuantity()<=0){
            throw new ApiException("quantity should be a positive numeric value");
        }

        if(orderItemForm.getPrice()==null){
            throw new ApiException("price unknown");
        }

        try{
            Double price = Double.parseDouble(""+orderItemForm.getPrice());
        }catch (Exception e){
            throw new ApiException("price should be a positive numeric value");
        }

        if(orderItemForm.getPrice().compareTo(0.0)<=0){
            throw new ApiException("price should be a positive numeric value");
        }

    }

    protected static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm orderItemForm, Integer productId, Integer orderId) throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setSellingPrice(orderItemForm.getPrice());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setProductId(productId);
        return orderItemPojo;
    }


    protected static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo, String product, String barcode) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setOrderItemId(orderItemPojo.getOrderItemId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setProduct(product);
        orderItemData.setBarcode(barcode);
        orderItemData.setPrice(orderItemPojo.getSellingPrice());
        orderItemData.setTotal((orderItemPojo.getSellingPrice()*orderItemPojo.getQuantity()));
        return orderItemData;
    }

    protected static void validateEditOrderItemForm(EditOrderItemForm editOrderItemForm) throws ApiException{

        if(editOrderItemForm==null){
            throw new ApiException("invalid edit request(form null)");
        }

        if(editOrderItemForm.getOrderCode()==null
                || editOrderItemForm.getOrderCode().trim().equals("")
        || editOrderItemForm.getOrderCode().trim().split(" ").length!=1){
            throw new ApiException("invalid order code");
        }

        if(editOrderItemForm.getBarcode()==null || editOrderItemForm.getBarcode().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(editOrderItemForm.getQuantity()==null){
            throw new ApiException("quantity unknown");
        }

        try {
            Integer quantity = Integer.parseInt(""+editOrderItemForm.getQuantity());
        }catch (Exception exception){
            throw new ApiException("quantity should be a positive numeric value");
        }

        if(editOrderItemForm.getQuantity()<0){
            throw new ApiException("quantity should be a positive numeric value");
        }

    }

    //METHODS INVOKED FROM REPORTS DTO
    protected static void validateSalesReportForm(SalesReportForm salesReportForm) throws ApiException{
        if(salesReportForm.getStartDate()==null || salesReportForm.getEndDate()==null){
            throw new ApiException("please input valid start and end dates");
        }

        if(salesReportForm.getBrand()==null || salesReportForm.getBrand().trim().equals("")){
            throw new ApiException("input a valid brand");
        }

        if(salesReportForm.getCategory()==null || salesReportForm.getCategory().trim().equals("")){
            throw new ApiException("input a valid category");
        }
    }


}
