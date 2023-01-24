package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.helper.SalesReportHelper;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.pojo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Service
public class ReportService {
  @Autowired
  private OrderDao orderDao;
  @Autowired
  private OrderService orderService;
  @Autowired
  private BrandDao brandDao;
  @Autowired
  private OrderItemDao orderItemDao;

  @Autowired
  private ProductDao productDao;

  @Transactional
  public SalesReportData getSalesReportForABrandCategoryInGivenTime(SalesReportForm salesReportForm) throws ApiException, ParseException {
       List<OrderData> orderList = orderService.getAll();
       List<Integer> orders = new ArrayList<>();
       for(OrderData obj : orderList)
       {
           if(obj.getStatus()== (Status.PENDING).name()) continue;
          String time = obj.getTime();
          String[] dateTime = time.split(" ");
          String date = dateTime[0];
           String orderDate= date;
           String startDate = salesReportForm.getStartDate();
           String endDate = salesReportForm.getEndDate();
           if(orderDate.compareTo(startDate)>=0 && orderDate.compareTo(endDate)<=0)
           {
               orders.add(obj.getOrderId());
           }
       }

       int brandCategoryId = brandDao.selectByBrandCategory(salesReportForm.getBrand(), salesReportForm.getCategory()).getId();
       int quantity = 0;
       double revenue = 0;

       for(int orderId : orders)
       {
           List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);
           for(OrderItemPojo pojo : orderItemPojoList)
           {
               ProductPojo productPojo = productDao.selectByBarcode(pojo.getProduct());
               if(productPojo.getBrandCategoryId()==brandCategoryId)
               {
                   quantity+=(pojo.getQuantity());
                   revenue+=(pojo.getQuantity()* pojo.getPrice());
               }
           }
       }

      SalesReportData salesReportData = new SalesReportData();
       salesReportData.setBrand(salesReportForm.getBrand());
       salesReportData.setCategory(salesReportForm.getCategory());
       salesReportData.setQuantity(quantity);
       salesReportData.setRevenue(revenue);
     return salesReportData;
  }

  @Transactional
  public List<SalesReportData> getAllSales() throws ApiException{
      List<OrderData> orderList = orderService.getAll();
      List<Integer> orders = new ArrayList<>();
      List<SalesReportData> salesReportDataList = new ArrayList<>();

      //gets all the orders that are placed
      for(OrderData orderData : orderList){
          if(orderData.getStatus().equals(Status.COMPLETE.name())){
              orders.add(orderData.getOrderId());
          }
      }
      Map<String,List<SalesReportHelper>> brandCategoryDataMap = new HashMap<>();

      for(Integer orderId : orders){
          List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

          for(OrderItemPojo orderItemPojo : orderItemPojoList){
              ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
              String brand = productPojo.getBrand();
              String category = productPojo.getCategory();
              Integer quantity = orderItemPojo.getQuantity();
              Double price = orderItemPojo.getPrice();
              Double revenue = (price*quantity);
              if(!brandCategoryDataMap.containsKey(brand))
              {
                  brandCategoryDataMap.put(brand,new ArrayList<>());
                  brandCategoryDataMap.get(brand).add(new SalesReportHelper(category,quantity,revenue));
              }else{
                  List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(brand);
                  boolean flag = false;
                  for(int i = 0;i<salesReportHelperList.size();++i)
                  {
                      if(salesReportHelperList.get(i).getCategory().equals(category))
                      {
                          salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                          salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                          flag = true;
                          break;
                      }
                  }

                  if(!flag){
                      salesReportHelperList.add(new SalesReportHelper(category,quantity,revenue));
                  }
              }
          }
      }

      for(String brand : brandCategoryDataMap.keySet()){
          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(brand);
          for(SalesReportHelper salesReportHelper : salesReportHelperList){
              SalesReportData salesReportData = new SalesReportData();
              salesReportData.setBrand(brand);
              salesReportData.setCategory(salesReportHelper.getCategory());
              salesReportData.setQuantity(salesReportHelper.getQuantity());
              salesReportData.setRevenue(salesReportHelper.getRevenue());
              salesReportDataList.add(salesReportData);
          }
      }
      return salesReportDataList;
  }



  //Get sales report based on the input given by the user

  @Transactional
  public List<SalesReportData> getSalesOnInput(SalesReportForm salesReportForm) throws ApiException, ParseException {
      String startDate = salesReportForm.getStartDate();
      String endDate = salesReportForm.getEndDate();
      String brand = salesReportForm.getBrand();
      String category = salesReportForm.getCategory();

      if(brand==null) brand = "";
      if(category==null) category = "";

      System.out.println("startDate: "+startDate);
      System.out.println("endDate: "+endDate);
      System.out.println("brand : "+brand);
      System.out.println("category : "+category);

      List<SalesReportData> salesReportDataList = new ArrayList<>();
      List<OrderData> orderList = orderService.getAll();
      List<Integer> orders = new ArrayList<>();

      //IF DATE FILTER IS GIVEN
      if(!startDate.equals("")){
          for(OrderData orderData : orderList)
          {
              if(orderData.getStatus().equals((Status.PENDING).name())) continue;
              String time = orderData.getTime();
              String[] dateTime = time.split(" ");
              String date = dateTime[0];
              if(date.compareTo(startDate)>=0 && date.compareTo(endDate)<=0){
                  orders.add(orderData.getOrderId());
              }
          }

          Map<String,List<SalesReportHelper>> brandCategoryDataMap = new HashMap<>();

          //neither brand nor category is given
          if(brand.equals("") && category.equals("")){
              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      Integer quantity = orderItemPojo.getQuantity();
                      Double price = orderItemPojo.getPrice();
                      Double revenue = (price*quantity);
                      if(!brandCategoryDataMap.containsKey(productPojo.getBrand()))
                      {
                          brandCategoryDataMap.put(productPojo.getBrand(),new ArrayList<>());
                          brandCategoryDataMap.get(productPojo.getBrand()).add(new SalesReportHelper(productPojo.getCategory(), quantity,revenue));
                      }else{
                          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(productPojo.getBrand());
                          boolean flag = false;
                          for(int i = 0;i<salesReportHelperList.size();++i)
                          {
                              if(salesReportHelperList.get(i).getCategory().equals(productPojo.getCategory()))
                              {
                                  salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                                  salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                                  flag = true;
                                  break;
                              }
                          }

                          if(!flag){
                              salesReportHelperList.add(new SalesReportHelper(productPojo.getCategory(),quantity,revenue));
                          }
                      }
                  }
              }

          }else if(brand.equals(""))
          {
                //category is given but brand is not given
              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      if(!productPojo.getCategory().equals(category)) continue;
                      Integer quantity = orderItemPojo.getQuantity();
                      Double price = orderItemPojo.getPrice();
                      Double revenue = (price*quantity);
                      if(!brandCategoryDataMap.containsKey(productPojo.getCategory()))
                      {
                          brandCategoryDataMap.put(productPojo.getCategory(),new ArrayList<>());
                          brandCategoryDataMap.get(productPojo.getCategory()).add(new SalesReportHelper(productPojo.getBrand(), quantity,revenue,true));
                      }else{
                          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(productPojo.getCategory());
                          boolean flag = false;
                          for(int i = 0;i<salesReportHelperList.size();++i)
                          {
                              if(salesReportHelperList.get(i).getBrand().equals(productPojo.getCategory()))
                              {
                                  salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                                  salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                                  flag = true;
                                  break;
                              }
                          }

                          if(!flag){
                              salesReportHelperList.add(new SalesReportHelper(productPojo.getBrand(),quantity,revenue,true));
                          }
                      }
                  }
              }

          }else if(category.equals("")){
              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      if(!productPojo.getBrand().equals(brand)) continue;
                      Integer quantity = orderItemPojo.getQuantity();
                      Double price = orderItemPojo.getPrice();
                      Double revenue = (price*quantity);
                      if(!brandCategoryDataMap.containsKey(productPojo.getBrand()))
                      {
                          brandCategoryDataMap.put(productPojo.getBrand(),new ArrayList<>());
                          brandCategoryDataMap.get(productPojo.getBrand()).add(new SalesReportHelper(productPojo.getCategory(), quantity,revenue));
                      }else{
                          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(productPojo.getBrand());
                          boolean flag = false;
                          for(int i = 0;i<salesReportHelperList.size();++i)
                          {
                              if(salesReportHelperList.get(i).getCategory().equals(productPojo.getCategory()))
                              {
                                  salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                                  salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                                  flag = true;
                                  break;
                              }
                          }

                          if(!flag){
                              salesReportHelperList.add(new SalesReportHelper(productPojo.getCategory(),quantity,revenue));
                          }
                      }
                  }
              }

          }else {
              salesReportDataList.add(getSalesReportForABrandCategoryInGivenTime(salesReportForm));
          }

          for(String brandData : brandCategoryDataMap.keySet()){
              List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(brandData);
              for(SalesReportHelper salesReportHelper : salesReportHelperList){
                  SalesReportData salesReportData = new SalesReportData();
                  salesReportData.setBrand(brandData);
                  salesReportData.setCategory(salesReportHelper.getCategory());
                  salesReportData.setQuantity(salesReportHelper.getQuantity());
                  salesReportData.setRevenue(salesReportHelper.getRevenue());
                  salesReportDataList.add(salesReportData);
              }
          }
      }else
      {
          for(OrderData orderData : orderList)
          {
              if(orderData.getStatus().equals((Status.PENDING).name())) continue;
              orders.add(orderData.getOrderId());
          }
          Map<String,List<SalesReportHelper>> brandCategoryDataMap = new HashMap<>();
          if(brand.equals("") && !category.equals("")){
              //category is given but brand is not given
              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      if(!productPojo.getCategory().equals(category)) continue;
                      Integer quantity = orderItemPojo.getQuantity();
                      Double price = orderItemPojo.getPrice();
                      Double revenue = (price*quantity);
                      if(!brandCategoryDataMap.containsKey(productPojo.getCategory()))
                      {
                          brandCategoryDataMap.put(productPojo.getCategory(),new ArrayList<>());
                          brandCategoryDataMap.get(productPojo.getCategory()).add(new SalesReportHelper(productPojo.getBrand(), quantity,revenue,true));
                      }else{
                          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(productPojo.getCategory());
                          boolean flag = false;
                          for(int i = 0;i<salesReportHelperList.size();++i)
                          {
                              if(salesReportHelperList.get(i).getBrand().equals(productPojo.getCategory()))
                              {
                                  salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                                  salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                                  flag = true;
                                  break;
                              }
                          }

                          if(!flag){
                              salesReportHelperList.add(new SalesReportHelper(productPojo.getBrand(),quantity,revenue,true));
                          }
                      }
                  }
              }
          }else if(category.equals("") && !brand.equals("")){

              System.out.println("inside the if block with no date chosen and no category chosen");

              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      if(!productPojo.getBrand().equals(brand)) continue;
                      Integer quantity = orderItemPojo.getQuantity();
                      Double price = orderItemPojo.getPrice();
                      Double revenue = (price*quantity);
                      if(!brandCategoryDataMap.containsKey(productPojo.getBrand()))
                      {
                          brandCategoryDataMap.put(productPojo.getBrand(),new ArrayList<>());
                          brandCategoryDataMap.get(productPojo.getBrand()).add(new SalesReportHelper(productPojo.getCategory(), quantity,revenue));
                      }else{
                          List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(productPojo.getBrand());
                          boolean flag = false;
                          for(int i = 0;i<salesReportHelperList.size();++i)
                          {
                              if(salesReportHelperList.get(i).getCategory().equals(productPojo.getCategory()))
                              {
                                  salesReportHelperList.get(i).setQuantity(salesReportHelperList.get(i).getQuantity()+quantity);
                                  salesReportHelperList.get(i).setRevenue(salesReportHelperList.get(i).getRevenue()+revenue);
                                  flag = true;
                                  break;
                              }
                          }

                          if(!flag){
                              salesReportHelperList.add(new SalesReportHelper(productPojo.getCategory(),quantity,revenue));
                          }
                      }
                  }
              }


          }else{
              Integer quantity = 0;
              Double revenue = 0.0;
              for(Integer orderId : orders){
                  List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);

                  for(OrderItemPojo orderItemPojo : orderItemPojoList){
                      ProductPojo productPojo = productDao.selectByBarcode(orderItemPojo.getBarcode());
                      if(productPojo.getCategory().equals(category) && productPojo.getBrand().equals(brand))
                      {
                          quantity+=orderItemPojo.getQuantity();
                          revenue+=(orderItemPojo.getPrice()*orderItemPojo.getQuantity());
                      }
                  }
              }

              SalesReportData salesReportData = new SalesReportData();
              salesReportData.setRevenue(revenue);
              salesReportData.setBrand(brand);
              salesReportData.setCategory(category);
              salesReportData.setQuantity(quantity);
              salesReportDataList.add(salesReportData);
          }

          for(String brandData : brandCategoryDataMap.keySet()){
              List<SalesReportHelper> salesReportHelperList = brandCategoryDataMap.get(brandData);
              for(SalesReportHelper salesReportHelper : salesReportHelperList){
                  SalesReportData salesReportData = new SalesReportData();
                  salesReportData.setBrand(brandData);
                  salesReportData.setCategory(salesReportHelper.getCategory());
                  salesReportData.setQuantity(salesReportHelper.getQuantity());
                  salesReportData.setRevenue(salesReportHelper.getRevenue());
                  salesReportDataList.add(salesReportData);
              }
          }
      }

      
      return salesReportDataList;
  }
}
