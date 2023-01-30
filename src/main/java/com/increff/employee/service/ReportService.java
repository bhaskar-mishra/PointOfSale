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
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Service
public class ReportService {

  @Autowired
  private BrandService brandService;
  @Autowired
  private OrderItemService orderItemService;
  @Autowired
  private ProductService productService;




  //Get sales report based on the input given by the user

}
