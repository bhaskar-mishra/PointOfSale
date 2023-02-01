package com.increff.pos.service;

import com.increff.pos.model.InvoiceDetails;
import com.increff.pos.model.InvoiceItem;
import org.springframework.stereotype.Service;

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
