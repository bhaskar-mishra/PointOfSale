package com.increff.employee.controller;


import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class BrandServiceController {

    @Autowired
    private BrandService service;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand-serviceController",method = RequestMethod.POST)
    public void addBrand(@RequestBody BrandForm form) throws ApiException{
        BrandPojo p = convert(form);
        service.add(p);
    }

    @ApiOperation("Gets a brand category by ID")
    @RequestMapping(path = "/api/brand-serviceController/{id}",method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException{
        BrandPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation("Gets list of all the brand categories")
    @RequestMapping(path = "/api/brand-serviceController",method = RequestMethod.GET)
    public List<BrandData> getAll(){
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<>();
        for(BrandPojo p : list){
            list2.add(convert(p));
        }

        return list2;
    }

    @ApiOperation(value = "Deletes a brand category")
    @RequestMapping(path = "/api/brand-serviceController/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){service.delete(id);}

    @ApiOperation(value = "Updates a brand category")
    @RequestMapping(path = "/api/brand-serviceController/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody BrandForm f)throws ApiException{
        BrandPojo p = convert(f);
        service.update(id,p);
    }

    private static BrandPojo convert(BrandForm f){
        BrandPojo p = new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());

        return p;
    }

    private static BrandData convert(BrandPojo p)
    {
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
}
