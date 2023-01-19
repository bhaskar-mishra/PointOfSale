package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String SELECTBYID = "select p from ProductPojo p where id=:id";
    private static String SELECTALL = "select p from ProductPojo p";
    private static String SELECTBYBARCODE = "select p from ProductPojo p where barcode=:barcode";
    private static String UPDATE = "update ProductPojo set name=:name,brand_category=:brand_category,mrp=:mrp where barcode=:barcode";
    private static String DELETE = "delete from ProductPojo p where barcode=:barcode";
    @Transactional
    public void insert(ProductPojo p) {
        em().persist(p);
    }


    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECTALL, ProductPojo.class);
        return query.getResultList();
    }

    public void delete(String barcode)
    {
        Query query = em().createQuery(DELETE);
        query.setParameter("barcode",barcode);
        int val = query.executeUpdate();
    }

    public void update(ProductPojo p) throws ApiException {
        Query query = em().createQuery(UPDATE);
        query.setParameter("name",p.getProduct());
        query.setParameter("mrp",p.getMrp());
        query.setParameter("brand_category",p.getBrandCategoryId());
        query.setParameter("barcode",p.getBarcode());
        int numberOfEntriesUpdated = query.executeUpdate();
    }

    public boolean checkUnique(String barcode)
    {
            TypedQuery<ProductPojo> query = getQuery(SELECTBYBARCODE, ProductPojo.class);
            query.setParameter("barcode", barcode);
            return (query.getResultList().size() == 0);

    }

    public ProductPojo select(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECTBYBARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }
}
