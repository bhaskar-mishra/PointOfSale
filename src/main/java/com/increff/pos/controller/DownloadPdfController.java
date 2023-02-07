package com.increff.pos.controller;


import com.increff.pos.dto.DownloadPdfDto;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class DownloadPdfController {
    @Autowired
    private DownloadPdfDto downloadPdfDto;

    @ApiOperation("returns Base64 encoded string for invoice")
    @RequestMapping(path = "/api/generateInvoice/{orderCode}",method = RequestMethod.GET)
    public String generateInvoice(@PathVariable String orderCode) throws ApiException{
        return downloadPdfDto.generateInvoice(orderCode);
    }
}
