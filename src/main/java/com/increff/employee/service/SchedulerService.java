package com.increff.employee.service;


import com.increff.employee.dao.SchedulerDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private SchedulerDao schedulerDao;


    public List<SchedulerPojo> getAllSchedules() throws ApiException {
        List<SchedulerPojo> schedulerPojoList = schedulerDao.selectAll();
        return schedulerPojoList;
    }

    @Transactional
    public void addSchedule(String randomKeyForOrder,OrderPojo orderPojo,List<OrderItemPojo> orderItemPojoList) throws ApiException{
        String date = orderPojo.getPlacedTime().split(" ")[0];
        Integer invoiced_item_count = 0;
        Double revenue = 0.0;
        for (OrderItemPojo orderItemPojo : orderItemPojoList){
            if(orderItemPojo.getRandomKey().equals(randomKeyForOrder)){
                invoiced_item_count+=orderItemPojo.getQuantity();
                revenue+=(orderItemPojo.getPrice()*orderItemPojo.getQuantity());
            }
        }

        SchedulerPojo schedulerPojo = schedulerDao.selectByDate(date);
        if(schedulerPojo==null){
            schedulerPojo = new SchedulerPojo();
            schedulerPojo.setDate(date);
            schedulerPojo.setRevenue(revenue);
            schedulerPojo.setInvoicedItemsCount(invoiced_item_count);
            schedulerPojo.setInvoicedOrdersCount(1);
            schedulerDao.insert(schedulerPojo);
        }else{
            schedulerPojo.setInvoicedOrdersCount(schedulerPojo.getInvoicedOrdersCount()+1);
            schedulerPojo.setRevenue(schedulerPojo.getRevenue()+revenue);
            schedulerPojo.setInvoicedItemsCount(schedulerPojo.getInvoicedItemsCount()+invoiced_item_count);
        }
    }

}
