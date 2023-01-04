package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandDao bDao;

    @Transactional
    public void add(ProductPojo p) throws ApiException{
        ProductPojo existing = dao.select(p.getId());
        if(existing!=null){
            throw new ApiException("This product is already there");
        }
        boolean unique = dao.checkUnique(p.getBarCode());
        if(!unique){
            throw new ApiException("This product is already there");
        }
        BrandPojo pojo = bDao.select(p.getBrand_category());
        if(pojo==null){
            throw new ApiException("there's no brand with given brand-category id: "+p.getBrand_category());
        }
        System.out.println(p.getBarCode());
        dao.insert(p);

    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(String barcode) throws ApiException{
        return getCheck(barcode);
    }

    @Transactional
    public ProductPojo getCheck(String barcode) throws ApiException{
        ProductPojo p = dao.select(barcode);
        if(p==null)
        {
            throw new ApiException("no product with given barcode exists");
        }
        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(String barcode,ProductPojo p) throws ApiException{
        normalize(p);
        ProductPojo ex = getCheck(barcode);
        ex.setBrand_category(p.getBrand_category());
        ex.setMrp(p.getMrp());
        ex.setName(p.getName());
        ProductPojo obj = dao.select(p.getBarCode());
        if(obj==null)
        {
            throw new ApiException("barcode invalid");
        }
        BrandPojo pojo = bDao.select(p.getBrand_category());
        if(pojo==null)
        {
            throw new ApiException("no brand category with given id exists, id: "+p.getBrand_category());
        }
        dao.update(ex);
    }

    @Transactional
    public void delete(String barcode){dao.delete(barcode);}

    protected static void normalize(ProductPojo p){
        p.setName(p.getName().toLowerCase().trim());
    }

}
