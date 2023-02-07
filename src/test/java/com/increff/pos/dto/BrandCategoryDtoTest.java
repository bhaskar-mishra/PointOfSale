package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class BrandCategoryDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @Test
    public void addBrandCategoryTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Store 99");
        brandForm.setCategory("BOOKS");
        brandCategoryDto.addBrandCategory(brandForm);
        BrandPojo brandPojo = brandCategoryDto.getByBrandCategory("Store 99","BOOKS");
        assertEquals("store 99",brandPojo.getBrand());
        assertEquals("books",brandPojo.getCategory());
    }

    @Test
    public void normalizeBrandFormTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("      BHaskaR");
        brandForm.setCategory("   MiShRa   ");
        brandCategoryDto.normalize(brandForm);
        assertEquals("bhaskar",brandForm.getBrand());
        assertEquals("mishra",brandForm.getCategory());
    }

    @Test(expected = ApiException.class)
    public void validateBrandFormTestBrandForNull() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(null);
        brandForm.setCategory("category");
        DtoUtils.validateBrandForm(brandForm);
    }

    @Test(expected = ApiException.class)
    public void validateBrandFormTestForEmptyBrand() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("      ");
        brandForm.setCategory("category");
        DtoUtils.validateBrandForm(brandForm);
    }

    @Test(expected = ApiException.class)
    public void validateBrandFormTestForCategoryNull() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory(null);
        brandForm.setBrand("brand");
        DtoUtils.validateBrandForm(brandForm);
    }

    @Test(expected = ApiException.class)
    public void validateBrandFormTestForEmptyCategory() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("    ");
        DtoUtils.validateBrandForm(brandForm);
    }

    @Test
    public void getByBrandCategoryTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("mishra");
        brandForm.setBrand("bhaskar");
        brandCategoryDto.addBrandCategory(brandForm);
        BrandPojo brandPojo = brandCategoryDto.getByBrandCategory("bhaskar","mishra");
        assertEquals("bhaskar",brandPojo.getBrand());
        assertEquals("mishra",brandPojo.getCategory());
    }

    @Test(expected = ApiException.class)
    public void getByBrandCategoryTestForBrandNull() throws ApiException{
        brandCategoryDto.getByBrandCategory(null,"category");
    }

    @Test(expected = ApiException.class)
    public void getByBrandCategoryTestForEmptyBrand() throws ApiException{
        brandCategoryDto.getByBrandCategory("   ","category");
    }

    @Test(expected = ApiException.class)
    public void getByBrandCategoryTestForCategoryNull() throws ApiException{
        brandCategoryDto.getByBrandCategory("brand",null);
    }

    @Test(expected = ApiException.class)
    public void getByBrandCategoryTestForEmptyCategory() throws ApiException{
        brandCategoryDto.getByBrandCategory("brand","   ");
    }

    @Test
    public void getCategoriesForBrandTest() throws ApiException{
        List<BrandForm> brandForms = Arrays.asList(new BrandForm(),new BrandForm());
        brandForms.get(0).setBrand("nestle");
        brandForms.get(0).setCategory("dairy");
        brandForms.get(1).setBrand("nestle");
        brandForms.get(1).setCategory("chocolates");
        brandCategoryDto.addBrandCategory(brandForms.get(0));
        brandCategoryDto.addBrandCategory(brandForms.get(1));
        List<BrandCategoryData> brandCategoryDataList = brandCategoryDto.getCategoriesForBrand("nestle");
        assertEquals(brandForms.get(0).getBrand(),brandCategoryDataList.get(0).getBrand());
        assertEquals(brandForms.get(0).getCategory(),brandCategoryDataList.get(0).getCategory());
        assertEquals(brandForms.get(1).getBrand(),brandCategoryDataList.get(1).getBrand());
        assertEquals(brandForms.get(1).getCategory(),brandCategoryDataList.get(1).getCategory());
    }

    @Test
    public void getAllBrandCategoriesTest() throws ApiException{
        List<BrandForm> brandForms = Arrays.asList(new BrandForm(),new BrandForm());
        brandForms.get(0).setBrand("nestle");
        brandForms.get(0).setCategory("noodles");
        brandForms.get(1).setBrand("apple");
        brandForms.get(1).setCategory("iphone");
        brandCategoryDto.addBrandCategory(brandForms.get(0));
        brandCategoryDto.addBrandCategory(brandForms.get(1));
        List<BrandCategoryData> brandCategoryDataList = brandCategoryDto.getAllBrandCategories();
        assertEquals(brandForms.get(0).getBrand(),brandCategoryDataList.get(0).getBrand());
        assertEquals(brandForms.get(0).getCategory(),brandCategoryDataList.get(0).getCategory());
        assertEquals(brandForms.get(1).getBrand(),brandCategoryDataList.get(1).getBrand());
        assertEquals(brandForms.get(1).getCategory(),brandCategoryDataList.get(1).getCategory());
    }

    @Test
    public void updateBrandCategoryTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("nestle");
        brandForm.setCategory("noodles");
        brandCategoryDto.addBrandCategory(brandForm);
        BrandPojo brandPojo = brandCategoryDto.getByBrandCategory("nestle","noodles");
        brandForm.setBrand("apple");
        brandForm.setCategory("iphone");
        brandCategoryDto.updateBrandCategory(brandPojo.getId(),brandForm);
        BrandCategoryData brandCategoryData = brandCategoryDto.getBrandCategoryById(brandPojo.getId());
        assertEquals("apple",brandCategoryData.getBrand());
        assertEquals("iphone",brandCategoryData.getCategory());
    }

    @Test
    public void getBrandCategoryById() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("nestle");
        brandForm.setCategory("noodles");
        brandCategoryDto.addBrandCategory(brandForm);
        BrandPojo brandPojo = brandCategoryDto.getByBrandCategory("nestle","noodles");
        BrandCategoryData brandCategoryData = brandCategoryDto.getBrandCategoryById(brandPojo.getId());
        assertEquals("nestle",brandCategoryData.getBrand());
        assertEquals("noodles",brandCategoryData.getCategory());
    }

    @Test
    public void convertBrandFormToPojoTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("nestle");
        brandForm.setCategory("noodles");
        BrandPojo brandPojo = DtoUtils.convertBrandFormToPojo(brandForm);
        assertEquals("nestle",brandPojo.getBrand());
        assertEquals("noodles",brandPojo.getCategory());
    }

    @Test
    public void convertBrandPojoToData() throws ApiException{
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("nestle");
        brandPojo.setCategory("noodles");
        brandPojo.setId(0);
        BrandCategoryData brandCategoryData = DtoUtils.convertBrandPojoToData(brandPojo);
        assertEquals("nestle",brandCategoryData.getBrand());
        assertEquals("noodles",brandCategoryData.getCategory());
        assertEquals(Integer.valueOf(0),brandCategoryData.getId());
    }
}
