package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;


    public void addItem(OrderItemPojo orderItemPojo)
    {
        OrderItemPojo orderItemPojo1 = orderItemDao.selectByOrderAndProductId(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
        // IF Product is already present, add the quantity and set price to new price
        if(orderItemPojo1!=null){
            orderItemPojo1.setQuantity(orderItemPojo1.getQuantity()+ orderItemPojo.getQuantity());
            orderItemPojo1.setSellingPrice(orderItemPojo.getSellingPrice());
            return ;
        }
        orderItemDao.insert(orderItemPojo);
    }

    public OrderItemPojo getByOrderAndProductId(Integer orderId,Integer productId) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectByOrderAndProductId(orderId,productId);
        if(orderItemPojo==null){
            throw new ApiException("this order item doesn't exist");
        }
        return orderItemPojo;
    }


    public OrderItemPojo getOrderItemById(Integer orderItemId) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(orderItemPojo==null){
            throw new ApiException("invalid orderItemId");
        }
        return orderItemPojo;
    }


    public List<OrderItemPojo> getAllItemsById(Integer orderId)throws ApiException {
        List<OrderItemPojo> orderItemPojoList =  orderItemDao.selectAllById(orderId);
        if(orderItemPojoList==null){
            throw new ApiException("invalid orderId");
        }

        return orderItemPojoList;
    }


    public void updateOrderItem(Integer orderItemId,Integer quantity) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(orderItemPojo==null) {
            throw new ApiException("invalid orderItemId");
        }
        if(quantity==0){
            orderItemDao.deleteById(orderItemId);
        }else{
            orderItemPojo.setQuantity(quantity);
        }
    }


    public void deleteOrderItemById(Integer orderItemId) throws ApiException{
        OrderItemPojo orderItemPojo = orderItemDao.selectById(orderItemId);
        if(orderItemPojo==null){
            throw new ApiException("invalid orderItemId");
        }
        orderItemDao.deleteById(orderItemId);
    }

}
