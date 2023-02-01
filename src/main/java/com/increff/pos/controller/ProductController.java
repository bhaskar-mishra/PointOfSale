package com.increff.pos.controller;

import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
          productDto.updateProduct(productEditForm);
    }



}
