package com.increff.employee.service;


import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.SchedulerDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private SchedulerDao schedulerDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;

    public List<SchedulerPojo> getAllSchedules() throws ApiException {
        List<SchedulerPojo> schedulerPojoList = schedulerDao.selectAll();
        return schedulerPojoList;
    }

    @Transactional
    public void addSchedule(String randomKeyForOrder) throws ApiException{
        OrderPojo orderPojo = orderDao.selectByRandomKey(randomKeyForOrder);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAllByRandomKey(randomKeyForOrder);
        String date = orderPojo.getTime().split(" ")[0];
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
            schedulerPojo.setInvoiced_items_count(invoiced_item_count);
            schedulerPojo.setInvoiced_orders_count(1);
            schedulerDao.insert(schedulerPojo);
        }else{
            schedulerPojo.setInvoiced_orders_count(schedulerPojo.getInvoiced_orders_count()+1);
            schedulerPojo.setRevenue(schedulerPojo.getRevenue()+revenue);
            schedulerPojo.setInvoiced_items_count(schedulerPojo.getInvoiced_items_count()+invoiced_item_count);
        }
    }

}
