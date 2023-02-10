package com.increff.pos.dto;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;

public class DtoUtils {


    //METHODS INVOKED FROM BRAND DTO
    protected static void validateBrandForm(BrandForm brandForm) throws ApiException {

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

    protected static void validateProductEditForm(ProductUpdateForm productUpdateForm) throws ApiException{

        if(productUpdateForm.getBarcode()==null || productUpdateForm.getBarcode().toLowerCase().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(productUpdateForm.getProduct()==null || productUpdateForm.getProduct().toLowerCase().trim().equals("")){
            throw new ApiException("product invalid");
        }

        if(productUpdateForm.getMRP()==null){
            throw new ApiException("mrp should be a positive numeric value");
        }

        try{
            Double mrp = Double.parseDouble(""+ productUpdateForm.getMRP());
        }catch (Exception exception){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }

        if(productUpdateForm.getMRP().compareTo(0.0)<=0){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }
    }

    protected static void normalizeProductForm(ProductForm productForm){
        productForm.setProduct(productForm.getProduct().toLowerCase().trim());
        productForm.setBrand(productForm.getBrand().toLowerCase().trim());
        productForm.setCategory(productForm.getCategory().toLowerCase().trim());
        productForm.setBarcode(productForm.getBarcode().toLowerCase().trim());
    }

    protected static void normalizeProductEditForm(ProductUpdateForm productUpdateForm){
        productUpdateForm.setProduct(productUpdateForm.getProduct().toLowerCase().trim());
        productUpdateForm.setBarcode(productUpdateForm.getBarcode().toLowerCase().trim());
    }


    //METHODS INVOKED FROM INVENTORY DTO

    protected static void validateInventoryForm(InventoryForm inventoryForm) throws ApiException{

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

        if(inventoryForm.getQuantity()<0){
            throw new ApiException("quantity can't be negative");
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

    protected static void validateEditOrderItemForm(UpdateOrderItemForm updateOrderItemForm) throws ApiException{


        if(updateOrderItemForm.getOrderCode()==null
                || updateOrderItemForm.getOrderCode().trim().equals("")
        || updateOrderItemForm.getOrderCode().trim().split(" ").length!=1){
            throw new ApiException("invalid order code");
        }

        if(updateOrderItemForm.getBarcode()==null || updateOrderItemForm.getBarcode().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(updateOrderItemForm.getQuantity()==null){
            throw new ApiException("quantity unknown");
        }

        try {
            Integer quantity = Integer.parseInt(""+updateOrderItemForm.getQuantity());
        }catch (Exception exception){
            throw new ApiException("quantity should be a positive numeric value");
        }

        if(updateOrderItemForm.getQuantity()<0){
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


    //METHODS INVOKED FROM AdminApiController
    protected static UserData convertUserPojoToData(UserPojo userPojo){
        UserData userData = new UserData();
        userData.setEmail(userPojo.getEmail());
        userData.setRole(userPojo.getRole());
        userData.setId(userPojo.getId());
        return userData;
    }

    protected static UserPojo convertUserFormToPojo(UserForm userForm){
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail(userForm.getEmail());
        userPojo.setRole(userForm.getRole());
        userPojo.setPassword(userForm.getPassword());
        return userPojo;
    }

    protected static void normalizeUserForm(UserForm userForm) {
        userForm.setEmail(userForm.getEmail().toLowerCase().trim());
        userForm.setRole(userForm.getRole().toLowerCase().trim());
    }

    protected static void validateUserForm(UserForm userForm) throws ApiException{
        if(userForm.getEmail()==null || userForm.getEmail().trim().equals("")){
            throw new ApiException("invalid email");
        }

        if(userForm.getPassword()==null || userForm.getPassword().trim().equals("")){
            throw new ApiException("invalid password");
        }

        if(userForm.getRole()==null || userForm.getRole().trim().equals("")){
            throw new ApiException("invalid role");
        }
    }


}
