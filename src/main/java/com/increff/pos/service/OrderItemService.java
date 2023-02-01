package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void updateOrderItem(Integer orderItemId,Integer quantity) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(orderItemPojo==null){
            throw new ApiException("invalid orderItemId");
        }
        if(quantity==0){
            orderItemDao.deleteById(orderItemId);
        }else{
            orderItemPojo.setQuantity(quantity);
        }
    }

}
