package com.increff.pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
