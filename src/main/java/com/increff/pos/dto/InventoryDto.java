package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;


    public void addInventory(InventoryForm inventoryForm) throws ApiException{
        DtoUtils.validateInventoryForm(inventoryForm);
        DtoUtils.normalizeInventoryForm(inventoryForm);
        ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = DtoUtils.convertInventoryFormToPojo(inventoryForm,productPojo.getId());
        inventoryService.add(inventoryPojo);
    }


    public InventoryData getInventory(String barcode) throws ApiException{
        if(barcode==null || barcode.toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        ProductPojo productPojo = productService.selectByBarcode(barcode);

        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());
        return DtoUtils.convertInventoryPojoToData(productPojo.getProduct(),inventoryPojo,barcode);
    }

    @Transactional
    public void updateInventory(InventoryForm inventoryForm) throws ApiException{
        DtoUtils.validateInventoryForm(inventoryForm);
        DtoUtils.normalizeInventoryForm(inventoryForm);
        ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());
        inventoryService.updateInventory(productPojo.getId(),inventoryForm.getQuantity());
    }

    public List<InventoryData> getAllInventory() throws ApiException{
        List<InventoryPojo> inventoryPojoList =  inventoryService.getAllInventory();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList){
            ProductPojo productPojo = productService.selectByProductId(inventoryPojo.getProductId());
            inventoryDataList.add(DtoUtils.convertInventoryPojoToData(productPojo.getProduct(),inventoryPojo,productPojo.getBarcode()));
        }
        return inventoryDataList;
    }




}
