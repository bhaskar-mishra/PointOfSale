package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static String SELECT_BY_ORDER_ITEM_ID = "select p from OrderItemPojo p where orderItemId=:orderItemId";
    private static String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";
    private static String SELECT_BY_ORDER_AND_PRODUCT_ID = "select p from OrderItemPojo p where orderId=:orderId and productId=:productId";

    private static String SELECT_BY_ORDER_CODE = "select p from OrderItemPojo p where randomKey=:randomKey";
    private static String DELETE_BY_ID = "delete p from OrderItemPojo p where orderItemId=:orderItemId";

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

    public List<OrderItemPojo> selectAllByRandomKey(String randomKey){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_CODE, OrderItemPojo.class);
        query.setParameter("randomKey",randomKey);
        return query.getResultList();
    }

    public void deleteById(Integer orderItemId){
        TypedQuery<OrderItemPojo> query = getQuery(DELETE_BY_ID,OrderItemPojo.class);
        query.setParameter("orderItemId",orderItemId);
    }
}
