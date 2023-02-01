package com.increff.pos.controller;

import com.increff.pos.dto.DownloadPdfDto;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.DownloadPdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
