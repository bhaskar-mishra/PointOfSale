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
    public void addProduct(@RequestBody ProductForm productForm) throws ApiException{
        productDto.addProduct(productForm);
    }

    @ApiOperation(value = "Gets product by barcode")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable String barcode) throws ApiException{
        return  productDto.getProductByBarcode(barcode);
    }

    @ApiOperation(value = "selects all products")
    @RequestMapping(path = "/api/product",method = RequestMethod.GET)
    public List<ProductData> getAllProducts() throws ApiException{
        return productDto.getAllProducts();
    }


    @ApiOperation(value = "updates a product")
    @RequestMapping(path = "/api/product",method = RequestMethod.PUT)
    public void updateProduct(@RequestBody ProductEditForm productEditForm) throws ApiException{
          productDto.updateProductWithGivenBarcode(productEditForm);
    }



}
