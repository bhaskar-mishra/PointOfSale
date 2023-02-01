package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
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
        OrderData orderData = DtoUtils.convertOrderPojoToData(orderPojo);
        return orderData;
    }

    public OrderData getOrderByCode(String orderCode) throws ApiException{
        if(orderCode==null || orderCode.trim().split(" ").length!=1){
            throw new ApiException("invalid orderCode");
        }
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);
        return DtoUtils.convertOrderPojoToData(orderPojo);
    }

    public OrderData getOrderById(Integer orderId) throws ApiException{
        if(orderId==null){
            throw new ApiException("invalid orderId");
        }

        OrderPojo orderPojo = orderService.getOrderById(orderId);

        return DtoUtils.convertOrderPojoToData(orderPojo);
    }

    public List<OrderData> getAll() throws ApiException{
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for(OrderPojo orderPojo : orderPojoList){
            orderDataList.add(DtoUtils.convertOrderPojoToData(orderPojo));
        }

        return orderDataList;
    }






//    public OrderData getOrderWtihGivenRandomKey(String randomKey) throws ApiException{
//        OrderPojo orderPojo = orderService.getOrderByOrderCode(randomKey);
//        return DtoUtils.convertOrderPojoToData(orderPojo);
//    }
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




}
