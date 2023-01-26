package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dto.DownloadPdfDto;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import com.increff.employee.pojo.OrderItemPojo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import  java.util.List;

@Service
public class DownloadPdfService {

    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    OrderDao orderDao;


    public InvoiceDetails getInvoice(String randomKeyForOrder,List<InvoiceItem> invoiceItemList) throws ApiException{

        Integer orderId = orderDao.selectByRandomKey(randomKeyForOrder).getOrderId();
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setTime(LocalDateTime.now());
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItemList(invoiceItemList);
        return invoiceDetails;
    }
}
