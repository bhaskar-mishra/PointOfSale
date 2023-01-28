package com.increff.employee.dto;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
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

    public void placeOrder(String randomKey) throws ApiException{
        orderItemService.placeOrder(randomKey);
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
}
