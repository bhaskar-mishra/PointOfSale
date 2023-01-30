package com.increff.employee.dto;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.SchedulerData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.SchedulerService;
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
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;

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
                totalRevenue += i.getQuantity() * i.getPrice();
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
            System.out.println("date : " + schedulerPojo.getDate());
            System.out.println("invoice_orders_count : " + schedulerPojo.getInvoicedOrdersCount());
            System.out.println("invoice_items_count : " + schedulerPojo.getInvoicedItemsCount());
            System.out.println("total_revenue : " + schedulerPojo.getRevenue());
            schedulerData.setDate(schedulerPojo.getDate());
            schedulerData.setTotal_revenue(schedulerPojo.getRevenue());
            schedulerData.setInvoiced_items_count(schedulerPojo.getInvoicedItemsCount());
            schedulerData.setInvoiced_orders_count(schedulerPojo.getInvoicedOrdersCount());
            schedulerDataList.add(schedulerData);
        }

        return schedulerDataList;
    }

}
