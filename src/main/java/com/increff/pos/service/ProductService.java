package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductService {
    @Autowired
    private ProductDao productDao;


    //rename
    public void add(ProductPojo productPojo) throws ApiException {
        ProductPojo byBarcode = productDao.selectByBarcode(productPojo.getBarcode());
        if (byBarcode != null) {
            throw new ApiException("This product already exists");
        }
        productDao.insert(productPojo);
    }


    public List<ProductPojo> getAll() throws ApiException {
        return productDao.selectAll();
    }


    public ProductPojo selectByProductId(Integer productId) throws ApiException{
        ProductPojo productPojo = productDao.selectById(productId);
        if(productPojo==null){
            throw new ApiException("product doesn't exist");
        }

        return productPojo;
    }


    public List<ProductPojo> selectByBrandCategoryId(Integer brandCategoryId) throws ApiException{
        List<ProductPojo> productPojoList = productDao.selectByBrandCategoryId(brandCategoryId);
        if(productPojoList==null){
            throw new ApiException("invalid brandCategoryId");
        }

        return productPojoList;
    }


    public ProductPojo selectByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        if (productPojo == null) {
            throw new ApiException("no product with given barcode exists");
        }
        return productPojo;
    }


    public void updateProduct(ProductUpdateForm productUpdateForm) throws ApiException {
        ProductPojo productPojo = productDao.selectByBarcode(productUpdateForm.getBarcode());
        if (productPojo == null) {
            throw new ApiException("invalid barcode");
        }
        productPojo.setProduct(productUpdateForm.getProduct());
        productPojo.setMrp(productUpdateForm.getMRP());
    }

}
