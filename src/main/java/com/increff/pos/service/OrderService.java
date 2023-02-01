package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;;
import com.increff.pos.pojo.OrderPojo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public OrderPojo createOrder() throws ApiException{
        OrderPojo orderPojo = new OrderPojo();
        orderDao.insert(orderPojo);
        String randomKeyForId = createRandomString();
        orderPojo.setOrderCode(randomKeyForId);
        return orderPojo;
    }

    protected String createRandomString(){
        int len = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(len,useLetters,useNumbers);
        return generatedString;
    }

    public List<OrderPojo> getAll() throws ApiException{
        return orderDao.selectAll();
    }

    public OrderPojo getOrderById(int id) throws ApiException{
        return orderDao.selectById(id);
    }

    public OrderPojo getOrderByRandomKey(String randomKey) throws ApiException{
        return orderDao.selectByRandomKey(randomKey);
    }

    public List<OrderPojo> selectOrderWithDateFilter(String start, String end)
    {
        return orderDao.selectOrderWithDateFilter(start, end);
    }
}
