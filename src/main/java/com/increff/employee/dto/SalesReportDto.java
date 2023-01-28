package com.increff.employee.dto;


import com.increff.employee.model.OrderData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ReportService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesReportDto {

    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderService orderService;

    public SalesReportData getSalesReportForABrandCategoryInGivenTime(SalesReportForm salesReportForm) throws ApiException, ParseException {
        normalize(salesReportForm);
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for (OrderPojo orderPojo : orderPojoList){
            orderDataList.add(convertOrderPojoToData(orderPojo));
        }
       return reportService.getSalesReportForABrandCategoryInGivenTime(salesReportForm,orderDataList);
    }

    public List<SalesReportData> getSalesOnInput(SalesReportForm salesReportForm) throws ApiException, ParseException {
        normalize(salesReportForm);
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for (OrderPojo orderPojo : orderPojoList){
            orderDataList.add(convertOrderPojoToData(orderPojo));
        }

        return reportService.getSalesOnInput(salesReportForm,orderDataList);
    }

    public List<SalesReportData> getAllSales() throws ApiException{
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for (OrderPojo orderPojo : orderPojoList){
            orderDataList.add(convertOrderPojoToData(orderPojo));
        }
       return reportService.getAllSales(orderDataList);
    }

    private void normalize(SalesReportForm salesReportForm){
        salesReportForm.setBrand(salesReportForm.getBrand().toLowerCase().trim());
        salesReportForm.setCategory(salesReportForm.getCategory().toLowerCase().trim());
    }

    private OrderData convertOrderPojoToData(OrderPojo orderPojo){
        OrderData orderData = new OrderData();
        orderData.setRandomKey(orderPojo.getRandomKeyForId());
        orderData.setOrderId(orderPojo.getOrderId());
        orderData.setStatus(orderPojo.getStatus());
        orderData.setTime(orderPojo.getTime());
        return orderData;
    }

}
