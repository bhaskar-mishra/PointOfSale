package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.InventoryReportForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
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
