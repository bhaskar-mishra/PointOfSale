package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

 @ApiOperation(value = "creates an order")
 @RequestMapping(path = "/api/order",method = RequestMethod.POST)
 public OrderData createOrder() throws ApiException{
     return orderDto.createOrder();
 }

 @ApiOperation(value = "gets all the orders placed")
 @RequestMapping(path = "/api/order",method = RequestMethod.GET)
 public List<OrderData> getAll() throws ApiException{
     return orderDto.getAll();
 }

@ApiOperation(value = "gets an order with given id")
@RequestMapping(path = "/api/order/{id}",method = RequestMethod.GET)
public OrderData getOrderWithGivenId(@PathVariable int id) throws ApiException{
     return orderDto.getOrderWithGivenId(id);
}

@ApiOperation(value = "gets an order with given random key")
@RequestMapping(path = "/api/order/useRandomKey/{randomKey}",method = RequestMethod.GET)
public OrderData getOrderWithGivenRandomKey(@PathVariable String randomKey) throws ApiException{
     return orderDto.getOrderWtihGivenRandomKey(randomKey);
}

}
