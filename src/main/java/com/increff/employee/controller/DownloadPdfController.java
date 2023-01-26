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

    @ApiOperation("Gets invoice to be printed")
    @RequestMapping(path = "/api/getInvoice/{randomKey}",method = RequestMethod.GET)
    public InvoiceDetails getInvoice(@PathVariable String randomKey) throws ApiException{
        List<InvoiceItem> invoiceItemList = downloadPdfDto.convertToInvoiceItem(randomKey);
        return downloadPdfService.getInvoice(randomKey,invoiceItemList);
    }

    @ApiOperation("Prints pdf")
    @RequestMapping(path="api/printPdf/{invoiceEncoded}",method = RequestMethod.POST)
    public void downloadInvoice(@PathVariable String invoiceEncoded) throws ApiException{

    }
}
