package com.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//监听器类
@WebListener
public class ApplicationContextListener implements ServletContextListener {

    //任务调度器
    private Scheduler scheduler;

    /**
     * 重写ServletContextListener监听生命周期 初始方法
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            //获取StdSchedulerFactory实例，scheduler 工厂创建
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            System.out.println("scheduler start");
            System.out.println(scheduler.getSchedulerName());

            //注册服务
            JobDetail jobDetail = JobBuilder.newJob(AutoQuartz.class).withIdentity("AutoQuartzJob", "AutoQuartzJobGroup").build();

            //设置触发时间（每5秒1次）
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
            //触发器，一个trigger对应一个job
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "triggerGroup").startNow().withSchedule(simpleScheduleBuilder).build();
            //Scheduler触发
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写ServletContextListener监听生命周期 销毁方法
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
