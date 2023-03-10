package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    public void add(BrandPojo brandPojo) throws ApiException {
        if (!checkUnique(brandPojo.getBrand(), brandPojo.getCategory())) {
            throw new ApiException("this brand category already exists");
        }
        brandDao.insert(brandPojo);
    }


    public BrandPojo getBrandCategoryById(Integer id) throws ApiException {
        BrandPojo brandPojo = brandDao.selectById(id);
        if (brandPojo == null) {
            throw new ApiException("no such brand exists");
        }
        return brandPojo;
    }

    public List<BrandPojo> getAllBrandCategories() throws ApiException {
        return brandDao.selectAll();
    }


    public void updateBrandCategory(Integer id, BrandPojo brandPojo) throws ApiException {
        String brand = brandPojo.getBrand();
        String category = brandPojo.getCategory();

        BrandPojo pojo = brandDao.selectByBrandCategory(brand, category);

        if (pojo != null && !id.equals(pojo.getId()))
            throw new ApiException("Brand category Combination already exists");


        if (pojo != null && id.equals(pojo.getId())) {
            return;
        }

        pojo = brandDao.selectById(id);

        pojo.setBrand(brandPojo.getBrand());
        pojo.setCategory(brandPojo.getCategory());
    }


    private boolean checkUnique(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByBrandCategory(brand, category);
        //return brandPojo==null;
        if (brandPojo == null) {
            return true;
        }
        return false;
    }

    public BrandPojo selectByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByBrandCategory(brand, category);
        if (brandPojo == null) {
            throw new ApiException("brand category doesn't exist");
        }
        return brandPojo;
    }


    public List<String> getAllBrands() throws ApiException {
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        Set<String> brandSet = new HashSet<>();
        List<String> brandsList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandSet.add(brandPojo.getBrand());
        }

        for (String brand : brandSet) {
            brandsList.add(brand);
        }

        return brandsList;
    }


    public List<String> getAllCategories() throws ApiException {
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        Set<String> categorySet = new HashSet<>();
        List<String> categoryList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            categorySet.add(brandPojo.getCategory());
        }

        for (String brand : categorySet) {
            categoryList.add(brand);
        }

        return categoryList;
    }


    public List<BrandPojo> getCategoriesForBrand(String brand) throws ApiException {
        List<BrandPojo> brandPojoList = brandDao.selectByBrand(brand);
        return brandPojoList;
    }


}
