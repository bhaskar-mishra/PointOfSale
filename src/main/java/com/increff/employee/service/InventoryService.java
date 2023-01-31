package com.increff.employee.service;

import com.google.protobuf.Api;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.helper.InventoryReportHelper;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.InventoryReportForm;
import com.increff.employee.model.InventoryUpdateForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;


    @Transactional
    public void add(InventoryPojo inventoryPojo) throws ApiException{
             inventoryDao.insert(inventoryPojo);
    }



    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryPojo> get() throws ApiException{
        return inventoryDao.selectAll();
    }



    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryReportHelper> getInventoryReport(InventoryReportForm inventoryReportForm) throws ApiException{
        String brand = inventoryReportForm.getBrand();
        String category = inventoryReportForm.getCategory();
        String product = inventoryReportForm.getProduct();

        if(brand==null) brand = "";
        if(category==null) category = "";
        if(product==null) product = "";

        List<InventoryReportHelper> inventoryReportHelperList = new ArrayList<>();
        if(!brand.equals("") && !category.equals("") && !product.equals("")){
            ProductPojo productPojo = productDao.selectByBrandCategoryProduct(brand,category
            ,product);
            InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
            Integer quantity;
            if(inventoryPojo==null){
                quantity = 0;
            }else {
                quantity = inventoryPojo.getQuantity();
            }

            InventoryReportHelper inventoryReportHelper = new InventoryReportHelper();
            inventoryReportHelper.setInventoryReportForm(inventoryReportForm);
            inventoryReportHelper.setQuantity(quantity);
            inventoryReportHelperList.add(inventoryReportHelper);

        }else if(!brand.equals("") && !category.equals("")){
            List<ProductPojo> productPojoList = productDao.selectByBrandCategory(brand,category);
            for(ProductPojo productPojo : productPojoList){
                InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
                Integer quantity;
                if(inventoryPojo==null){
                    quantity = 0;
                }else {
                    quantity = inventoryPojo.getQuantity();
                }

                InventoryReportHelper inventoryReportHelper = new InventoryReportHelper();
                inventoryReportHelper.setInventoryReportForm(inventoryReportForm);
                inventoryReportHelper.setQuantity(quantity);
                inventoryReportHelperList.add(inventoryReportHelper);
            }

        }else if(!brand.equals("") && !product.equals("")){
                List<ProductPojo> productPojoList = productDao.selectByBrandProduct(brand,product);
                for(ProductPojo productPojo : productPojoList){
                    InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
                    Integer quantity;
                    if(inventoryPojo==null){
                        quantity = 0;
                    }else {
                        quantity = inventoryPojo.getQuantity();
                    }

                    InventoryReportHelper inventoryReportHelper = new InventoryReportHelper();
                    inventoryReportHelper.setInventoryReportForm(inventoryReportForm);
                    inventoryReportHelper.setQuantity(quantity);
                    inventoryReportHelperList.add(inventoryReportHelper);
                }
        }else if(!category.equals("") && !product.equals("")){
               List<ProductPojo> productPojoList = productDao.selectByCategoryProduct(category,product);
               for(ProductPojo productPojo : productPojoList){
                   InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
                   Integer quantity;
                   if(inventoryPojo==null){
                       quantity = 0;
                   }else {
                       quantity = inventoryPojo.getQuantity();
                   }

                   InventoryReportHelper inventoryReportHelper = new InventoryReportHelper();
                   inventoryReportHelper.setInventoryReportForm(inventoryReportForm);
                   inventoryReportHelper.setQuantity(quantity);
                   inventoryReportHelperList.add(inventoryReportHelper);
               }
        }else {
            List<ProductPojo> productPojoList = productDao.selectByProduct(product);
            for (ProductPojo productPojo : productPojoList){
                InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
                Integer quantity;
                if(inventoryPojo==null){
                    quantity = 0;
                }else {
                    quantity = inventoryPojo.getQuantity();
                }

                InventoryReportHelper inventoryReportHelper = new InventoryReportHelper();
                inventoryReportHelper.setInventoryReportForm(inventoryReportForm);
                inventoryReportHelper.setQuantity(quantity);
                inventoryReportHelperList.add(inventoryReportHelper);
            }
        }
        return inventoryReportHelperList;
    }



//    @Transactional
//    public List<InventoryReportData> getInventoryDetailsOfAllProducts() throws ApiException{
//        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
//        List<ProductPojo> productPojoList = productDao.selectAll();
//        for(ProductPojo productPojo : productPojoList){
//            InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(productPojo.getBarcode());
//            Integer quantity;
//            if(inventoryPojo==null){
//                quantity = 0;
//            }else {
//                quantity = inventoryPojo.getQuantity();
//            }
//            inventoryReportDataList.add(InventoryReportDto.productToInventoryReportData(productPojo,quantity));
//        }
//        return inventoryReportDataList;
//    }



    @Transactional
    public void updateInventoryForAGivenBarcode(String barcode, InventoryUpdateForm inventoryUpdateForm) throws ApiException{
        InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(barcode);
        inventoryPojo.setQuantity(inventoryUpdateForm.getQuantity());
    }

//    @Transactional
//    public InventoryPojo getCheck(int id) throws ApiException{
//        InventoryPojo p = dao.select(id);
//        if(p==null)
//        {
//            throw new ApiException("This product doesn't exist in the inventory");
//        }
//
//        return p;
//    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void update(int id,InventoryPojo p) throws ApiException{
//        if(p.getQuantity()<0)
//        {
//            throw new ApiException("There are not these many samples of this product available");
//        }
//        InventoryPojo d = getCheck(p.getId());
//        d.setId(p.getId());
//        d.setQuantity(p.getQuantity());
//        dao.update(d);
//    }
}
