package com.increff.employee.controller;

import com.increff.employee.dto.DownloadPdfDto;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItem;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DownloadPdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping(path = "/api/getInvoice/{random_key_for_order}",method = RequestMethod.GET)
    public String getInvoice(@PathVariable String random_key_for_order) throws ApiException, IOException {
        return downloadPdfDto.getInvoice(random_key_for_order);
    }

}
