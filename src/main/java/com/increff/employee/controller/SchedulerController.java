package com.increff.employee.controller;


import com.increff.employee.dto.SchedulerDto;
import com.increff.employee.model.SchedulerData;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<SchedulerData> getAllSchedules() throws ApiException{
         List<SchedulerPojo> schedulerPojoList = schedulerService.getAllSchedules();
         return schedulerDto.convertToSchedulerData(schedulerPojoList);
    }

    @ApiOperation(value = "add a schedule")
    @RequestMapping(path = "/api/scheduler/addSchedule/{randomKeyForOrder}",method = RequestMethod.POST)
    public void addSchedule(@PathVariable String randomKeyForOrder) throws ApiException{
        System.out.println("reaching add schedule");
        schedulerService.addSchedule(randomKeyForOrder);
    }
}
