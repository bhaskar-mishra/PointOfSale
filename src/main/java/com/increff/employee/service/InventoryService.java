package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.helper.InventoryReportHelper;
import com.increff.employee.model.*;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
             InventoryPojo inventoryPojo1 = inventoryDao.selectByBarcode(inventoryPojo.getBarcode());
             if(inventoryPojo1!=null){
                 inventoryPojo1.setQuantity(inventoryPojo1.getQuantity()+ inventoryPojo.getQuantity());
             }else {
                 inventoryDao.insert(inventoryPojo);
             }
    }



    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryPojo> getAllInventory() throws ApiException{
        return inventoryDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getInventoryByBarcode(String barcode) throws ApiException{
        InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(barcode);
        if(inventoryPojo==null){
            throw new ApiException("this product is not in inventory");
        }

        return inventoryPojo;
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


    @Transactional
    public void updateInventory(InventoryForm inventoryForm) throws ApiException{
        InventoryPojo inventoryPojo = inventoryDao.selectByBarcode(inventoryForm.getBarcode());
        if(inventoryPojo==null){
            throw new ApiException("This product is not in inventory");
        }
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
    }


}
