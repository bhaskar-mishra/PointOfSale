package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{

    private static String select_id = "select p from InventoryPojo p where id=:id";
    private static String update = "update InventoryPojo set quantity=:quantity where id=:id";
    private static String select_all = "select p from InventoryPojo p";
    @Transactional
    public void insert(InventoryPojo p) {
        em().persist(p);
    }

    public InventoryPojo select(int id){
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }
    public void update(InventoryPojo p) {
        int id = p.getId();
        Query query = em().createQuery(update);
        query.setParameter("id",id);
        query.setParameter("quantity",p.getQuantity());
        int numberOfEntriesUpdated = query.executeUpdate();
    }
}
