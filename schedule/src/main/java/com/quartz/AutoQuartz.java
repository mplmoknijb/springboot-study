package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class AutoQuartz implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("AutoQuartz.execute --------------" + new Date());
    }
}
