package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto orderDto;

    @Test
    public void createOrderTest() throws ApiException {
        OrderData orderData = orderDto.createOrder();
        OrderData orderData1 = orderDto.getOrderByCode(orderData.getOrderCode());

        assertEquals(orderData.getOrderId(),orderData1.getOrderId());
        assertEquals(orderData.getOrderCode(),orderData1.getOrderCode());
        assertEquals(orderData.getStatus(),orderData1.getStatus());
        assertEquals(orderData.getTime(),orderData1.getTime());
    }

    @Test
    public void getOrderByCodeTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderData orderData1 = orderDto.getOrderByCode(orderData.getOrderCode());

        assertEquals(orderData.getOrderId(),orderData1.getOrderId());
        assertEquals(orderData.getOrderCode(),orderData1.getOrderCode());
        assertEquals(orderData.getStatus(),orderData1.getStatus());
        assertEquals(orderData.getTime(),orderData1.getTime());
    }

    @Test(expected = ApiException.class)
    public void getOrderByCodeNullTest() throws ApiException{
        OrderData orderData1 = orderDto.createOrder();
        OrderData orderData = orderDto.getOrderByCode(null);
    }

    @Test(expected = ApiException.class)
    public void getOrderByCodeEmptyCodeTest() throws ApiException{
        OrderData orderData1 = orderDto.createOrder();
        OrderData orderData = orderDto.getOrderByCode("   ");
    }

    @Test
    public void getOrderByIdTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderData orderData1 = orderDto.getOrderById(orderData.getOrderId());

        assertEquals(orderData.getOrderId(),orderData1.getOrderId());
        assertEquals(orderData.getOrderCode(),orderData1.getOrderCode());
        assertEquals(orderData.getStatus(),orderData1.getStatus());
        assertEquals(orderData.getTime(),orderData1.getTime());
    }

    @Test(expected = ApiException.class)
    public void getOrderByIdNullTest()throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderData orderData1 = orderDto.getOrderById(null);
    }

    @Test
    public void getAllTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderData orderData1 = orderDto.createOrder();

        List<OrderData> orderDataList = orderDto.getAll();

        assertEquals(orderData.getOrderId(),orderDataList.get(0).getOrderId());
        assertEquals(orderData.getOrderCode(),orderDataList.get(0).getOrderCode());
        assertEquals(orderData.getStatus(),orderDataList.get(0).getStatus());
        assertEquals(orderData.getTime(),orderDataList.get(0).getTime());

        assertEquals(orderData1.getOrderId(),orderDataList.get(1).getOrderId());
        assertEquals(orderData1.getOrderCode(),orderDataList.get(1).getOrderCode());
        assertEquals(orderData1.getStatus(),orderDataList.get(1).getStatus());
        assertEquals(orderData1.getTime(),orderDataList.get(1).getTime());
    }

    @Test
    public void convertOrderPojoToDataTest() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderCode("orderCode");
        orderPojo.setOrderId(Integer.valueOf(1));
        orderPojo.setPlacedTime(ZonedDateTime.now());
        orderPojo.setStatus("PENDING");

        OrderData orderData = DtoUtils.convertOrderPojoToData(orderPojo);

        assertEquals(orderPojo.getOrderId(),orderData.getOrderId());
        assertEquals(orderPojo.getOrderCode(),orderData.getOrderCode());
        assertEquals(orderPojo.getStatus(),orderData.getStatus());
        assertEquals(orderPojo.getPlacedTime(),orderData.getTime());
    }



}
