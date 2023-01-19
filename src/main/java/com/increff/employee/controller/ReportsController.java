package com.increff.employee.controller;

import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Api
@RestController
public class ReportsController {
    @Autowired
    private ReportService service;

    @ApiOperation(value = "Gets sales report")
    @RequestMapping(path = "/api/reportsController",method = RequestMethod.POST)
    public SalesReportData getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException, ParseException {
         return service.getSalesReport(salesReportForm);
    }
}
