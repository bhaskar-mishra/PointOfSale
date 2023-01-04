package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void addItem(OrderItemPojo orderItemPojo)
    {
        orderItemDao.insert(orderItemPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void placeOrder(int orderId) throws ApiException{
        OrderPojo orderPojo = orderDao.select(orderId);
        orderPojo.setStatus();
    }

}
