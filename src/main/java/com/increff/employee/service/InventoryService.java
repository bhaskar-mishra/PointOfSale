package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Transactional
    public void add(InventoryPojo p) throws ApiException{
        InventoryPojo existing = dao.select(p.getId());
        if(existing!=null){
            throw new ApiException("This product already exists");
        }

        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException{
        return getCheck(id);
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException{
        InventoryPojo p = dao.select(id);
        if(p==null)
        {
            throw new ApiException("This product doesn't exist in the inventory");
        }

        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,InventoryPojo p) throws ApiException{
        if(p.getQuantity()<0)
        {
            throw new ApiException("There are not these many samples of this product available");
        }
        InventoryPojo d = getCheck(p.getId());
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        dao.update(d);
    }
}
