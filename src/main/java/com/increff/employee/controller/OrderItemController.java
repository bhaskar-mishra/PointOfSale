package com.increff.employee.controller;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.EditOrderItemForm;
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
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "adds an orderItem")
    @RequestMapping(path="/api/orderItem",method = RequestMethod.POST)
    public void addItem(@RequestBody OrderItemForm orderItemForm) throws ApiException
    {
        orderItemDto.addItem(orderItemForm);
    }

    @ApiOperation(value = "places an order")
    @RequestMapping(path = "/api/orderItem/{randomKey}",method = RequestMethod.PUT)
    public void placeOrder(@PathVariable String randomKey) throws ApiException
    {
        orderItemDto.placeOrder(randomKey);
    }

    @ApiOperation(value = "gets the list of all orderItems for a given order Id")
    @RequestMapping(path = "/api/orderItem/{randomKey}",method = RequestMethod.GET)
    public List<OrderItemData> getOrderItems(@PathVariable String randomKey) throws ApiException{
        return orderItemDto.getOrderItemsForAnOrder(randomKey);
    }

    @ApiOperation(value = "edits and orderItem")
    @RequestMapping(path = "api/orderItem/editOrderItem",method = RequestMethod.PUT)
    public void editOrderItemId(@RequestBody EditOrderItemForm editOrderItemForm) throws ApiException{
        orderItemDto.editOrderItem(editOrderItemForm);
    }


}
