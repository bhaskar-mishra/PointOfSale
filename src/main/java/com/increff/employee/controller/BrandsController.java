package com.increff.employee.controller;


import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.BrandsReportForm;
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
public class BrandsController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand",method = RequestMethod.POST)
    public void addBrand(@RequestBody BrandForm form) throws ApiException{
        BrandPojo p = BrandCategoryDto.convert(form);
        brandService.add(p);
    }


    @ApiOperation("Gets list of all the brand categories")
    @RequestMapping(path = "/api/brand",method = RequestMethod.GET)
    public List<BrandCategoryData> getAllBrandCateogry() throws ApiException {
        System.out.println("Inside getAllBrandCategory method in /api/brand");
        List<BrandPojo> list = brandService.getAll();
        List<BrandCategoryData> list2 = new ArrayList<>();
        for(BrandPojo p : list){
            list2.add(BrandCategoryDto.convert(p));
        }

        return list2;
    }

    //GET Brands report
    @ApiOperation("gets brand report")
    @RequestMapping(path = "/api/brands/brandsReport",method = RequestMethod.POST)
    public List<BrandCategoryData> getBrandsReport(@RequestBody BrandsReportForm brandsReportForm) throws ApiException{
        return brandService.getBrandsReport(brandsReportForm);
    }



    @ApiOperation("Gets all the brands")
    @RequestMapping(path = "/api/brand/allBrands",method = RequestMethod.GET)
    public List<String> getAllBrands() throws ApiException{
        return brandService.getAllBrands();
    }



    @ApiOperation("Gets all the categories")
    @RequestMapping(path = "/api/brand/allCategories",method = RequestMethod.GET)
    public List<String> getAllCategory() throws ApiException{
        return brandService.getAllCategories();
    }



    @ApiOperation("Gets all the categories for a particular brand")
    @RequestMapping(path = "/api/brand/{brand}",method = RequestMethod.GET)
    public List<String> getCategoriesForBrand(@PathVariable String brand) throws ApiException{
        System.out.println("Reaching getCategoriesForBrand method" + brand);
        return brandService.getCategoriesForBrand(brand);

    }



    @ApiOperation(value = "gets brandCategoryId for a given brand-category")
    @RequestMapping(path = "/api/brand/{brand}/{category}",method = RequestMethod.GET)
    public int getBrandCategoryId(@PathVariable String brand,@PathVariable String category) throws ApiException{
        return brandService.getBrandCategoryId(brand,category);

    }



    @ApiOperation(value = "Deletes a brand category")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        brandService.delete(id);
    }



    @ApiOperation(value = "Updates a brand category")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody BrandForm brandForm)throws ApiException{
        brandService.update(id,brandForm);
    }



}
