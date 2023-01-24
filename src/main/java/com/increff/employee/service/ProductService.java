package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductEditForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

    @Transactional
    public void add(ProductPojo p) throws ApiException{
        boolean unique = productDao.checkUnique(p.getBarcode());
        if(!unique){
            throw new ApiException("This product is already there");
        }
        BrandPojo pojo = brandDao.selectById(p.getBrandCategoryId());
        if(pojo==null){
            throw new ApiException("there's no brand with given brand-category id: "+p.getBrandCategoryId());
        }
        System.out.println(p.getBarcode());
        productDao.insert(p);

    }

    @Transactional(rollbackOn = ApiException.class)
    public List<ProductPojo> getAll() throws ApiException{
        return productDao.selectAll();
    }

    @Transactional
    public ProductPojo selectByBarcode(String barcode) throws ApiException{
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if(productPojo==null)
        {
            throw new ApiException("no product with given barcode exists");
        }
        return productPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateProduct(String barcode, ProductEditForm productEditForm) throws ApiException{
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        productPojo.setProduct(productEditForm.getProduct());
        System.out.println("new MRP "+productEditForm.getMRP());
        productPojo.setMRP(productEditForm.getMRP());
    }

    @Transactional
    public void deleteByBarcode(String barcode){
        productDao.delete(barcode);
    }

    protected static void normalize(ProductPojo p){
        p.setProduct(p.getProduct().toLowerCase().trim());
    }

}
