package com.increff.employee.controller;

import com.increff.employee.model.*;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Adds a product to the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm form) throws ApiException {
        System.out.println("Reaching addInventory method");
        InventoryPojo p = convert(form);
        inventoryService.add(p);
    }

    @ApiOperation("Gets all products in the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.GET)
    public List<InventoryData> get() throws ApiException{
        return inventoryService.get();
    }

    @ApiOperation("Gets inventory details of all products")
    @RequestMapping(path = "/api/inventory/getInventoryAll",method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryDetails() throws ApiException{
        return inventoryService.getInventoryDetailsOfAllProducts();
    }

    @ApiOperation("Gets inventory report on userInput")
    @RequestMapping(path = "/api/inventory/inventoryReport",method = RequestMethod.POST)
    public List<InventoryReportData> getInventoryReport(@RequestBody InventoryReportForm inventoryReportForm) throws ApiException{
        return inventoryService.getInventoryReport(inventoryReportForm);
    }

    @ApiOperation("Updates inventory report")
    @RequestMapping(path = "/api/inventory/updateInventory/{barcode}",method = RequestMethod.PUT)
    public void updateInventoryForAGivenBarcode(@PathVariable String barcode,@RequestBody InventoryUpdateForm inventoryUpdateForm) throws ApiException{
        System.out.println("reaching updateInventoryForAGivenBarcode in inventory controller");
        System.out.println("barcode : "+barcode);
        System.out.println("quantity : "+inventoryUpdateForm.getQuantity());
        inventoryService.updateInventoryForAGivenBarcode(barcode,inventoryUpdateForm);
    }

    private static InventoryPojo convert(InventoryForm inventoryForm){
        System.out.println("Form being converted to Pojo");
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setBarcode(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

}
