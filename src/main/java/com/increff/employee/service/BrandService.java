package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.BrandsReportForm;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;


    @Transactional
    public void add(BrandPojo p) throws ApiException {
        boolean unique = brandDao.checkUnique(p.getBrand(), p.getCategory());
        if (!unique) {
            throw new ApiException("This brand category already exists");
        }
        brandDao.insert(p);
    }

    @Transactional
    public List<BrandPojo> getAll() throws ApiException {
        return brandDao.selectAll();
    }

    @Transactional
    public List<String> getAllBrands() throws ApiException{
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        Set<String> brandSet = new HashSet<>();
        List<String> brandsList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            brandSet.add(brandPojo.getBrand());
        }

        for(String brand : brandSet){
            brandsList.add(brand);
        }

        return brandsList;
    }

    @Transactional
    public List<String> getAllCategories() throws ApiException{
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        Set<String> categorySet = new HashSet<>();
        List<String> categoryList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            categorySet.add(brandPojo.getCategory());
        }

        for(String brand : categorySet){
            categoryList.add(brand);
        }

        return categoryList;
    }

    @Transactional
    public List<String> getCategoriesForBrand(String brand) throws ApiException{
        List<BrandPojo> brandPojoList = getAll();
        List<String> categoriesForGivenBrand = new ArrayList<>();
        for(BrandPojo pojo : brandPojoList)
        {
            if(pojo.getBrand().equals(brand)) {
                categoriesForGivenBrand.add(pojo.getCategory());
            }
        }

        return categoriesForGivenBrand;
    }

    @Transactional
    public Integer getBrandCategoryId(String brand,String category) throws ApiException
    {

        System.out.println("inside getBrandCategory");
        List<BrandPojo> brandPojoList = getAll();

        Integer id = -1;
        for(BrandPojo brandPojo : brandPojoList)
        {
            if(brandPojo.getBrand().equals(brand) && brandPojo.getCategory().equals(category))
            {
                id = brandPojo.getId();
                break;
            }
        }

        return id;
    }


    @Transactional
    public void delete(int id) {
        brandDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateBrandCategory(int id, BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = getCheck(id);

        String brand = brandForm.getBrand();
        String category = brandForm.getCategory();

        if(brand==null || brand.equals("")){
            brand = brandPojo.getBrand();
        }

        if(category==null || category.equals("")){
            category = brandPojo.getCategory();
        }


        Integer brandCategoryId = getBrandCategoryId(brand,category);

        if(brandCategoryId.equals(-1)){
            brandDao.updateBrandCategory(id,brand,category);
        }else if(brandCategoryId.equals(id)){
            brandDao.updateBrandCategory(id,brand,category);
        }else {
            delete(id);
        }
    }


    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo brandPojo = brandDao.selectById(id);
        if (brandPojo == null) {
            throw new ApiException("Brand category with given id does not exist, id: " + id);
        }
        return brandPojo;
    }


    @Transactional
    public List<BrandPojo> getBrandsReport(BrandsReportForm brandsReportForm) throws ApiException{

        String brand = brandsReportForm.getBrand();
        String category = brandsReportForm.getCategory();

        if(brand==null) brand = "";
        if(category==null) category = "";

        List<BrandPojo>  brandPojoArrayList = new ArrayList<>();;

        if(!brand.equals("") && !category.equals("")){
            BrandPojo brandPojo = brandDao.selectByBrandCategory(brand,category);
            if(brandPojo!=null){
                brandPojoArrayList.add(brandPojo);
            }
        }else if(brand.equals("")){
            List<BrandPojo> brandPojoList = brandDao.selectByCategory(category);
            for(BrandPojo brandPojo : brandPojoList){
                brandPojoArrayList.add(brandPojo);
            }
        }else
        {
            List<BrandPojo> brandPojoList = brandDao.selectByBrand(brand);
            for(BrandPojo brandPojo : brandPojoList){
                brandPojoArrayList.add(brandPojo);
            }
        }
        return brandPojoArrayList;
    }

}
