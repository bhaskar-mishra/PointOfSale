package com.increff.pos.dto;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
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
        DtoUtils.validateOrderItemForm(orderItemForm);

        //throws an exception if orderCode is incorrect
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderItemForm.getOrderCode());
        //throws an exception if barcode is incorrect
        ProductPojo productPojo = productService.selectByBarcode(orderItemForm.getBarcode());

        if(orderPojo.getStatus().equals("PLACED")){
            throw new ApiException("order already placed! no more items can be added");
        }
        //normalizes barcode
        orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());

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
    public void editOrderItem(UpdateOrderItemForm updateOrderItemForm) throws ApiException {
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
        updateOrderItemForm.setBarcode(updateOrderItemForm.getBarcode().toLowerCase().trim());
        OrderPojo orderPojo = orderService.getOrderByOrderCode(updateOrderItemForm.getOrderCode());
        if(orderPojo.getStatus().equals("PLACED")){
            throw new ApiException("Order is already placed! Can't be edited");
        }
        ProductPojo productPojo = productService.selectByBarcode(updateOrderItemForm.getBarcode());
        OrderItemPojo orderItemPojo = orderItemService.getByOrderAndProductId(orderPojo.getOrderId(),productPojo.getId());
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(orderItemPojo.getProductId());
        Integer quantityAvailable = inventoryPojo.getQuantity()+orderItemPojo.getQuantity();
        if(quantityAvailable< updateOrderItemForm.getQuantity()){
            throw new ApiException("Available samples : "+quantityAvailable);
        }
        inventoryService.updateInventory(orderItemPojo.getProductId(),inventoryPojo.getQuantity()
                + orderItemPojo.getQuantity()
                - updateOrderItemForm.getQuantity()
        );
        orderItemService.updateOrderItem(orderItemPojo.getOrderItemId(), updateOrderItemForm.getQuantity());
    }

    public OrderItemData getOrderItemById(Integer id) throws ApiException{
        if(id==null){
            throw new ApiException("invalid orderItemId");
        }
        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(id);
        ProductPojo productPojo = productService.selectByProductId(orderItemPojo.getProductId());
        return DtoUtils.convertOrderItemPojoToData(orderItemPojo,productPojo.getProduct(),productPojo.getBarcode());
    }



    public List<OrderItemData> getOrderItemsByCode(String orderCode) throws ApiException{
        if(orderCode==null || orderCode.trim().equals("")){
            throw new ApiException("unknown orderCode");
        }
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);//this is to check if orderCode is valid
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            ProductPojo productPojo = productService.selectByProductId(orderItemPojo.getProductId());
            orderItemDataList.add(DtoUtils.convertOrderItemPojoToData(orderItemPojo,productPojo.getProduct(),productPojo.getBarcode()));
        }

        return orderItemDataList;
    }

    @Transactional
    public void placeOrder(String orderCode) throws ApiException{
        if(orderCode==null || orderCode.trim().split(" ").length!=1 || orderCode.trim().equals("")){
            throw new ApiException("invalid orderCode");
        }
        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
        if(orderItemPojoList.size()==0){
            throw new ApiException("no items in order");
        }

        if(orderPojo.getStatus().equals("PLACED")){
            throw new ApiException("Order already placed");
        }

        orderPojo.setStatus("PLACED");
        orderPojo.setPlacedTime(ZonedDateTime.now());
    }

    @Transactional
    public void deleteOrderItemById(Integer orderItemId) throws ApiException{
        if(orderItemId==null){
            throw new ApiException("invalid orderItemId");
        }

        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(orderItemId);
        OrderPojo orderPojo = orderService.getOrderById(orderItemPojo.getOrderId());

        if(orderPojo.getStatus().equals("PLACED")){
            throw new ApiException("Order already placed! Item can't be deleted");
        }

        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(orderItemPojo.getProductId());
        inventoryPojo.setQuantity(inventoryPojo.getQuantity()+orderItemPojo.getQuantity());
        orderItemService.deleteOrderItemById(orderItemId);
    }


}
