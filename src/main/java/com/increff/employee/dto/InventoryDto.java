package com.increff.employee.dto;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.helper.InventoryReportHelper;
import com.increff.employee.model.*;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
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
        normalize(inventoryForm);
        InventoryPojo inventoryPojo = convertFormToPojo(inventoryForm);
        inventoryService.add(inventoryPojo);
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

    @Transactional
    public void updateInventoryForAGivenBarcode(String barcode, InventoryUpdateForm inventoryUpdateForm) throws ApiException{
        barcode = barcode.toLowerCase().trim();
        inventoryService.updateInventoryForAGivenBarcode(barcode,inventoryUpdateForm);
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

    private InventoryPojo convertFormToPojo(InventoryForm inventoryForm){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setBarcode(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

    public static InventoryData pojoToData(InventoryPojo inventoryPojo){
        InventoryData inventoryData = new InventoryData();
        inventoryData.setBarcode(inventoryPojo.getBarcode());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setProduct(inventoryPojo.getProduct());
        return inventoryData;
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
