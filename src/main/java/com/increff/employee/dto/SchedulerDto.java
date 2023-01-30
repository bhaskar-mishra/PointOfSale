package com.increff.employee.dto;


import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.SchedulerData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerDto {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;

    public List<SchedulerData> getAllSchedules() throws ApiException{
        List<SchedulerPojo> schedulerPojoList = schedulerService.getAllSchedules();
        return convertToSchedulerData(schedulerPojoList);
    }

    public void addSchedule(String random_key_for_id) throws ApiException{
        OrderPojo orderPojo = orderDao.selectByRandomKey(random_key_for_id);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAllByRandomKey(random_key_for_id);
        schedulerService.addSchedule(random_key_for_id,orderPojo,orderItemPojoList);
    }

    private List<SchedulerData> convertToSchedulerData(List<SchedulerPojo> schedulerPojoList) throws ApiException{
        List<SchedulerData> schedulerDataList = new ArrayList<>();
        for(SchedulerPojo schedulerPojo : schedulerPojoList){
            SchedulerData schedulerData = new SchedulerData();
            System.out.println("date : "+schedulerPojo.getDate());
            System.out.println("invoice_orders_count : "+schedulerPojo.getInvoicedOrdersCount());
            System.out.println("invoice_items_count : "+schedulerPojo.getInvoicedItemsCount());
            System.out.println("total_revenue : "+schedulerPojo.getRevenue());
            schedulerData.setDate(schedulerPojo.getDate());
            schedulerData.setTotal_revenue(schedulerPojo.getRevenue());
            schedulerData.setInvoiced_items_count(schedulerPojo.getInvoicedItemsCount());
            schedulerData.setInvoiced_orders_count(schedulerPojo.getInvoicedOrdersCount());
            schedulerDataList.add(schedulerData);
        }

        return schedulerDataList;
    }

}
