package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.Status;
import com.increff.pos.service.ApiException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private static String SELECT_ALL = "select p from OrderPojo p";
    private static String SELECT_BY_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_BY_ORDER_CODE = "select p from OrderPojo p where orderCode=:orderCode";
    private static  String SELECT_WITH_ZONED_DATE_FILTER = "select p from OrderPojo p where status=:status and placedTime>=:start and placedTime<=:end";

    Logger logger = Logger.getLogger(OrderDao.class);
    public void insert(OrderPojo orderPojo){em().persist(orderPojo);}
    public List<OrderPojo> selectAll(){
        logger.info("select all being called inside order dao");
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

    public OrderPojo selectById(int orderId){
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID, OrderPojo.class);
        query.setParameter("id",orderId);
        return getSingle(query);
    }

    public OrderPojo selectByOrderCode(String orderCode) throws ApiException
    {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ORDER_CODE,OrderPojo.class);
        query.setParameter("orderCode" , orderCode);
        return getSingle(query);
    }

    public List<OrderPojo> selectOrderWithDateFilter(ZonedDateTime startDateTime,ZonedDateTime endDateTime){
        TypedQuery<OrderPojo> query = getQuery(SELECT_WITH_ZONED_DATE_FILTER, OrderPojo.class);
        query.setParameter("status", Status.PLACED);
        query.setParameter("start",startDateTime);
        query.setParameter("end",endDateTime);
        return query.getResultList();
    }
}
