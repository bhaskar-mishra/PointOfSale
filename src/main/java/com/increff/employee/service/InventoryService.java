package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.InventoryData;
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

        System.out.println("Inside inventory add method in service");
        String barcode = inventoryPojo.getBarcode();
        InventoryPojo pojo = inventoryDao.select(barcode);
        if(pojo!=null){
            pojo.setQuantity(pojo.getQuantity()+inventoryPojo.getQuantity());
        }else{
            ProductPojo productPojo = productDao.select(barcode);
            if(productPojo==null){
                throw new ApiException("This product doesn't exist in the product table");
            }

            System.out.println("pojo set to be added");
            inventoryPojo.setProduct(productPojo.getProduct());
            inventoryDao.insert(inventoryPojo);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryData> get() throws ApiException{
        List<InventoryPojo> inventoryPojoList = inventoryDao.selectAll();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList){
            inventoryDataList.add(InventoryDto.pojoToData(inventoryPojo));
        }

        return inventoryDataList;
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
