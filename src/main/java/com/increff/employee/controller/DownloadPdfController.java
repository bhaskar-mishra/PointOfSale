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

import java.util.List;

@Api
@RestController
public class DownloadPdfController {

    @Autowired
    private DownloadPdfService downloadPdfService;

    @Autowired
    private DownloadPdfDto downloadPdfDto;

    private String invoiceToDownload;

    @ApiOperation("Gets invoice to be printed")
    @RequestMapping(path = "/api/getInvoice/{randomKey}",method = RequestMethod.GET)
    public String getInvoice(@PathVariable String randomKey) throws ApiException{
        List<InvoiceItem> invoiceItemList = downloadPdfDto.convertToInvoiceItem(randomKey);
        InvoiceDetails invoiceDetails =  downloadPdfService.getInvoice(randomKey,invoiceItemList);
        String encodedInvoice = downloadPdfDto.getEncodedInvoice(invoiceDetails);
        this.invoiceToDownload = encodedInvoice;
        return encodedInvoice;
    }

    @ApiOperation("Prints pdf")
    @RequestMapping(path="api/printPdf",method = RequestMethod.POST)
    public void downloadInvoice() throws ApiException{

    }
}
