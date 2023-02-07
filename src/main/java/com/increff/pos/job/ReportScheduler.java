package com.increff.pos.job;

import com.increff.pos.dto.ReportsDto;
import com.increff.pos.service.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class ReportScheduler {
    @Autowired
    ReportsDto reportDto;

    Logger logger = Logger.getLogger(ReportScheduler.class);

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void createDailyReport() throws ApiException {
        logger.info("Creating daily report");
        reportDto.createDailyReport();
    }
}