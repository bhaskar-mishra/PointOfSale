package com.increff.pos.dao;

import com.increff.pos.pojo.DailySalesReportPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class SchedulerDao extends AbstractDao{

    private static String SELECT_BY_DATE = "select p from DailySalesReportPojo p where date=:date";
    private static String SELECT_ALL = "select p from DailySalesReportPojo p";


    @Transactional
    public void insert(DailySalesReportPojo dailySalesReportPojo) {em().persist(dailySalesReportPojo);}

    public DailySalesReportPojo selectByDate(String date){
        TypedQuery<DailySalesReportPojo> schedulerPojoTypedQuery = getQuery(SELECT_BY_DATE, DailySalesReportPojo.class);
        schedulerPojoTypedQuery.setParameter("date",date);
        return schedulerPojoTypedQuery.getResultStream().findFirst().orElse(null);
    }

    public List<DailySalesReportPojo> selectAll(){
        TypedQuery<DailySalesReportPojo> schedulerPojoTypedQuery = getQuery(SELECT_ALL, DailySalesReportPojo.class);
        return schedulerPojoTypedQuery.getResultList();
    }


}
