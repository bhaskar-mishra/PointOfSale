package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;


    @Transactional
    public void add(ProductPojo productPojo) throws ApiException {
        ProductPojo productPojo1 = productDao.selectByBarcode(productPojo.getBarcode());
        if (productPojo1 != null) {
            throw new ApiException("This product already exists");
        }
        productDao.insert(productPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<ProductPojo> getAll() throws ApiException {
        return productDao.selectAll();
    }

    @Transactional
    public ProductPojo selectByProductId(Integer productId) throws ApiException{
        ProductPojo productPojo = productDao.selectById(productId);
        if(productPojo==null){
            throw new ApiException("product doesn't exist");
        }

        return productPojo;
    }

    @Transactional
    public List<ProductPojo> selectByBrandCategoryId(Integer brandCategoryId) throws ApiException{
        List<ProductPojo> productPojoList = productDao.selectByBrandCategoryId(brandCategoryId);
        if(productPojoList==null || productPojoList.size()==0){
            throw new ApiException("invalid brandCategoryId");
        }

        return productPojoList;
    }

    @Transactional
    public ProductPojo selectByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if (productPojo == null) {
            throw new ApiException("no product with given barcode exists");
        }
        return productPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateProduct(ProductEditForm productEditForm) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(productEditForm.getBarcode());
        if (productPojo == null) {
            throw new ApiException("invalid barcode");
        }
        productPojo.setProduct(productEditForm.getProduct());
        productPojo.setMrp(productEditForm.getMRP());
    }

}
