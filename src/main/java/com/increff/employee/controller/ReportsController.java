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
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ReportsController {
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Gets sales report")
    @RequestMapping(path = "/api/reportsController",method = RequestMethod.POST)
    public SalesReportData getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException, ParseException {
         return reportService.getSalesReportForABrandCategoryInGivenTime(salesReportForm);
    }

    @ApiOperation(value = "Gets all sales")
    @RequestMapping(path = "/api/reportController/allSales",method = RequestMethod.GET)
    public List<SalesReportData> getAllSales() throws ApiException {
       return reportService.getAllSales();
    }

    @ApiOperation(value = "Gets sales according to user input")
    @RequestMapping(path = "/api/reportsController/onUserInput",method = RequestMethod.POST)
    public List<SalesReportData> getSalesOnInput(@RequestBody SalesReportForm salesReportForm) throws ApiException, ParseException {
        System.out.println("Inside getSalesOnInput method in ReportsController");
        return reportService.getSalesOnInput(salesReportForm);
    }
}
