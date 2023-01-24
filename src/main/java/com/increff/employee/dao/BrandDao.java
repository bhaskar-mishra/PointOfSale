package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{

    private static String SELECT_BY_ID = "select p from BrandPojo p where id=:id";
    private static String SELECT_ALL = "select p from BrandPojo p";
    private static String DELETE_BY_ID = "delete from BrandPojo p where id=:id";
    private static String SELECT_BY_BRAND_CATEGORY = "select p from BrandPojo p where brand=:brand and category=:category";
    private static String UPDATE = "update BrandPojo set brand=:brand,category=:category where id=:id";
    private static String SELECT_BY_BRAND = "select p from BrandPojo p where brand=:brand";
    private static String SELECT_BY_CATEGORY = "select p from BrandPojo p where category=:category";

    @Transactional
    public void insert(BrandPojo brandPojo) {
        em().persist(brandPojo);
    }

    public BrandPojo selectById(int id) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public BrandPojo selectByBrandCategory(String brand, String category) throws ApiException{
        System.out.println("reaching selectByBrandCategory inside BrandDao");
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() throws ApiException {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

    public int delete(int id) {
        Query query = em().createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public void updateBrandCategory(Integer id,String brand,String category) {
     BrandPojo brandPojo = selectById(id);
     brandPojo.setBrand(brand);
     brandPojo.setCategory(category);
    }


    public boolean checkUnique(String brand,String category)
    {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return (query.getResultList().size()==0);
    }

    public List<BrandPojo> selectByBrand(String brand){
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND, BrandPojo.class);
        query.setParameter("brand",brand);
        return query.getResultList();
    }

    public List<BrandPojo> selectByCategory(String category){
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_CATEGORY, BrandPojo.class);
        query.setParameter("category",category);
        return query.getResultList();
    }
}
