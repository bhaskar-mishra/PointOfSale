package com.increff.employee.dao;

import com.increff.employee.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{

    private static String SELECT_BY_BARCODE = "select p from InventoryPojo p where barcode=:barcode";
    private static String UPDATE = "update InventoryPojo set quantity=:quantity where id=:id";
    private static String SELECT_ALL = "select p from InventoryPojo p";

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em().persist(inventoryPojo);
    }

    public InventoryPojo selectByBarcode(String barcode){
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }
    public void update(InventoryPojo p) {
        int id = p.getId();
        Query query = em().createQuery(UPDATE);
        query.setParameter("id",id);
        query.setParameter("quantity",p.getQuantity());
        int numberOfEntriesUpdated = query.executeUpdate();
    }

    @Transactional
    public  void setQuantity(String barcode,int quantity){
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        getSingle(query).setQuantity(quantity);
    }

}
