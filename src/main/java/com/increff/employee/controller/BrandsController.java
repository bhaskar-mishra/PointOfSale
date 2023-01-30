package com.increff.employee.controller;


import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.BrandsReportForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandsController {

    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand",method = RequestMethod.POST)
    public void addBrandCategory(@RequestBody BrandForm brandForm) throws ApiException{
        brandCategoryDto.addBrandCategory(brandForm);
    }

    @ApiOperation("Gets list of all the brand categories")
    @RequestMapping(path = "/api/brand",method = RequestMethod.GET)
    public List<BrandCategoryData> getAllBrandCateogries() throws ApiException {
        return brandCategoryDto.getAllBrandCategories();
    }

    //GET Brands report
    @ApiOperation("gets brand report")
    @RequestMapping(path = "/api/brands/brandsReport",method = RequestMethod.POST)
    public List<BrandCategoryData> getBrandsReport(@RequestBody BrandsReportForm brandsReportForm) throws ApiException{
        return brandCategoryDto.getBrandsReport(brandsReportForm);
    }

    @ApiOperation("Gets all the brands")
    @RequestMapping(path = "/api/brand/allBrands",method = RequestMethod.GET)
    public List<String> getAllBrands() throws ApiException{
        return brandCategoryDto.getAllBrands();
    }

    @ApiOperation("Gets all the categories")
    @RequestMapping(path = "/api/brand/allCategories",method = RequestMethod.GET)
    public List<String> getAllCategory() throws ApiException{
        return brandCategoryDto.getAllCategories();
    }

    @ApiOperation("Gets all the categories for a particular brand")
    @RequestMapping(path = "/api/brand/{brand}",method = RequestMethod.GET)
    public List<String> getCategoriesForBrand(@PathVariable String brand) throws ApiException{
        return brandCategoryDto.getCategoriesForBrand(brand);
    }

    @ApiOperation(value = "gets brandCategoryId for a given brand-category")
    @RequestMapping(path = "/api/brand/{brand}/{category}",method = RequestMethod.GET)
    public Integer getBrandCategoryId(@PathVariable String brand,@PathVariable String category) throws ApiException{
        return brandCategoryDto.getBrandCategoryId(brand,category);
    }

    @ApiOperation(value = "Updates a brand category")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.PUT)
    public void updateBrandCategory(@PathVariable int id, @RequestBody BrandForm brandForm)throws ApiException{
        brandCategoryDto.updateBrandCategory(id,brandForm);
    }



}
