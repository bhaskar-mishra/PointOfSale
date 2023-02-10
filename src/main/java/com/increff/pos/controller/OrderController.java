package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderController {
 @Autowired
 private OrderService orderService;

 @Autowired
 private OrderDto orderDto;

 Logger logger = Logger.getLogger(OrderController.class);

 @ApiOperation(value = "creates an order")
 @RequestMapping(path = "/api/order",method = RequestMethod.POST)
 public OrderData createOrder() throws ApiException{
     return orderDto.createOrder();
 }

 @ApiOperation(value = "gets all the orders placed")
 @RequestMapping(path = "/api/order",method = RequestMethod.GET)
 public List<OrderData> getAll() throws ApiException{
     logger.info("get all method being called");
     return orderDto.getAll();
 }

@ApiOperation(value = "gets an order by orderCode")
@RequestMapping(path = "/api/order/{orderCode}",method = RequestMethod.GET)
public OrderData getOrderByCode(@PathVariable String orderCode) throws ApiException{
     return orderDto.getOrderByCode(orderCode);
}

@ApiOperation(value = "gets an order by orderId")
@RequestMapping(path = "/api/order/getById/{orderId}",method = RequestMethod.GET)
public OrderData getOrderById(@PathVariable Integer orderId) throws ApiException{
     return orderDto.getOrderById(orderId);
}


}
