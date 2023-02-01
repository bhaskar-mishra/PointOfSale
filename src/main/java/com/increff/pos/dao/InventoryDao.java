package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static String SELECT_BY_ID = "select p from InventoryPojo p where id=:id";

    @Autowired
    private ProductDao productDao;

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em().persist(inventoryPojo);
    }

    public InventoryPojo selectById(Integer id){
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_ID,InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }

    public InventoryPojo selectByBarcode(String barcode){
        ProductPojo productPojo = productDao.selectByBarcode(barcode);
        return selectById(productPojo.getId());
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }


}
