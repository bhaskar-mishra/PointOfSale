package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.pojo.InventoryPojo;

public class InventoryDto {

    public static InventoryData pojoToData(InventoryPojo inventoryPojo){
        InventoryData inventoryData = new InventoryData();
        inventoryData.setBarcode(inventoryPojo.getBarcode());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setProduct(inventoryPojo.getProduct());
        return inventoryData;
    }
}
