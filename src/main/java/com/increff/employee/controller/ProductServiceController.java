package com.increff.employee.controller;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductServiceController {
    @Autowired
    private ProductService service;

    @ApiOperation(value= "Adds a product")
    @RequestMapping(path = "/api/product",method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductForm form) throws ApiException{
        System.out.println(form.getBarcode());
        ProductPojo p = convert(form);
        System.out.println(p.getBarcode());
        service.add(p);
    }

    @ApiOperation(value = "selects all products")
    @RequestMapping(path = "/api/product",method = RequestMethod.GET)
    public List<ProductData> get(@PathVariable(required = false) Integer id) throws ApiException{
        System.out.println("Inside get API");
        List<ProductPojo> p =service.getAll();
        List<ProductData> productDataList = new ArrayList<>();
        for(ProductPojo pojo : p)
        {
            productDataList.add(convert(pojo));
        }

        return productDataList;
    }

    @ApiOperation(value = "Deletes a product")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String barcode){service.delete(barcode);}

    @ApiOperation(value = "updates a product")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.PUT)
    public void update(@PathVariable String barcode,@RequestBody ProductForm f) throws ApiException{
        ProductPojo p = convert(f);
        service.update(barcode,p);
    }


    private static ProductPojo convert(ProductForm f){
        ProductPojo p = new ProductPojo();
        p.setBrandCategoryId(f.getBrandCategoryId());
        p.setProduct(f.getProduct());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    private static ProductData convert(ProductPojo p){
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setProduct(p.getProduct());
        d.setBrandCategoryId(p.getBrandCategoryId());
        d.setMrp(p.getMrp());
        d.setBarcode(p.getBarcode());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
}
