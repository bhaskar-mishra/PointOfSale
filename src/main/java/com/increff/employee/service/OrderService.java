package com.increff.employee.service;

import com.google.protobuf.Api;
import com.increff.employee.dao.OrderDao;;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.pojo.OrderPojo;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public OrderData createOrder(OrderPojo orderPojo) throws ApiException{
        orderDao.insert(orderPojo);
        String randomKeyForId = createRandomString();
        orderPojo.setRandomKeyForId(randomKeyForId);
        return OrderDto.convert(orderPojo);
    }

    protected String createRandomString(){
        int len = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(len,useLetters,useNumbers);
        return generatedString;
    }

    public List<OrderData> getAll() throws ApiException{
        List<OrderPojo> list =  orderDao.selectAll();
        List<OrderData> list2 = new ArrayList<>();

        for(OrderPojo pojo : list)
        {
            list2.add(OrderDto.convert(pojo));
        }

        return list2;
    }

    public OrderData getOrder(int id) throws ApiException{
        return OrderDto.convert(orderDao.selectById(id));
    }

    public OrderData getOrder(String randomKey) throws ApiException{
        return OrderDto.convert(orderDao.selectByRandomKey(randomKey));
    }
}
