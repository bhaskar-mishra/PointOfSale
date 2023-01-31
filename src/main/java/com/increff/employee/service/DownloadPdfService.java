package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import  java.util.List;

@Service
public class DownloadPdfService {


    public InvoiceDetails getInvoice(String randomKeyForOrder,List<InvoiceItem> invoiceItemList,Integer orderId) throws ApiException{

        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setTime(LocalDateTime.now());
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItems(invoiceItemList);
        return invoiceDetails;
    }

    public void downloadPdf(String randomKeyForOrder,String invoiceToDownload) throws IOException {
        String filePath = ".\\src\\main\\resources\\pdf";
        String fileName = ""+randomKeyForOrder+".pdf";
        byte[] decodedBytes = Base64.getDecoder().decode(invoiceToDownload.getBytes());
        FileOutputStream fileOutputStream = new FileOutputStream(filePath+fileName);
        fileOutputStream.write(decodedBytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
