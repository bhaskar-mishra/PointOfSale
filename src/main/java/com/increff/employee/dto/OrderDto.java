package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;

    public OrderData createOrder() throws ApiException{
        OrderPojo orderPojo = orderService.createOrder();
        OrderData orderData = convertPojoToData(orderPojo);
        return orderData;
    }

    public List<OrderData> getAll() throws ApiException{
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for(OrderPojo orderPojo : orderPojoList){
            orderDataList.add(convertPojoToData(orderPojo));
        }

        return orderDataList;
    }

    public OrderData getOrderWithGivenId(Integer id) throws ApiException{
        OrderPojo orderPojo = orderService.getOrderById(id);
        return convertPojoToData(orderPojo);
    }

    public OrderData getOrderWtihGivenRandomKey(String randomKey) throws ApiException{
        OrderPojo orderPojo = orderService.getOrderByRandomKey(randomKey);
        return convertPojoToData(orderPojo);
    }
//    public static OrderItemPojo convert(OrderItemForm orderItemForm,String product,Integer orderId) throws ApiException
//    {
//        OrderItemPojo orderItemPojo = new OrderItemPojo();
//        orderItemPojo.setProduct(product);
//        orderItemPojo.setQuantity(orderItemForm.getQuantity());
//        orderItemPojo.setPrice(orderItemForm.getPrice());
//        orderItemPojo.setOrderId(orderId);
//        orderItemPojo.setBarcode(orderItemForm.getBarcode());
//        orderItemPojo.setRandomKey(orderItemForm.getRandomKey());
//        return orderItemPojo;
//    }

    private OrderData convertPojoToData(OrderPojo orderPojo) throws ApiException{
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


}
