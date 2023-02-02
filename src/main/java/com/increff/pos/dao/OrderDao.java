package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private static String SELECTALL = "select p from OrderPojo p";
    private static String SELECTBYID = "select p from OrderPojo p where id=:id";
    private static String SELECT_BY_ORDER_CODE = "select p from OrderPojo p where orderCode=:orderCode";
    private static  String SELECT_WITH_DATE_FILTER = "select p from OrderPojo p where placedTime >= :start and placedTime<=:end";
    private static  String SELECT_WITH_ZONED_DATE_FILTER = "select p from OrderPojo p where status=:status and placedTime>=:start and placedTime<=:end";
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

    public OrderPojo selectByOrderCode(String orderCode) throws ApiException
    {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ORDER_CODE,OrderPojo.class);
        query.setParameter("orderCode" , orderCode);
        return getSingle(query);
    }

    public List<OrderPojo> selectOrderWithDateFilter(String start, String end)
    {
        TypedQuery<OrderPojo> query = getQuery(SELECT_WITH_DATE_FILTER,OrderPojo.class);
        query.setParameter("start",start);
        query.setParameter("end",end);
        return query.getResultList();
    }

    public List<OrderPojo> selectOrderWithDateFilter(ZonedDateTime startDateTime,ZonedDateTime endDateTime){
        TypedQuery<OrderPojo> query = getQuery(SELECT_WITH_ZONED_DATE_FILTER, OrderPojo.class);
        query.setParameter("status","PLACED");
        query.setParameter("start",startDateTime);
        query.setParameter("end",endDateTime);
        return query.getResultList();
    }
}
