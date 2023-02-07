package com.increff.pos.controller;

import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds a product to the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm inventoryForm) throws ApiException {
        inventoryDto.addInventory(inventoryForm);
    }

    @ApiOperation("Gets all products in the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.GET)
    public List<InventoryData> getAllInventory() throws ApiException{
        return inventoryDto.getAllInventory();
    }

    @ApiOperation("Gets inventory for a particular product")
    @RequestMapping(path = "/api/inventory/{barcode}")
    public InventoryData getInventory(@PathVariable String barcode) throws ApiException{
        return inventoryDto.getInventory(barcode);
    }

    @ApiOperation("Updates inventory report")
    @RequestMapping(path = "/api/inventory/updateInventory",method = RequestMethod.PUT)
    public void updateInventory(@RequestBody InventoryForm inventoryForm) throws ApiException{
        inventoryDto.updateInventory(inventoryForm);
    }

}
