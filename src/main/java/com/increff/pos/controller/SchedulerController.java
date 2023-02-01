package com.increff.pos.controller;


import com.increff.pos.dto.SchedulerDto;
import com.increff.pos.model.SchedulerData;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private SchedulerDto schedulerDto;

    @ApiOperation(value = "gets all schedules")
    @RequestMapping(path = "/api/scheduler/getAllSchedules",method = RequestMethod.GET)
    public List<SchedulerData> getAllDailyReport() throws ApiException{
         return schedulerDto.getAllDailyReport();
    }

}
