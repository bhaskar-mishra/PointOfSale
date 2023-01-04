package com.increff.employee.service;

import com.google.protobuf.Api;
import com.increff.employee.dao.OrderDao;;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.pojo.OrderPojo;
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


    private int orderId;

    @Transactional
    public void createOrder(OrderPojo p) throws ApiException{
        orderDao.insert(p);
        orderId = p.getId();
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

}
