package com.increff.employee.dto;


import com.increff.employee.model.SchedulerData;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerDto {

    public List<SchedulerData> convertToSchedulerData(List<SchedulerPojo> schedulerPojoList) throws ApiException{
        List<SchedulerData> schedulerDataList = new ArrayList<>();
        for(SchedulerPojo schedulerPojo : schedulerPojoList){
            SchedulerData schedulerData = new SchedulerData();
            System.out.println("date : "+schedulerPojo.getDate());
            System.out.println("invoice_orders_count : "+schedulerPojo.getInvoiced_orders_count());
            System.out.println("invoice_items_count : "+schedulerPojo.getInvoiced_items_count());
            System.out.println("total_revenue : "+schedulerPojo.getRevenue());
            schedulerData.setDate(schedulerPojo.getDate());
            schedulerData.setTotal_revenue(schedulerPojo.getRevenue());
            schedulerData.setInvoiced_items_count(schedulerPojo.getInvoiced_items_count());
            schedulerData.setInvoiced_orders_count(schedulerPojo.getInvoiced_orders_count());
            schedulerDataList.add(schedulerData);
        }

        return schedulerDataList;
    }

}
