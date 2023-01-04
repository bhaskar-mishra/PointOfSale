package com.increff.employee.controller;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class ProductServiceController {
    @Autowired
    private ProductService service;

    @ApiOperation(value= "Adds a brand")
    @RequestMapping(path = "/api/product-serviceController",method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductForm form) throws ApiException{
        System.out.println(form.getBarCode());
        ProductPojo p = convert(form);
        System.out.println(p.getBarCode());
        service.add(p);
    }

    @ApiOperation(value = "select a product with given barcode")
    @RequestMapping(path = "/api/product-serviceController/{barcode}",method = RequestMethod.GET)
    public ProductData get(@PathVariable String barcode) throws ApiException{
        ProductPojo p =service.get(barcode);
        return convert(p);
    }

    @ApiOperation(value = "Deletes a product")
    @RequestMapping(path = "/api/product-serviceController/{barcode}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String barcode){service.delete(barcode);}

    @ApiOperation(value = "updates a product")
    @RequestMapping(path = "/api/product-serviceController/{barcode}",method = RequestMethod.PUT)
    public void update(@PathVariable String barcode,@RequestBody ProductForm f) throws ApiException{
        ProductPojo p = convert(f);
        service.update(barcode,p);
    }
    private static ProductPojo convert(ProductForm f){
        ProductPojo p = new ProductPojo();
        p.setBrand_category(f.getBrand_category());
        p.setName(f.getName());
        p.setBarCode(f.getBarCode());
        p.setMrp(f.getMrp());
        return p;
    }

    private static ProductData convert(ProductPojo p){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setName(p.getName());
        d.setBrand_category(p.getBrand_category());
        d.setMrp(p.getMrp());
        return d;
    }
}
