package com.increff.pos.service;


import com.increff.pos.dao.SchedulerDao;
import com.increff.pos.pojo.DailySalesReportPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class DailySalesReportService {

    @Autowired
    private SchedulerDao schedulerDao;


    public List<DailySalesReportPojo> getAllSchedules() throws ApiException {
        List<DailySalesReportPojo> dailySalesReportPojoList = schedulerDao.selectAll();
        return dailySalesReportPojoList;
    }


    public void addSchedule(DailySalesReportPojo pojo) throws ApiException {
        schedulerDao.insert(pojo);
    }

    public DailySalesReportPojo selectByDate(String date) {
        return schedulerDao.selectByDate(date);
    }

    public void update(DailySalesReportPojo pojo, String date) {
        DailySalesReportPojo dailySalesReportPojo = schedulerDao.selectByDate(date);
        dailySalesReportPojo.setRevenue(pojo.getRevenue());
        dailySalesReportPojo.setInvoicedItemsCount(pojo.getInvoicedItemsCount());
        dailySalesReportPojo.setInvoicedOrdersCount(pojo.getInvoicedOrdersCount());
    }

}
