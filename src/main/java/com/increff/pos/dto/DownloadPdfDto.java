package com.increff.pos.dto;

import com.increff.pos.client.InvoiceClient;
import com.increff.pos.model.InvoiceDetails;
import com.increff.pos.model.InvoiceItem;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadPdfDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceClient invoiceClient;

    public String generateInvoice(String orderCode) throws ApiException{
        System.out.println("inside generateInvoice in Dto");
        if(orderCode==null){
            throw new ApiException("orderCode unknown");
        }

        OrderPojo orderPojo = orderService.getOrderByOrderCode(orderCode);
        if(!orderPojo.getStatus().equals("PLACED")){
            throw new ApiException("order unplaced! invoice can't be generated");
        }

        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllItemsById(orderPojo.getOrderId());
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            InvoiceItem invoiceItem = new InvoiceItem();
            ProductPojo productPojo = productService.selectByProductId(orderItemPojo.getProductId());
            invoiceItem.setName(productPojo.getProduct());
            invoiceItem.setPrice(orderItemPojo.getSellingPrice());
            invoiceItem.setQuantity(orderItemPojo.getQuantity());
            invoiceItemList.add(invoiceItem);
        }

        LocalDateTime placedDateTime = orderPojo.getPlacedTime().toLocalDateTime();
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setItems(invoiceItemList);
        invoiceDetails.setTime(placedDateTime);
        invoiceDetails.setOrderId(orderPojo.getOrderId());

        return invoiceClient.generateInvoice(invoiceDetails);
    }
}
