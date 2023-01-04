package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";
    private static String select_barC = "select p from ProductPojo p where barcode=:barcode";
    private static String update = "update ProductPojo set name=:name,brand_category=:brand_category,mrp=:mrp where barcode=:barcode";
    private static String delete = "delete from ProductPojo p where barcode=:barcode";
    @Transactional
    public void insert(ProductPojo p) {
        em().persist(p);
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void delete(String barcode)
    {
        Query query = em().createQuery(delete);
        query.setParameter("barcode",barcode);
        int val = query.executeUpdate();
    }

    public void update(ProductPojo p) throws ApiException {
        Query query = em().createQuery(update);
        query.setParameter("name",p.getName());
        query.setParameter("mrp",p.getMrp());
        query.setParameter("brand_category",p.getBrand_category());
        query.setParameter("barcode",p.getBarCode());
        int numberOfEntriesUpdated = query.executeUpdate();
    }

    public boolean checkUnique(String barcode)
    {
            TypedQuery<ProductPojo> query = getQuery(select_barC, ProductPojo.class);
            query.setParameter("barcode", barcode);
            return (query.getResultList().size() == 0);

    }

    public ProductPojo select(String barCode) {
        TypedQuery<ProductPojo> query = getQuery(select_barC, ProductPojo.class);
        query.setParameter("barcode", barCode);
        return getSingle(query);
    }
}
