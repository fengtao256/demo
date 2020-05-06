package com.example.quartsdemo.quartz;



import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Date date = new Date() ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println("当前时间："+sdf.format(date));
        JobKey key = jobExecutionContext.getJobDetail().getKey() ;
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap() ;
        System.err.println("Information of "+key+"\n  myFirstName:"+dataMap.getString("myFirstName")+
                           "\n  mylASTName:"+dataMap.getString("myLastName"));

    }
}
