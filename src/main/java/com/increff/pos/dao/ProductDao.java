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
    private static String SELECT_BY_BRAND_CATEGORY_ID = "select p from ProductPojo p where brandCategoryId=:brandCategoryId";

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

    public List<ProductPojo> selectByBrandCategoryId(Integer brandCategoryId) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY_ID, ProductPojo.class);
        query.setParameter("brandCategoryId",brandCategoryId);
        return query.getResultList();
    }


}
