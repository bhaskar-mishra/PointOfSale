package com.increff.pos.dto;

import com.increff.pos.model.EditOrderItemForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;


    public void addItem(OrderItemForm orderItemForm) throws ApiException {
        DtoUtils.validate(orderItemForm);

        //throws an exception if orderCode is incorrect
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderItemForm.getOrderCode());
        //throws an exception if barcode is incorrect
        ProductPojo productPojo = productService.selectByBarcode(orderItemForm.getBarcode());
        //normalizes barcode
        orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
        InventoryPojo inventoryPojo = inventoryService.getInventoryById(productPojo.getId());

        //checks if input quantity is exceeding the available limit
        if(inventoryPojo.getQuantity()< orderItemForm.getQuantity()){
            throw new ApiException("Quantity Available : "+inventoryPojo.getQuantity());
        }

        //UPDATES INVENTORY ON ADDITION OF AN ITEM
        inventoryService.updateInventory(productPojo.getId(), inventoryPojo.getQuantity()-orderItemForm.getQuantity());
        //ADDS ITEM TO ORDER
        orderItemService.addItem(DtoUtils.convertOrderItemFormToPojo(orderItemForm, productPojo.getId(), orderPojo.getOrderId()));
    }



    @Transactional
    public void editOrderItem(EditOrderItemForm editOrderItemForm) throws ApiException {
        DtoUtils.validateEditOrderItemForm(editOrderItemForm);
        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(editOrderItemForm.getOrderItemId());
        InventoryPojo inventoryPojo = inventoryService.getInventoryById(orderItemPojo.getProductId());
        inventoryService.updateInventory(orderItemPojo.getProductId(),inventoryPojo.getQuantity()
                + orderItemPojo.getQuantity()
                - editOrderItemForm.getQuantity()
        );
        orderItemService.updateOrderItem(editOrderItemForm.getOrderItemId(), editOrderItemForm.getQuantity());
    }

    @Transactional
    public void placeOrder(String orderCode) throws ApiException{
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);
        orderPojo.setStatus("PLACED");
        orderPojo.setPlacedTime(ZonedDateTime.now());
    }

    public List<OrderItemData> getOrderItemsForAnOrder(String orderCode) throws ApiException{
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            ProductPojo productPojo = productService.selectById(orderItemPojo.getProductId());
            orderItemDataList.add(DtoUtils.convertOrderItemPojoToData(orderItemPojo,productPojo.getProduct(),productPojo.getBarcode()));
        }

        return orderItemDataList;
    }

}
