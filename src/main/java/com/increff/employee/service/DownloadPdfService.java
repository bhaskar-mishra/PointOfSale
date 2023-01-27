package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import  java.util.List;

@Service
public class DownloadPdfService {

    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    OrderDao orderDao;


    public InvoiceDetails getInvoice(String randomKeyForOrder,List<InvoiceItem> invoiceItemList) throws ApiException{


        System.out.println("inside getInvoice in DownloadPdfService");
        Integer orderId = orderDao.selectByRandomKey(randomKeyForOrder).getOrderId();
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setTime(LocalDateTime.now());
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItems(invoiceItemList);
        System.out.println("getInvoice working fine in DownloadPdfService");
        System.out.println("exiting getInvoice in DownloadPdfService");
        return invoiceDetails;
    }
}
