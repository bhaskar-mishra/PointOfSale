package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class OrderItemController {
    @Autowired
    private OrderItemService service;

    @ApiOperation(value = "adds an item")
    @RequestMapping(path="/api/orderItemController",method = RequestMethod.POST)
    public void addItem(@RequestBody OrderItemForm itemForm) throws ApiException
    {
        OrderDto dto = new OrderDto();
        OrderItemPojo pojo = dto.convert(itemForm);
        service.addItem(pojo);
    }

    @ApiOperation(value = "places an order")
    @RequestMapping(path = "/api/orderItemController/{orderId}",method = RequestMethod.PUT)
    public void placeOrder(@PathVariable int orderId) throws ApiException
    {
        service.placeOrder(orderId);
    }


}
