package com.increff.pos.dto;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.EditOrderItemForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private OrderDao orderDao;

    public void addItem(OrderItemForm orderItemForm) throws ApiException {
        orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
        OrderItemPojo orderItemPojo = validateAndConvertOrderItemForm(orderItemForm);
        orderItemService.addItem(orderItemPojo);
    }

    @Transactional
    public void editOrderItem(EditOrderItemForm editOrderItemForm) throws ApiException {
        validate(editOrderItemForm);
        orderItemService.updateOrderItem(editOrderItemForm.getOrderItemId(), editOrderItemForm.getQuantity());
    }

    @Transactional
    public void placeOrder(String randomKey) throws ApiException{
        OrderPojo orderPojo = orderDao.selectByRandomKey(randomKey);
        orderItemService.placeOrder(orderPojo);
    }

    public List<OrderItemData> getOrderItemsForAnOrder(String randomKey) throws ApiException{
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItems(randomKey);
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            orderItemDataList.add(convertPojoToData(orderItemPojo));
        }

        return orderItemDataList;
    }

    @Transactional
    public OrderItemPojo validateAndConvertOrderItemForm(OrderItemForm orderItemForm) throws ApiException {
        ProductPojo productByBarcode = productDao.selectByBarcode(orderItemForm.getBarcode());
        if (productByBarcode == null) {
            throw new ApiException("This product does not exist");
        }

        InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productByBarcode.getBarcode());
        if (orderItemForm.getQuantity() > inventoryPojo.getQuantity()) {
            throw new ApiException("These many samples of the given product are not there");
        }
        inventoryPojo.setQuantity(inventoryPojo.getQuantity() - orderItemForm.getQuantity());
        String randomKey = orderItemForm.getRandomKey();
        OrderPojo orderPojo = orderDao.selectByRandomKey(randomKey);
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setProduct(productByBarcode.getProduct());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setPrice(orderItemForm.getPrice());
        orderItemPojo.setOrderId(orderPojo.getOrderId());
        orderItemPojo.setBarcode(orderItemForm.getBarcode());
        orderItemPojo.setRandomKey(orderItemForm.getRandomKey());

        return orderItemPojo;
    }


    private OrderItemData convertPojoToData(OrderItemPojo orderItemPojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setOrderItemId(orderItemPojo.getOrderItemId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        orderItemData.setProduct(orderItemPojo.getProduct());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        return orderItemData;
    }

    private void validate(EditOrderItemForm editOrderItemForm) throws ApiException{
        if(editOrderItemForm==null){
            throw new ApiException("invalid edit request(form null)");
        }

        if(editOrderItemForm.getOrderItemId()==null){
            throw new ApiException("orderItemId invalid : null");
        }

        if(editOrderItemForm.getQuantity()<0){
            throw new ApiException("quantity can't be negative");
        }
    }
}
