package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;;
import com.increff.pos.pojo.OrderPojo;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    Logger logger = Logger.getLogger(OrderService.class);

    public OrderPojo createOrder() throws ApiException{
        OrderPojo orderPojo = new OrderPojo();
        String orderCode = UUID.randomUUID().toString();
        orderPojo.setOrderCode(orderCode);
        orderDao.insert(orderPojo);
        return orderPojo;
    }


    public List<OrderPojo> getAll() throws ApiException{
        logger.info("inside getAll in orderService");
        List<OrderPojo> orderPojoList = new ArrayList<>();
        try{
            orderPojoList =  orderDao.selectAll();
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        logger.info("order dao working fine");
        return orderPojoList;
    }

    public OrderPojo getOrderById(int id) throws ApiException{
        OrderPojo orderPojo =  orderDao.selectById(id);
        if(orderPojo==null){
            throw new ApiException("Invalid orderId");
        }

        return orderPojo;
    }

    public OrderPojo getOrderByOrderCode(String orderCode) throws ApiException{
        OrderPojo orderPojo =  orderDao.selectByOrderCode(orderCode);
        if(orderPojo==null){
            throw new ApiException("invalid orderCode");
        }
        return orderPojo;
    }


    public List<OrderPojo> selectOrderWithDateFilter(ZonedDateTime startDateTime,ZonedDateTime endDateTime) throws ApiException{
        return orderDao.selectOrderWithDateFilter(startDateTime,endDateTime);
    }
}
