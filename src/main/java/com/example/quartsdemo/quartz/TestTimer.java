package com.example.quartsdemo.quartz;

import com.mysql.jdbc.StringUtils;

import java.util.*;

public class TestTimer {
    static int count = 0;
    static {
        showTimer();
    }
    public static void showTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ++count;
                System.out.println("时间=" + new Date() + " 执行了" + count + "次"); // 1次
            }
        };

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的23:09:00执行，
        calendar.set(year, month, day, 00, 00, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println(date);

        int period = 60*1000;
        //每天的date时刻执行task，每隔2秒重复执行
//        timer.schedule(task,period);
        timer.schedule(task, date, period);
//        //每天的date时刻执行task, 仅执行一次
//        timer.schedule(task, date);
    }

    public static void main(String[] args) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>() ;
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("aaaa","aa") ;
        map.put("bbbb","bbbb") ;

        list.add(map) ;
        list.add(map) ;
        for (Map<String,Object> temp :list) {
            System.out.println("before:"+list.get(0));
            temp.put("aaaa","aaaa");
            System.out.println("ofter:"+list.get(0));
        }
//
//        System.out.println((String)map.get("aaaa"));
//        if("".equals(map.get("aaaa"))){
//            System.out.println("应该替换为空值（null）");
//        }
//        if("".equals(null)){
//
//        }
    }
}
