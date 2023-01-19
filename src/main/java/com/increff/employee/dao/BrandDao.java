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

    private static String SELECTBYID = "select p from BrandPojo p where id=:id";
    private static String SELECTALL = "select p from BrandPojo p";
    private static String DELETEBYID = "delete from BrandPojo p where id=:id";
    private static String CHECKUNIQUE = "select p from BrandPojo p where brand=:brand and category=:category";
    private static String UPDATE = "update BrandPojo set brand=:brand,category=:category where id=:id";

    @Transactional
    public void insert(BrandPojo brandPojo) {
        em().persist(brandPojo);
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(SELECTBYID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public BrandPojo select(String brand,String category) throws ApiException{
        TypedQuery<BrandPojo> query = getQuery(CHECKUNIQUE, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECTALL, BrandPojo.class);
        return query.getResultList();
    }

    public int delete(int id) {
        Query query = em().createQuery(DELETEBYID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public void update(BrandPojo p) {
       int id = p.getId();
       Query query = em().createQuery(UPDATE);
       query.setParameter("id",id);
       query.setParameter("brand",p.getBrand());
       query.setParameter("category",p.getCategory());
       int numberOfEntriesUpdated = query.executeUpdate();
    }


    public boolean checkUnique(String brand,String category)
    {
        TypedQuery<BrandPojo> query = getQuery(CHECKUNIQUE, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return (query.getResultList().size()==0);
    }
}
