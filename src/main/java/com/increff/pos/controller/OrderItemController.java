package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.EditOrderItemForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemController {
    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "adds an orderItem")
    @RequestMapping(path="/api/orderItem",method = RequestMethod.POST)
    public void addItem(@RequestBody OrderItemForm orderItemForm) throws ApiException
    {
        orderItemDto.addItem(orderItemForm);
    }

    @ApiOperation(value = "places an order")
    @RequestMapping(path = "/api/orderItem/{orderCode}",method = RequestMethod.PUT)
    public void placeOrder(@PathVariable String orderCode) throws ApiException
    {
         orderItemDto.placeOrder(orderCode);
    }

    @ApiOperation(value = "gets the list of all orderItems for a given order code")
    @RequestMapping(path = "/api/orderItem/{orderCode}",method = RequestMethod.GET)
    public List<OrderItemData> getOrderItems(@PathVariable String orderCode) throws ApiException{
         return orderItemDto.getOrderItemsByCode(orderCode);
    }

    @ApiOperation(value = "gets order item by id")
    @RequestMapping(path = "/api/orderItem/getById/{orderItemId}",method = RequestMethod.GET)
    public OrderItemData getOrderItemById(@PathVariable Integer orderItemId) throws ApiException{
        return  orderItemDto.getOrderItemById(orderItemId);
    }

    @ApiOperation(value = "edits and orderItem")
    @RequestMapping(path = "api/orderItem/editOrderItem",method = RequestMethod.PUT)
    public void editOrderItemId(@RequestBody EditOrderItemForm editOrderItemForm) throws ApiException{
        orderItemDto.editOrderItem(editOrderItemForm);
    }

    @ApiOperation(value = "deletes order item by id")
    @RequestMapping(path = "/api/orderItem/{orderItemId}",method = RequestMethod.DELETE)
    public void deleteOrderItemById(@PathVariable Integer orderItemId) throws ApiException{
        orderItemDto.deleteOrderItemById(orderItemId);
    }


}
