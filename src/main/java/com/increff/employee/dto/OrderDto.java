package com.increff.employee.dto;

import com.google.protobuf.Api;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDto {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;
    public  OrderItemPojo convert(OrderItemForm form) throws ApiException
    {
        ProductPojo productByBarcode = productDao.select(form.getBarcode());
        if(productByBarcode==null)
        {
            throw new ApiException("This product does not exist");
        }

        InventoryPojo inventoryDetails = inventoryDao.select(productByBarcode.getId());
        if(form.getQuantity()>inventoryDetails.getQuantity())
        {
            throw new ApiException("These many samples of the given product are not there");
        }
        inventoryDetails.setQuantity(inventoryDetails.getQuantity()- form.getQuantity());

        OrderItemPojo orderItem = new OrderItemPojo();
        orderItem.setProductId(productByBarcode.getId());
        orderItem.setQuantity(form.getQuantity());
        orderItem.setMrp(form.getMrp());
        orderItem.setOrderId(form.getOrderId());
        return orderItem;
    }

    public static OrderData convert(OrderPojo orderPojo) throws ApiException{
        OrderData orderData = new OrderData();
        orderData.setOrderId(orderPojo.getId());
        orderData.setTime(orderPojo.getTime());
        orderData.setStatus(orderPojo.getStatus());

        return orderData;
    }
}
