package com.increff.employee.controller;

import com.increff.employee.dto.DownloadPdfDto;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DownloadPdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Api
@RestController
public class DownloadPdfController {

    @Autowired
    private DownloadPdfService downloadPdfService;

    @Autowired
    private DownloadPdfDto downloadPdfDto;


    @ApiOperation("Gets invoice to be printed")
    @RequestMapping(path = "/api/getInvoice/{randomKeyForOrder}",method = RequestMethod.GET)
    public String getInvoice(@PathVariable String randomKeyForOrder) throws ApiException, IOException {
        List<InvoiceItem> invoiceItemList = downloadPdfDto.convertToInvoiceItem(randomKeyForOrder);
        InvoiceDetails invoiceDetails =  downloadPdfService.getInvoice(randomKeyForOrder,invoiceItemList);
        String encodedInvoice = downloadPdfDto.getEncodedInvoice(invoiceDetails);
        downloadPdfService.downloadPdf(randomKeyForOrder,encodedInvoice);
        return encodedInvoice;
    }

}
