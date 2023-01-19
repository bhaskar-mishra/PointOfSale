package com.increff.employee.controller;

import com.increff.employee.model.BrandForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryServiceController {

    @Autowired
    private InventoryService service;

    @ApiOperation(value = "Adds a product to the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm form) throws ApiException {
        System.out.println("Reaching addInventory method");
        InventoryPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation("Gets all products in the inventory")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.GET)
    public List<InventoryData> get() throws ApiException{
        return service.get();
    }

//    @ApiOperation(value = "Updates a product inventory")
//    @RequestMapping(path = "/api/inventory-serviceController/{id}",method = RequestMethod.PUT)
//    public void update(@PathVariable int id,@RequestBody InventoryForm f)throws ApiException{
//        InventoryPojo p = service.get(id);
//        int quantity = p.getQuantity()-f.getQuantity();
//        p.setQuantity(quantity);
//        service.update(id,p);
//    }

    private static InventoryPojo convert(InventoryForm inventoryForm){
        System.out.println("Form being converted to Pojo");
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setBarcode(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

}
