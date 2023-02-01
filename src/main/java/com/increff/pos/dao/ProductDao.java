package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from ProductPojo p where id=:id";
    private static String SELECT_ALL = "select p from ProductPojo p";
    private static String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private static String DELETE = "delete from ProductPojo p where barcode=:barcode";
    private static String SELECT_BY_BRAND_CATEGORY_PRODUCT = "select p from ProductPojo p where brand=:brand and category=:category and product=:product";
    private static String SELECT_BY_BRAND_PRODUCT = "select p from ProductPojo p where brand=:brand and product=:product";
    private static String SELECT_BY_CATEGORY_PRODUCT = "select p from ProductPojo p where category=:category and product=:product";
    private static String SELECT_BY_PRODUCT = "select p from ProductPojo p where product=:product";
    @Transactional
    public void insert(ProductPojo p) {
        em().persist(p);
    }

    public ProductPojo selectById(Integer id){
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_ID, ProductPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }


    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public void delete(String barcode)
    {
        Query query = em().createQuery(DELETE);
        query.setParameter("barcode",barcode);
        int val = query.executeUpdate();
    }


    public ProductPojo selectByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public ProductPojo selectByBrandCategoryProduct(String brand,String category,String product) throws ApiException{
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY_PRODUCT, ProductPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        query.setParameter("product",product);

        return getSingle(query);
    }

    public List<ProductPojo> selectByBrandCategory(String brand,String category) throws  ApiException{
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY_PRODUCT, ProductPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return query.getResultList();
    }

    public List<ProductPojo> selectByBrandProduct(String brand,String product) throws ApiException {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BRAND_PRODUCT, ProductPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("product",product);
        return query.getResultList();
    }

    public List<ProductPojo> selectByCategoryProduct(String category,String product) throws  ApiException{
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_CATEGORY_PRODUCT, ProductPojo.class);
        query.setParameter("category",category);
        query.setParameter("product",product);

        return query.getResultList();
    }

    public List<ProductPojo> selectByProduct(String product) throws ApiException{
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_PRODUCT, ProductPojo.class);
        query.setParameter("product",product);

        return query.getResultList();
    }
}