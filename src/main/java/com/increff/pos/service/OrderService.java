package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public OrderPojo createOrder() throws ApiException{
        OrderPojo orderPojo = new OrderPojo();
        String orderCode = UUID.randomUUID().toString();
        orderPojo.setOrderCode(orderCode);
        orderDao.insert(orderPojo);
        return orderPojo;
    }


    public List<OrderPojo> getAll() throws ApiException{
        return orderDao.selectAll();
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

    public List<OrderPojo> selectOrderWithDateFilter(String start, String end)
    {
        return orderDao.selectOrderWithDateFilter(start, end);
    }
}
