package com.increff.pos.dto;

import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.model.SchedulerData;
import com.increff.pos.pojo.*;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsDto {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException{
          validateSalesReportForm(salesReportForm);
          salesReportForm.setBrand(salesReportForm.getBrand().toLowerCase().trim());
          salesReportForm.setCategory(salesReportForm.getCategory().toLowerCase().trim());
          ZonedDateTimeConverter zonedDateTimeConverter = new ZonedDateTimeConverter();
          ZonedDateTime startDateTime;
          try{
              startDateTime = zonedDateTimeConverter.convert(salesReportForm.getStartDate());
          }catch (Exception exception){
              throw new ApiException("input valid dates");
          }
          ZonedDateTime endDateTime;
          zonedDateTimeConverter = new ZonedDateTimeConverter();
          try{
              endDateTime = zonedDateTimeConverter.convert(salesReportForm.getEndDate());
          }catch (Exception exception){
              throw new ApiException("input valid dates");
          }


          List<OrderPojo> orderPojoList = orderService.selectOrderWithDateFilter(startDateTime,endDateTime);

          Map<String,List<SalesReportData>> salesReportMap = new HashMap<>();

          for(OrderPojo orderPojo : orderPojoList){
              List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
              for(OrderItemPojo orderItemPojo : orderItemPojoList){
                  ProductPojo productPojo = productService.selectById(orderItemPojo.getProductId());

                  if((salesReportForm.getBrand().equals("ALL")
                          || salesReportForm.getBrand().equals(productPojo.getBrand()))
                  && (salesReportForm.getCategory().equals("ALL")
                  || (salesReportForm.getCategory().equals(productPojo.getCategory())))){
                      addToSalesReport(salesReportMap,orderItemPojo,productPojo.getBrand(),productPojo.getCategory());
                  }
              }

          }

          List<SalesReportData> salesReportDataList = new ArrayList<>();
          for(String brand : salesReportMap.keySet()){
              List<SalesReportData> salesReportData = salesReportMap.get(brand);
              salesReportDataList.addAll(salesReportData);
          }

          return salesReportDataList;

    }

    private void addToSalesReport(Map<String,List<SalesReportData>> salesReportMap,OrderItemPojo orderItemPojo,String brand,String category){
        if(!salesReportMap.containsKey(brand)){
            salesReportMap.put(brand,new ArrayList<>());
        }

        List<SalesReportData> salesReportDataList = salesReportMap.get(brand);
        boolean categoryPresent = false;
        for(SalesReportData salesReportData : salesReportDataList){
            if(salesReportData.getCategory().equals(category)){
                salesReportData.setQuantity(salesReportData.getQuantity()+orderItemPojo.getQuantity());
                salesReportData.setRevenue(salesReportData.getRevenue()
                        +(orderItemPojo.getQuantity()*orderItemPojo.getSellingPrice()));

                categoryPresent = true;
                break;
            }
        }

        if(!categoryPresent){
            SalesReportData salesReportData = new SalesReportData();
            salesReportData.setBrand(brand);
            salesReportData.setCategory(category);
            salesReportData.setQuantity(orderItemPojo.getQuantity());
            salesReportData.setRevenue((orderItemPojo.getSellingPrice()* orderItemPojo.getQuantity()));
            salesReportDataList.add(salesReportData);
        }
        salesReportMap.put(brand,salesReportDataList);
    }

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

    private void validateSalesReportForm(SalesReportForm salesReportForm) throws ApiException{
        if(salesReportForm.getStartDate()==null || salesReportForm.getEndDate()==null){
            throw new ApiException("please input valid start and end dates");
        }

        if(salesReportForm.getBrand()==null || salesReportForm.getBrand().trim().equals("")){
            throw new ApiException("input a valid brand");
        }

        if(salesReportForm.getCategory()==null || salesReportForm.getCategory().trim().equals("")){
            throw new ApiException("input a valid category");
        }
    }

}
