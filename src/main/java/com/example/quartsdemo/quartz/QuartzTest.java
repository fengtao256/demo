package com.example.quartsdemo.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) {
        try {
            //测试全局变量map
            JobDataMap modelMap = new JobDataMap();
            modelMap.put("myFirstName", "Feng");
            modelMap.put("myLastName", "Tao");
            //Scheduler - 与调度程序交互的主要API。
            //Job - 由希望由调度程序执行的组件实现的接口。
            //JobDetail - 用于定义作业的实例
            //Trigger（即触发器） - 定义执行给定作业的计划的组件。
            //JobBuilder - 用于定义/构建JobDetail实例，用于定义作业的实例。
            //TriggerBuilder - 用于定义/构建触发器实例。
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            System.out.println("====准备调用");
            scheduler.start();
            System.out.println("====开始调用");
            //采用静态调用newJob的方式
            JobDetail jobDetail = newJob(HelloJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData(modelMap)
                    .build();
            JobDetail jobDetail2 = newJob(HelloJob.class)
                    .withIdentity("myJob2", "group2")
                    .usingJobData(modelMap)
                    .build();
            //任务调度操作
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *"))
                    .build();
            Trigger trigger2 = newTrigger()
                    .withIdentity("myTrigger2", "group2")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *"))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.scheduleJob(jobDetail2, trigger2);
            //scheduler.shutdown();
            System.out.println("====结束调用");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

