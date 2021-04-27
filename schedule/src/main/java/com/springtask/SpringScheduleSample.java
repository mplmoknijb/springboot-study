package com.springtask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * spring task简单使用
 */
@Configuration
@EnableScheduling
public class SpringScheduleSample {

    @Scheduled(cron = "0/5 * * * * *")
    public void task1() {
        System.out.println(getClass() + "-----task1" + new Date());
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void task2() {
        System.out.println(getClass() + "-------task2" + new Date());
    }
}
