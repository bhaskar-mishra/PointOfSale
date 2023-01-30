package com.increff.employee.controller;

import com.increff.employee.dto.InventoryDto;
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

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds a product to the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm inventoryForm) throws ApiException {
        inventoryDto.addInventory(inventoryForm);
    }

    @ApiOperation("Gets all products in the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.GET)
    public List<InventoryData> getAllProductsInInventory() throws ApiException{
        return inventoryDto.getAllProductsInInventory();
    }


    // It prints inventory of all the products
    @ApiOperation("Gets inventory details of all products")
    @RequestMapping(path = "/api/inventory/getInventoryAll",method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryDetails() throws ApiException{
        return inventoryDto.getInventoryDetailsOfAllProducts();
    }


    //prints inventory of products input by the user
    @ApiOperation("Gets inventory report on userInput")
    @RequestMapping(path = "/api/inventory/inventoryReport",method = RequestMethod.POST)
    public List<InventoryReportData> getInventoryReport(@RequestBody InventoryReportForm inventoryReportForm) throws ApiException{
        return inventoryDto.getInventoryReport(inventoryReportForm);
    }

    @ApiOperation("Updates inventory report")
    @RequestMapping(path = "/api/inventory/updateInventory/{barcode}",method = RequestMethod.PUT)
    public void updateInventoryForAGivenBarcode(@PathVariable String barcode,@RequestBody InventoryUpdateForm inventoryUpdateForm) throws ApiException{
        inventoryDto.updateInventoryForAGivenBarcode(barcode,inventoryUpdateForm);
    }

}
