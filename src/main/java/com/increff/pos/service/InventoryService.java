package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    public void add(InventoryPojo inventoryPojo) throws ApiException{
             InventoryPojo inventoryPojo1 = inventoryDao.selectById(inventoryPojo.getProductId());
             if(inventoryPojo1!=null){
                 inventoryPojo1.setQuantity(inventoryPojo1.getQuantity()+ inventoryPojo.getQuantity());
             }else {
                 inventoryDao.insert(inventoryPojo);
             }
    }

    public InventoryPojo getInventoryByProductId(Integer productId) throws ApiException{
        InventoryPojo inventoryPojo = inventoryDao.selectById(productId);
        if(inventoryPojo==null){
            throw new ApiException("this product is not in inventory");
        }

        return inventoryPojo;
    }

    public void updateInventory(Integer productId,Integer quantity) throws ApiException{
        InventoryPojo inventoryPojo = inventoryDao.selectById(productId);
        if(inventoryPojo==null){
            throw new ApiException("This product is not in inventory");
        }
        inventoryPojo.setQuantity(quantity);
    }


    public List<InventoryPojo> getAllInventory() throws ApiException{
        return inventoryDao.selectAll();
    }


}
