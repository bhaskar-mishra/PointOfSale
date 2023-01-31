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
    public void updateProduct(ProductEditForm productEditForm) throws ApiException{
        ProductPojo productPojo = productDao.selectByBarcode(productEditForm.getBarcode());
        if(productPojo==null){
            throw new ApiException("invalid barcode");
        }
        productPojo.setProduct(productEditForm.getProduct());
        productPojo.setMrp(productEditForm.getMRP());
    }

}
