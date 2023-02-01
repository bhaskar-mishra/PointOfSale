package com.increff.pos.dto;

import com.increff.pos.model.InventoryReportData;
import com.increff.pos.model.InventoryReportForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Service;


@Service
public class InventoryReportDto {
    public static InventoryReportData inventoryReportFormToData(InventoryReportForm inventoryReportForm, Integer quantity){
        InventoryReportData inventoryReportData = new InventoryReportData();
        inventoryReportData.setQuantity(quantity);
        inventoryReportData.setBrand(inventoryReportForm.getBrand());
        inventoryReportData.setCategory(inventoryReportForm.getCategory());
        inventoryReportData.setProduct(inventoryReportForm.getProduct());
        return inventoryReportData;
    }

    public static InventoryReportData productToInventoryReportData(ProductPojo productPojo,Integer quantity) throws ApiException{
        InventoryReportData inventoryReportData = new InventoryReportData();
        inventoryReportData.setProduct(productPojo.getProduct());
        inventoryReportData.setBrand(productPojo.getBrand());
        inventoryReportData.setQuantity(quantity);
        inventoryReportData.setCategory(productPojo.getCategory());
        return inventoryReportData;
    }
}
