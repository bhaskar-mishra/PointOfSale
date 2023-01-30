package com.increff.employee.dao;

import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class SchedulerDao extends AbstractDao{

    private static String SELECT_BY_DATE = "select p from SchedulerPojo p where date=:date";
    private static String SELECT_ALL = "select p from SchedulerPojo p";


    @Transactional
    public void insert(SchedulerPojo schedulerPojo) {em().persist(schedulerPojo);}

    public SchedulerPojo selectByDate(String date){
        TypedQuery<SchedulerPojo> schedulerPojoTypedQuery = getQuery(SELECT_BY_DATE, SchedulerPojo.class);
        schedulerPojoTypedQuery.setParameter("date",date);
        return schedulerPojoTypedQuery.getResultStream().findFirst().orElse(null);
    }

    public List<SchedulerPojo> selectAll(){
        TypedQuery<SchedulerPojo> schedulerPojoTypedQuery = getQuery(SELECT_ALL, SchedulerPojo.class);
        return schedulerPojoTypedQuery.getResultList();
    }


}
