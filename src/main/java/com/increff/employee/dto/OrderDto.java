package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Service;


@Service
public class OrderDto {

    public static OrderItemPojo convert(OrderItemForm orderItemForm,String product,Integer orderId) throws ApiException
    {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setProduct(product);
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setPrice(orderItemForm.getPrice());
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setBarcode(orderItemForm.getBarcode());
        orderItemPojo.setRandomKey(orderItemForm.getRandomKey());
        return orderItemPojo;
    }

    public static OrderData convert(OrderPojo orderPojo) throws ApiException{
        OrderData orderData = new OrderData();
        orderData.setOrderId(orderPojo.getOrderId());
        orderData.setRandomKey(orderPojo.getRandomKeyForId());
        orderData.setTime(orderPojo.getTime());
        if(orderPojo.getStatus().equals((Status.PENDING).name())){
            orderData.setStatus("PENDING");
        }else {
            orderData.setStatus("COMPLETE");
        }
        return orderData;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo) throws ApiException{
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setOrderItemId(orderItemPojo.getOrderItemId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        orderItemData.setProduct(orderItemPojo.getProduct());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        return orderItemData;
    }
}
