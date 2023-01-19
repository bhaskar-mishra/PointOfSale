package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandDao bDao;

    @Transactional
    public void add(ProductPojo p) throws ApiException{
//        ProductPojo existing = dao.select(p.getId());
//        if(existing!=null){
//            throw new ApiException("This product is already there");
//        }
        boolean unique = dao.checkUnique(p.getBarcode());
        if(!unique){
            throw new ApiException("This product is already there");
        }
        BrandPojo pojo = bDao.select(p.getBrandCategoryId());
        if(pojo==null){
            throw new ApiException("there's no brand with given brand-category id: "+p.getBrandCategoryId());
        }
        System.out.println(p.getBarcode());
        dao.insert(p);

    }

    @Transactional(rollbackOn = ApiException.class)
    public List<ProductPojo> getAll() throws ApiException{
        return dao.selectAll();
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
        ex.setBrandCategoryId(p.getBrandCategoryId());
        ex.setMrp(p.getMrp());
        ex.setProduct(p.getProduct());
        ProductPojo obj = dao.select(p.getBarcode());
        if(obj==null)
        {
            throw new ApiException("barcode invalid");
        }
        BrandPojo pojo = bDao.select(p.getBrandCategoryId());
        if(pojo==null)
        {
            throw new ApiException("no brand category with given id exists, id: "+p.getBrandCategoryId());
        }
        dao.update(ex);
    }

    @Transactional
    public void delete(String barcode){dao.delete(barcode);}

    protected static void normalize(ProductPojo p){
        p.setProduct(p.getProduct().toLowerCase().trim());
    }

}
