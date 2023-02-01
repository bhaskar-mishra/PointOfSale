package com.increff.pos.dto;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.SchedulerData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.SchedulerPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerDto {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    public List<SchedulerData> getAllDailyReport() throws ApiException {
        List<SchedulerPojo> schedulerPojoList = schedulerService.getAllSchedules();
        return convertToSchedulerData(schedulerPojoList);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void createDailyReport() throws ApiException {
        SchedulerPojo schedulerPojo = new SchedulerPojo();
        LocalDate date = LocalDate.now();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;

        String startDate = null;
        String endDate = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            startDate = LocalDate.parse(date.toString(), formatter).atStartOfDay().toString();
            endDate = LocalDate.parse(date.toString(), formatter).atTime(23, 59, 59).toString();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

        List<OrderPojo> orderPojoList = orderService.selectOrderWithDateFilter(startDate, endDate);

        Integer totalOrders = orderPojoList.size();

        for (OrderPojo o : orderPojoList) {
            String id = o.getOrderCode();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItems(id);
            for (OrderItemPojo i : orderItemPojoList) {
                totalItems += i.getQuantity();
                totalRevenue += i.getQuantity() * i.getSellingPrice();
            }
        }

        schedulerPojo.setDate(date.toString());
        schedulerPojo.setRevenue(totalRevenue);
        schedulerPojo.setInvoicedItemsCount(totalItems);
        schedulerPojo.setInvoicedOrdersCount(totalOrders);

        SchedulerPojo pojo = schedulerService.selectByDate(date.toString());
        if (pojo == null)
            schedulerService.addSchedule(schedulerPojo);
        else
            schedulerService.update(schedulerPojo, pojo.getDate());
    }

    private List<SchedulerData> convertToSchedulerData(List<SchedulerPojo> schedulerPojoList) throws ApiException {
        List<SchedulerData> schedulerDataList = new ArrayList<>();
        for (SchedulerPojo schedulerPojo : schedulerPojoList) {
            SchedulerData schedulerData = new SchedulerData();
            schedulerData.setDate(schedulerPojo.getDate());
            schedulerData.setTotal_revenue(schedulerPojo.getRevenue());
            schedulerData.setInvoiced_items_count(schedulerPojo.getInvoicedItemsCount());
            schedulerData.setInvoiced_orders_count(schedulerPojo.getInvoicedOrdersCount());
            schedulerDataList.add(schedulerData);
        }

        return schedulerDataList;
    }

}