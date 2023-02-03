package com.increff.pos.controller;


import com.increff.pos.dto.ReportsDto;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportsController {

    @Autowired
    private ReportsDto reportsDto;


    @ApiOperation(value = "get sales Report")
    @RequestMapping(path = "/api/reports/salesReport",method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException{
            return reportsDto.getSalesReport(salesReportForm);
    }



    @ApiOperation(value = "gets all schedules")
    @RequestMapping(path = "/api/scheduler/getAllSchedules",method = RequestMethod.GET)
    public List<SchedulerData> getAllDailyReport() throws ApiException{
         return reportsDto.getAllDailyReport();
    }



    @ApiOperation(value = "gets inventory report")
    @RequestMapping(path = "/api/reports/inventoryReport",method = RequestMethod.POST)
    public List<InventoryReportData> getInventoryReport(@RequestBody BrandForm brandForm) throws ApiException{
        return reportsDto.getInventoryReport(brandForm);
    }

}
