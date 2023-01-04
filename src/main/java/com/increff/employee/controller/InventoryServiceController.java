package com.increff.employee.controller;

import com.increff.employee.model.BrandForm;
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

@Api
@RestController
public class InventoryServiceController {

    @Autowired
    private InventoryService service;

    @ApiOperation(value = "Adds a product to the inventory")
    @RequestMapping(path = "/api/inventory-serviceController",method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation("Gets a product inventory by id")
    @RequestMapping(path = "/api/inventory-serviceController/{id}",method = RequestMethod.GET)
    public InventoryPojo get(@PathVariable int id) throws ApiException{
        return service.get(id);
    }

    @ApiOperation(value = "Updates a product inventory")
    @RequestMapping(path = "/api/inventory-serviceController/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody InventoryForm f)throws ApiException{
        InventoryPojo p = service.get(id);
        int quantity = p.getQuantity()-f.getQuantity();
        p.setQuantity(quantity);
        service.update(id,p);
    }

    private static InventoryPojo convert(InventoryForm f){
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        p.setId(f.getId());
        return p;
    }

}
