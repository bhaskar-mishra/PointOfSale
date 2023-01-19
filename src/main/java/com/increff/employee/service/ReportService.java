package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  public SalesReportData getSalesReport(SalesReportForm salesReportForm) throws ApiException, ParseException {
       List<OrderData> orderList = orderService.getAll();
       List<Integer> orders = new ArrayList<>();
       for(OrderData obj : orderList)
       {
           if(obj.getStatus()== (Status.PENDING).name()) continue;
          String time = obj.getTime();
          String[] dateTime = time.split(" ");
          String date = dateTime[0];
           Date orderDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
           Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(salesReportForm.getStartDate());
           Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(salesReportForm.getEndDate());
           if(orderDate.compareTo(startDate)>=0 && orderDate.compareTo(endDate)<=0)
           {
               orders.add(obj.getOrderId());
           }
       }

       int brandCategoryId = brandDao.select(salesReportForm.getBrand(), salesReportForm.getCategory()).getId();
       int quantity = 0;
       double revenue = 0;

       for(int orderId : orders)
       {
           List<OrderItemPojo> orderItemPojoList = orderItemDao.selectAll(orderId);
           for(OrderItemPojo pojo : orderItemPojoList)
           {
               ProductPojo productPojo = productDao.select(pojo.getProduct());
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
}
