package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.pojo.UserPojo;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao{

    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";
    private static String delete_id = "delete from BrandPojo p where id=:id";
    private static String unique = "select p from BrandPojo p where brand=:brand and category=:category";
    private static String update = "update BrandPojo set brand=:brand,category=:category where id=:id";
    @Transactional
    public void insert(BrandPojo p) {
        em().persist(p);
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public int delete(int id) {
        Query query = em().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public void update(BrandPojo p) {
       int id = p.getId();
       Query query = em().createQuery(update);
       query.setParameter("id",id);
       query.setParameter("brand",p.getBrand());
       query.setParameter("category",p.getCategory());
       int numberOfEntriesUpdated = query.executeUpdate();
    }


    public boolean checkUnique(String brand,String category)
    {
        TypedQuery<BrandPojo> query = getQuery(unique, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return (query.getResultList().size()==0);
    }
}
