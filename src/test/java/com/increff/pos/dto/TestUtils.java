package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderItemPojo;

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

    protected static void setInventoryForm(InventoryForm inventoryForm,Integer quantity,String barcode){
        inventoryForm.setQuantity(quantity);
        inventoryForm.setBarcode(barcode);
    }

    protected  static  void setOrderItemForm(OrderItemForm orderItemForm,String orderCode,String barcode,Integer quantity,Double price){
        orderItemForm.setOrderCode(orderCode);
        orderItemForm.setQuantity(quantity);
        orderItemForm.setBarcode(barcode);
        orderItemForm.setPrice(price);
    }

    protected static void setEditOrderItemForm(EditOrderItemForm editOrderItemForm,String orderCode,String barcode,Integer quantity){
        editOrderItemForm.setOrderCode(orderCode);
        editOrderItemForm.setBarcode(barcode);
        editOrderItemForm.setQuantity(quantity);
    }

    protected static void setOrderItemPojo(OrderItemPojo orderItemPojo,Integer orderItemId,Integer productId,Integer orderId,Integer quantity,Double sellingPrice){
        orderItemPojo.setOrderItemId(orderItemId);
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setQuantity(quantity);
        orderItemPojo.setSellingPrice(sellingPrice);
        orderItemPojo.setProductId(productId);
    }
}
