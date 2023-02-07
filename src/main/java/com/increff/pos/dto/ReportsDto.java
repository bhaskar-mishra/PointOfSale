package com.increff.pos.dto;

import com.increff.pos.model.data.*;
import com.increff.pos.model.form.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportsDto {

    @Autowired
    private DailySalesReportService dailySalesReportService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException{
          DtoUtils.validateSalesReportForm(salesReportForm);
          salesReportForm.setBrand(salesReportForm.getBrand().toLowerCase().trim());
          salesReportForm.setCategory(salesReportForm.getCategory().toLowerCase().trim());
          salesReportForm.setStartDate(salesReportForm.getStartDate().trim());
          salesReportForm.setEndDate(salesReportForm.getEndDate().trim());


        ZonedDateTime startDateTime;
        ZonedDateTime endDateTime;
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDateTime = LocalDate.parse(salesReportForm.getStartDate(), dtf).atStartOfDay(ZoneId.systemDefault());
            endDateTime = LocalDate.parse(salesReportForm.getEndDate(), dtf).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);

        }catch (Exception exception){
            throw new ApiException("enter valid dates");
        }



          List<OrderPojo> orderPojoList = orderService.selectOrderWithDateFilter(startDateTime,endDateTime);

          Map<String,List<SalesReportData>> salesReportMap = new HashMap<>();

          for(OrderPojo orderPojo : orderPojoList){
              List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
              for(OrderItemPojo orderItemPojo : orderItemPojoList){
                  ProductPojo productPojo = productService.selectByProductId(orderItemPojo.getProductId());

                  if((salesReportForm.getBrand().equals("all")
                          || salesReportForm.getBrand().equals(productPojo.getBrand()))
                  && (salesReportForm.getCategory().equals("all")
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

    public List<InventoryReportData> getInventoryReport(BrandForm brandForm) throws ApiException{
        DtoUtils.validateBrandForm(brandForm);
        brandForm.setBrand(brandForm.getBrand().toLowerCase().trim());
        brandForm.setCategory(brandForm.getCategory().toLowerCase().trim());
        List<BrandPojo> brandPojoList = new ArrayList<>();
        if(brandForm.getBrand().equals("all") || brandForm.getCategory().equals("all")){
            brandPojoList.addAll(brandService.getAllBrandCategories());
        }else{
            brandPojoList.add(brandService.selectByBrandCategory(brandForm.getBrand(), brandForm.getCategory()));
        }

        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
        for(BrandPojo brandPojo : brandPojoList){
            if((brandForm.getBrand().equals("all")||brandForm.getBrand().equals(brandPojo.getBrand()))
               && (brandForm.getCategory().equals("all")||brandForm.getCategory().equals(brandPojo.getCategory()))){
                addToInventoryReport(inventoryReportDataList,brandPojo);
            }
        }

        return inventoryReportDataList;
    }


    private void addToInventoryReport(List<InventoryReportData> inventoryReportDataList,BrandPojo brandPojo) throws ApiException {
        Integer quantity = 0;
        List<ProductPojo> productPojoList = productService.selectByBrandCategoryId(brandPojo.getId());
        InventoryReportData inventoryReportData = new InventoryReportData();
        for(ProductPojo productPojo : productPojoList){
            try{
                InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());
                quantity+= inventoryPojo.getQuantity();
            }catch (Exception exception){

            }

        }
        inventoryReportData.setBrand(brandPojo.getBrand());
        inventoryReportData.setCategory(brandPojo.getCategory());
        inventoryReportData.setQuantity(quantity);
        inventoryReportDataList.add(inventoryReportData);
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
        List<DailySalesReportPojo> dailySalesReportPojoList = dailySalesReportService.getAllSchedules();
        return convertToSchedulerData(dailySalesReportPojoList);
    }

    public void createDailyReport() throws ApiException {
        DailySalesReportPojo dailySalesReportPojo = new DailySalesReportPojo();
        LocalDate date = LocalDate.now();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;

        ZonedDateTime startDateTime;
        ZonedDateTime endDateTime;
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDateTime = LocalDate.parse(date.toString(), dtf).atStartOfDay(ZoneId.systemDefault());
            endDateTime = LocalDate.parse(date.toString(), dtf).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);
        }catch (Exception exception){
            throw new ApiException("enter valid dates");
        }

        List<OrderPojo> orderPojoList = orderService.selectOrderWithDateFilter(startDateTime, endDateTime);

        Integer totalOrders = orderPojoList.size();

        for (OrderPojo o : orderPojoList) {
            String id = o.getOrderCode();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItems(id);
            for (OrderItemPojo i : orderItemPojoList) {
                totalItems += i.getQuantity();
                totalRevenue += i.getQuantity() * i.getSellingPrice();
            }
        }

        dailySalesReportPojo.setDate(date.toString());
        dailySalesReportPojo.setRevenue(totalRevenue);
        dailySalesReportPojo.setInvoicedItemsCount(totalItems);
        dailySalesReportPojo.setInvoicedOrdersCount(totalOrders);

        DailySalesReportPojo pojo = dailySalesReportService.selectByDate(date.toString());
        if (pojo == null) {
            dailySalesReportService.addSchedule(dailySalesReportPojo);
        }
        else {
            dailySalesReportService.update(dailySalesReportPojo, pojo.getDate());
        }
    }

    private List<SchedulerData> convertToSchedulerData(List<DailySalesReportPojo> dailySalesReportPojoList) throws ApiException {
        List<SchedulerData> schedulerDataList = new ArrayList<>();
        for (DailySalesReportPojo dailySalesReportPojo : dailySalesReportPojoList) {
            SchedulerData schedulerData = new SchedulerData();
            schedulerData.setDate(dailySalesReportPojo.getDate());
            schedulerData.setTotal_revenue(dailySalesReportPojo.getRevenue());
            schedulerData.setInvoiced_items_count(dailySalesReportPojo.getInvoicedItemsCount());
            schedulerData.setInvoiced_orders_count(dailySalesReportPojo.getInvoicedOrdersCount());
            schedulerDataList.add(schedulerData);
        }

        return schedulerDataList;
    }

}
