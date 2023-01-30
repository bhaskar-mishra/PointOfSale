package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private static String SELECTALL = "select p from OrderPojo p";
    private static String SELECTBYID = "select p from OrderPojo p where id=:id";
    private static String SELECTBYRANDOMKEY = "select p from OrderPojo p where randomKeyForId=:randomKeyForId";
    private static  String SELECT_WITH_DATEFILTER = "select p from OrderPojo p where placedTime >= :start and placedTime<=:end";
    @Transactional
    public void insert(OrderPojo orderPojo){em().persist(orderPojo);}
    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(SELECTALL, OrderPojo.class);
        return query.getResultList();
    }

    public OrderPojo selectById(int orderId){
        TypedQuery<OrderPojo> query = getQuery(SELECTBYID, OrderPojo.class);
        query.setParameter("id",orderId);
        return getSingle(query);
    }

    public OrderPojo selectByRandomKey(String randomKey) throws ApiException
    {
        TypedQuery<OrderPojo> query = getQuery(SELECTBYRANDOMKEY,OrderPojo.class);
        query.setParameter("randomKeyForId",randomKey);
        return getSingle(query);
    }

    public List<OrderPojo> selectOrderWithDateFilter(String start, String end)
    {
        TypedQuery<OrderPojo> query = getQuery(SELECT_WITH_DATEFILTER,OrderPojo.class);
        query.setParameter("start",start);
        query.setParameter("end",end);
        return query.getResultList();
    }
}
