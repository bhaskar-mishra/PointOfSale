package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemController {
    @Autowired
    private OrderItemService service;

    @ApiOperation(value = "adds an orderItem")
    @RequestMapping(path="/api/orderItem",method = RequestMethod.POST)
    public void addItem(@RequestBody OrderItemForm orderItemForm) throws ApiException
    {
        OrderItemPojo orderItemPojo = service.check(orderItemForm);
        System.out.println("Form converted successfully");
        service.addItem(orderItemPojo);
    }

    @ApiOperation(value = "places an order")
    @RequestMapping(path = "/api/orderItem/{randomKey}",method = RequestMethod.PUT)
    public void placeOrder(@PathVariable String randomKey) throws ApiException
    {
        service.placeOrder(randomKey);
    }

    @ApiOperation(value = "gets the list of all orderItems for a given order Id")
    @RequestMapping(path = "/api/orderItem/{randomKey}",method = RequestMethod.GET)
    public List<OrderItemData> getOrderItems(@PathVariable String randomKey) throws ApiException{
        return service.getAllItems(randomKey);
    }


}
