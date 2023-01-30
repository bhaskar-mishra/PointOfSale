package com.increff.employee.service;

import com.google.protobuf.Api;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;


    @Transactional
    public void addItem(OrderItemPojo orderItemPojo)
    {
        orderItemDao.insert(orderItemPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void placeOrder(OrderPojo orderPojo) throws ApiException{
        orderPojo.setStatus();
    }


    public List<OrderItemPojo> getAllItems(String randomKey) throws ApiException{
        return orderItemDao.selectAllByRandomKey(randomKey);
    }

    @Transactional
    public List<OrderItemPojo> selectAllById(Integer orderId)throws ApiException {
        return orderItemDao.selectAllById(orderId);
    }

}
