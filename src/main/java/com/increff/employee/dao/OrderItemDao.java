package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static String SELECTBYID = "select p from OrderItemPojo p where orderItemId=:orderItemId";
    private static String SELECTALL = "select p from OrderItemPojo p where orderId=:orderId";

    @Transactional
    public void insert(OrderItemPojo p){em().persist(p);}

    public OrderItemPojo select(int orderItemId){
        TypedQuery<OrderItemPojo> query = getQuery(SELECTBYID, OrderItemPojo.class);
        query.setParameter("orderItemId",orderItemId);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll(int orderId)
    {
        TypedQuery<OrderItemPojo> query = getQuery(SELECTALL, OrderItemPojo.class);
        return query.getResultList();
    }
}
