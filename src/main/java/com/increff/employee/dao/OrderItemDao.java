package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static String SELECTBYORDERITEMID = "select p from OrderItemPojo p where orderItemId=:orderItemId";
    private static String SELECTBYORDERID = "select p from OrderItemPojo p where orderId=:orderId";

    private static String SELECTBYRANDOMKEY = "select p from OrderItemPojo p where randomKey=:randomKey";

    @Transactional
    public void insert(OrderItemPojo p){em().persist(p);}

    public OrderItemPojo select(int orderItemId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECTBYORDERITEMID, OrderItemPojo.class);
        query.setParameter("orderItemId",orderItemId);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll(int orderId)
    {
        TypedQuery<OrderItemPojo> query = getQuery(SELECTBYORDERID, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectAll(String randomKey){
        TypedQuery<OrderItemPojo> query = getQuery(SELECTBYRANDOMKEY, OrderItemPojo.class);
        query.setParameter("randomKey",randomKey);
        return query.getResultList();
    }
}
