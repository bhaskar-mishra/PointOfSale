package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;

    @Transactional
    public void addItem(OrderItemPojo orderItemPojo)
    {
        orderItemDao.insert(orderItemPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void placeOrder(String randomKey) throws ApiException{
        OrderPojo orderPojo = orderDao.selectByRandomKey(randomKey);
        orderPojo.setStatus();
    }

    public OrderItemPojo check(OrderItemForm orderItemForm) throws ApiException{
        ProductPojo productByBarcode = productDao.selectByBarcode(orderItemForm.getBarcode());
        if(productByBarcode==null)
        {
            throw new ApiException("This product does not exist");
        }

        InventoryPojo inventoryDetails = inventoryDao.selectByBarcode(productByBarcode.getBarcode());
        if(orderItemForm.getQuantity()>inventoryDetails.getQuantity())
        {
            throw new ApiException("These many samples of the given product are not there");
        }
        inventoryDao.setQuantity(orderItemForm.getBarcode(),inventoryDetails.getQuantity()- orderItemForm.getQuantity());
        String randomKey = orderItemForm.getRandomKey();
        OrderPojo orderPojo = orderDao.selectByRandomKey(randomKey);

        return OrderDto.convert(orderItemForm,productByBarcode.getProduct(),orderPojo.getOrderId());
    }

    public List<OrderItemData> getAllItems(String randomKey) throws ApiException{

        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(randomKey);
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            orderItemDataList.add(OrderDto.convertOrderItemPojoToData(orderItemPojo));
        }
        return orderItemDataList;
    }

}
