package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.hibernate.criterion.Order;
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
        OrderItemPojo orderItemPojo1 = orderItemDao.selectByOrderAndProductId(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
        // IF Product is already present, add the quantity and set price to new price
        if(orderItemPojo1!=null){
            orderItemPojo1.setQuantity(orderItemPojo1.getQuantity()+ orderItemPojo.getQuantity());
            orderItemPojo1.setSellingPrice(orderItemPojo.getSellingPrice());
        }
        orderItemDao.insert(orderItemPojo);
    }


    public List<OrderItemPojo> getAllItems(String randomKey) throws ApiException{
        return orderItemDao.selectAllByRandomKey(randomKey);
    }

    public OrderItemPojo getOrderItemById(Integer orderItemId) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(orderItemPojo==null){
            throw new ApiException("invalid orderItemId");
        }
        return orderItemPojo;
    }

    @Transactional
    public List<OrderItemPojo> getAllItemsById(Integer orderId)throws ApiException {
        return orderItemDao.selectAllById(orderId);
    }

    @Transactional
    public void updateOrderItem(Integer orderItemId,Integer quantity) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(quantity==0){
            orderItemDao.deleteById(orderItemId);
        }else{
            orderItemPojo.setQuantity(quantity);
        }
    }

}
