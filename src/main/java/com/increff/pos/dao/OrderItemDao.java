package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static String SELECT_BY_ORDER_ITEM_ID = "select p from OrderItemPojo p where orderItemId=:orderItemId";
    private static String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";
    private static String SELECT_BY_ORDER_AND_PRODUCT_ID = "select p from OrderItemPojo p where orderId=:orderId and productId=:productId";
    private static String DELETE_BY_ID = "delete from OrderItemPojo p where orderItemId=:orderItemId";

    @Transactional
    public void insert(OrderItemPojo p){em().persist(p);}

    public OrderItemPojo selectById(int orderItemId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ITEM_ID, OrderItemPojo.class);
        query.setParameter("orderItemId",orderItemId);
        return getSingle(query);
    }

    public OrderItemPojo selectByOrderAndProductId(Integer orderId,Integer productId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_AND_PRODUCT_ID, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        query.setParameter("productId",productId);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAllById(int orderId)
    {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    public void deleteById(Integer orderItemId){
        Query query = em().createQuery(DELETE_BY_ID);
        query.setParameter("orderItemId",orderItemId);
        query.executeUpdate();
    }
}
