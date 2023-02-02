package com.increff.pos.controller;


import com.increff.pos.dto.BrandCategoryDto;
import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.BrandsReportForm;
import com.increff.pos.service.ApiException;
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


    @ApiOperation("Gets all the categories for a particular brand")
    @RequestMapping(path = "/api/brand/{brand}",method = RequestMethod.GET)
    public List<BrandCategoryData> getCategoriesForBrand(@PathVariable String brand) throws ApiException{
        return brandCategoryDto.getCategoriesForBrand(brand);
    }


    @ApiOperation(value = "Updates a brand category")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.PUT)
    public void updateBrandCategory(@PathVariable int id, @RequestBody BrandForm brandForm)throws ApiException{
        brandCategoryDto.updateBrandCategory(id,brandForm);
    }

    @ApiOperation(value = "gets a brand category by id")
    @RequestMapping(path = "/api/brand/getById/{id}",method = RequestMethod.GET)
    public BrandCategoryData getBrandCategoryById(@PathVariable Integer id) throws ApiException{
        return brandCategoryDto.getBrandCategoryById(id);
    }

}
