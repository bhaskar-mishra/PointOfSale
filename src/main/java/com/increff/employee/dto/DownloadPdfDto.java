package com.increff.employee.dto;


import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DownloadPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadPdfDto {

    @Autowired
    private  OrderItemDao orderItemDao;
    @Autowired
    private DownloadPdfService downloadPdfService;

    public String getInvoice(String random_key_for_order) throws ApiException, IOException {
        List<InvoiceItem> invoiceItemList = convertToInvoiceItem(random_key_for_order);
        InvoiceDetails invoiceDetails =  downloadPdfService.getInvoice(random_key_for_order,invoiceItemList);
        String encodedInvoice = getEncodedInvoice(invoiceDetails);
        downloadPdfService.downloadPdf(random_key_for_order,encodedInvoice);
        String filePath = ".\\src\\main\\resources\\pdf";
        Path pdfPath = Paths.get(filePath+random_key_for_order+".pdf");
        byte[] contents = Files.readAllBytes(pdfPath);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        String filename = random_key_for_order+"_invoice.pdf";
        httpHeaders.setContentDispositionFormData(filename,filename);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(contents,httpHeaders, HttpStatus.OK);
        return encodedInvoice;
    }

//    public ResponseEntity<byte[]>  getInvoice(int id) throws ApiException, IOException {
//        OrderMasterPojo orderData = orderApi.get(id);
//
//        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderData.getId() + "_invoice.pdf");
//
//        byte[] contents = Files.readAllBytes(pdfPath);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//
//        String filename = orderData.getId() + ".pdf";
//        headers.setContentDispositionFormData(filename, filename);
//
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
//        return response;
//    }

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

    public String getEncodedInvoice(InvoiceDetails invoiceDetails) throws ApiException{

        String url = "http://localhost:8000/invoice/api/generate";

        RestTemplate restTemplate = new RestTemplate();
        String encodedInvoice = restTemplate.postForObject(url, invoiceDetails, String.class);
        return encodedInvoice;
    }

}
