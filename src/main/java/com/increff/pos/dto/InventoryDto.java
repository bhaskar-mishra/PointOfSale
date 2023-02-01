package com.increff.pos.dto;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.helper.InventoryReportHelper;
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
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;

    public void addInventory(InventoryForm inventoryForm) throws ApiException{
        DtoUtils.validateInventoryForm(inventoryForm);
        normalize(inventoryForm);
        ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = DtoUtils.convertInventoryFormToPojo(inventoryForm,productPojo.getId());
        inventoryPojo.setProduct(productPojo.getProduct()); // delete product field from inventory and therefore this line
        inventoryService.add(inventoryPojo);
    }


    public InventoryData getInventory(String barcode) throws ApiException{
        if(barcode==null || barcode.toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        ProductPojo productPojo = productService.selectByBarcode(barcode);

        InventoryPojo inventoryPojo = inventoryService.getInventoryById(productPojo.getId());
        return DtoUtils.convertInventoryPojoToData(productPojo.getProduct(),inventoryPojo,barcode);
    }

    @Transactional
    public void updateInventory(InventoryForm inventoryForm) throws ApiException{
        DtoUtils.validateInventoryForm(inventoryForm);
        normalize(inventoryForm);
        ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());
        inventoryService.updateInventory(productPojo.getId(),inventoryForm.getQuantity());
    }

    public List<InventoryData> getAllInventory() throws ApiException{
        List<InventoryPojo> inventoryPojoList =  inventoryService.getAllInventory();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList){
            ProductPojo productPojo = productService.selectById(inventoryPojo.getId());
            inventoryDataList.add(DtoUtils.convertInventoryPojoToData(productPojo.getProduct(),inventoryPojo,productPojo.getBarcode()));
        }
        return inventoryDataList;
    }





    @Transactional
    public List<InventoryReportData> getInventoryDetailsOfAllProducts() throws ApiException{
        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
        List<ProductPojo> productPojoList = productDao.selectAll();
        for(ProductPojo productPojo : productPojoList){
            InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
            Integer quantity;
            if(inventoryPojo==null){
                quantity = 0;
            }else {
                quantity = inventoryPojo.getQuantity();
            }
            inventoryReportDataList.add(convertProductToInventoryReportData(productPojo,quantity));
        }
        return inventoryReportDataList;
    }

    @Transactional
    public List<InventoryReportData> getInventoryReport(InventoryReportForm inventoryReportForm) throws ApiException{
            normalize(inventoryReportForm);
            List<InventoryReportHelper> inventoryReportHelperList = inventoryService.getInventoryReport(inventoryReportForm);
            List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
            for(InventoryReportHelper inventoryReportHelper : inventoryReportHelperList){
                inventoryReportDataList.add(convertInventoryReportFormToData(inventoryReportHelper.getInventoryReportForm(), inventoryReportHelper.getQuantity()));
            }

            return inventoryReportDataList;
    }


    public  InventoryReportData convertInventoryReportFormToData(InventoryReportForm inventoryReportForm, Integer quantity){
        InventoryReportData inventoryReportData = new InventoryReportData();
        inventoryReportData.setQuantity(quantity);
        inventoryReportData.setBrand(inventoryReportForm.getBrand());
        inventoryReportData.setCategory(inventoryReportForm.getCategory());
        inventoryReportData.setProduct(inventoryReportForm.getProduct());
        return inventoryReportData;
    }

    private InventoryReportData convertProductToInventoryReportData(ProductPojo productPojo,Integer quantity){
        InventoryReportData inventoryReportData = new InventoryReportData();
        inventoryReportData.setProduct(productPojo.getProduct());
        inventoryReportData.setBrand(productPojo.getBrand());
        inventoryReportData.setQuantity(quantity);
        inventoryReportData.setCategory(productPojo.getCategory());
        return inventoryReportData;
    }


    private void normalize(InventoryForm inventoryForm){
        inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());
    }

    private void normalize(InventoryReportForm inventoryReportForm) {
        inventoryReportForm.setProduct(inventoryReportForm.getProduct().toLowerCase().trim());
        inventoryReportForm.setBrand(inventoryReportForm.getBrand().toLowerCase().trim());
        inventoryReportForm.setCategory(inventoryReportForm.getCategory().toLowerCase().trim());
    }


}
