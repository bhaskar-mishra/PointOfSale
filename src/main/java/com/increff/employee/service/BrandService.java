package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;


    @Transactional
    public void add(BrandPojo p) throws ApiException {
//        BrandPojo existing = dao.select(p.getId());
//
//        if (existing != null) {
//            throw new ApiException("This brand category already exists");
//        }
        boolean unique = brandDao.checkUnique(p.getBrand(), p.getCategory());
        if (!unique) {
            throw new ApiException("This brand category already exists");
        }
//        System.out.println(p.getBrand()+" "+p.getCategory());
        brandDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() throws ApiException {
        return brandDao.selectAll();
    }

    @Transactional
    public List<String> getCategoriesForBrand(String brand) throws ApiException{
        List<BrandPojo> brands = getAll();
        List<String> categoriesForGivenBrand = new ArrayList<>();
        for(BrandPojo pojo : brands)
        {
            if(pojo.getBrand().equals(brand)) categoriesForGivenBrand.add(pojo.getCategory());
        }

        return categoriesForGivenBrand;
    }

    @Transactional
    public int getBrandCategoryId(String brand,String category) throws ApiException
    {
        List<BrandPojo> brands = getAll();
        int id = 0;
        for(BrandPojo pojo : brands)
        {
            if(pojo.getBrand().equals(brand) && pojo.getCategory().equals(category))
            {
                id = pojo.getId();
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
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        brandDao.update(ex);
    }

    protected static void normalize(BrandPojo p) {
        p.setBrand(p.getBrand().toLowerCase().trim());
        p.setCategory(p.getCategory().toLowerCase().trim());
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = brandDao.select(id);
        if (p == null) {
            throw new ApiException("Brand category with given id does not exist, id: " + id);
        }
        return p;
    }

}
