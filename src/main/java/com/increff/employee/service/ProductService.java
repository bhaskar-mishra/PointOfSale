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
    public void add(ProductPojo productPojo) throws ApiException{
        ProductPojo productPojo1 = productDao.selectByBarcode(productPojo.getBarcode());
        if(productPojo1!=null){
            throw new ApiException("This product already exists");
        }
        BrandPojo pojo = brandDao.selectByBrandCategory(productPojo.getBrand(),productPojo.getCategory());
        if(pojo==null){
            throw new ApiException("incorrect brand category");
        }
        productDao.insert(productPojo);

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
        productPojo.setMrp(productEditForm.getMRP());
    }

    @Transactional
    public void deleteByBarcode(String barcode){
        productDao.delete(barcode);
    }

    protected static void normalize(ProductPojo p){
        p.setProduct(p.getProduct().toLowerCase().trim());
    }

}
