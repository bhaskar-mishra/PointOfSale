package com.increff.employee.service;


import com.increff.employee.dao.SchedulerDao;
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
    public void addSchedule(SchedulerPojo pojo) throws ApiException {
        schedulerDao.insert(pojo);
    }

    public SchedulerPojo selectByDate(String date) {
        return schedulerDao.selectByDate(date);
    }

    public void update(SchedulerPojo pojo, String date) {
        SchedulerPojo schedulerPojo = schedulerDao.selectByDate(date);
        schedulerPojo.setRevenue(pojo.getRevenue());
        schedulerPojo.setInvoicedItemsCount(pojo.getInvoicedItemsCount());
        schedulerPojo.setInvoicedOrdersCount(pojo.getInvoicedOrdersCount());
    }

}
