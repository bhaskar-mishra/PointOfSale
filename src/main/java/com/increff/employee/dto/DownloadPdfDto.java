package com.increff.employee.dto;


import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadPdfDto {

    @Autowired
    private  OrderItemDao orderItemDao;

    public  List<InvoiceItem> convertToInvoiceItem(String randomKeyForOrder) throws ApiException{

        System.out.println("reaching convertToInvoiceItem in DownloadPdfDto");

        if(orderItemDao==null){
            System.out.println("orderItemDao is null");
        }
        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAllByRandomKey(randomKeyForOrder);
        if(orderItemPojoList==null) System.out.println("orderItemPojoList null");
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setName(orderItemPojo.getProduct());
            invoiceItem.setQuantity(orderItemPojo.getQuantity());
            invoiceItem.setPrice(orderItemPojo.getPrice());
            invoiceItemList.add(invoiceItem);
        }

        System.out.println("invoiceItemList : "+invoiceItemList);
        System.out.println("convertToInvoiceItem working fine");

        return invoiceItemList;
    }

}
