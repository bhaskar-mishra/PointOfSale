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


        System.out.println("inside getInvoice in DownloadPdfService");

        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setTime(LocalDateTime.now());
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItems(invoiceItemList);
        System.out.println("getInvoice working fine in DownloadPdfService");
        System.out.println("exiting getInvoice in DownloadPdfService");
        return invoiceDetails;
    }

    public void downloadPdf(String randomKeyForOrder,String invoiceToDownload) throws IOException {
        System.out.println("inside downloadPdf method in DownloadPdfService");
        System.out.println("invoiceToDownload : "+invoiceToDownload);
        String filePath = ".\\src\\main\\resources\\pdf";
        String fileName = ""+randomKeyForOrder+".pdf";
        byte[] decodedBytes = Base64.getDecoder().decode(invoiceToDownload.getBytes());
        System.out.println("decoded invoice successfully");
        FileOutputStream fileOutputStream = new FileOutputStream(filePath+fileName);
        System.out.println("CreatedFileSuccessfully");
        fileOutputStream.write(decodedBytes);
        fileOutputStream.flush();
        fileOutputStream.close();
        System.out.println("Exiting downloadPdf in DownloadPdfService");
    }
}
