package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private static String SELECTALL = "select p from OrderPojo p";
    private static String SELECTBYID = "select p from OrderPojo p where orderId=:orderId";
    @Transactional
    public void insert(OrderPojo p){em().persist(p);}
    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(SELECTALL, OrderPojo.class);
        return query.getResultList();
    }

    public OrderPojo select(int orderId){
        TypedQuery<OrderPojo> query = getQuery(SELECTBYID, OrderPojo.class);
        query.setParameter("orderId",orderId);
        return getSingle(query);
    }
}
