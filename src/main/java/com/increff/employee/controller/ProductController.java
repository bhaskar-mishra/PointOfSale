package com.increff.employee.controller;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductEditForm;
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
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDto productDto;

    @ApiOperation(value= "Adds a product")
    @RequestMapping(path = "/api/product",method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductForm form) throws ApiException{
        ProductPojo productPojo = productDto.convert(form);
        productService.add(productPojo);
    }

    @ApiOperation(value = "selects all products")
    @RequestMapping(path = "/api/product",method = RequestMethod.GET)
    public List<ProductData> get(@PathVariable(required = false) Integer id) throws ApiException{
        System.out.println("Inside get API");
        List<ProductPojo> p = productService.getAll();
        List<ProductData> productDataList = new ArrayList<>();
        for(ProductPojo pojo : p)
        {
            productDataList.add(productDto.convert(pojo));
        }

        return productDataList;
    }

    @ApiOperation(value = "Deletes a product")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.DELETE)
    public void deleteByBarcode(@PathVariable String barcode){
        productService.deleteByBarcode(barcode);}

    @ApiOperation(value = "updates a product")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.PUT)
    public void updateProductWithGivenBarcode(@PathVariable String barcode, @RequestBody ProductEditForm productEditForm) throws ApiException{
          productDto.validateProductEditForm(barcode,productEditForm);
          productService.updateProduct(barcode,productEditForm);
    }



}
