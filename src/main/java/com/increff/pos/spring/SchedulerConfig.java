package com.increff.pos.spring;

import com.increff.pos.job.ReportScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    public ReportScheduler schedule() {
        return new ReportScheduler();
    }
}
