package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class OrderController {
 @Autowired
 private OrderService service;

 @ApiOperation(value = "creates an order")
 @RequestMapping(path = "/api/orderController",method = RequestMethod.POST)
 public void createOrder() throws ApiException{
     OrderPojo orderPojo = new OrderPojo();
     service.createOrder(orderPojo);
 }

 @ApiOperation(value = "gets all the orders placed")
 @RequestMapping(path = "/api/orderController",method = RequestMethod.GET)
 public List<OrderData> getAll() throws ApiException{
     return service.getAll();
 }



}
