package com.example.dateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtils {

    //String passHour = "1" ; 向前多少小时,1表示查询当前整小时和前一个小时的数据
    //调用示例 ： TimeUtils.packageSqlStr(passHour) ;
    /**
     * 将map参数拼装成D_DATETIME查询语句
     * @param passHour
     * @return
     */
    public static String packageSqlStr(String passHour){
        Map<String,String> params = getDate(passHour) ;
        return "{ D_DATETIME >= '" + params.get("startTime") + "' AND D_DATETIME <= '" + params.get("endTime") +"' }";
    }
    /**
     * 获取时间
     * @param passHour 向前多少小时,0表示查询当前整小时数据
     * @return
     */
    public static Map<String,String> getDate(String passHour){
        Map<String,String> ret = new HashMap<String,String>();
        if(passHour == null || "".equals(passHour)){
            passHour = "0";
        }
        int dateBefore = Integer.parseInt(passHour) + 8;
        int wordTime =  8;
        String startTime = getCompareDate("-"+dateBefore+"HH", "yyyy-MM-dd HH:00:00");//开始时间
        String endTime = getCompareDate("-"+wordTime+"HH", "yyyy-MM-dd HH:00:00");//结束时间
        String nowTime = getCompareDate("-"+wordTime+"HH", "yyyy-MM-dd HH:mm:ss");//当前世界时
        ret.put("startTime", startTime);//开始时间，1分钟
        ret.put("endTime", endTime);//结束时间，当前小时的0分
        ret.put("nowTime", nowTime);//当前时间
        return ret;
    }
    /**
     * 获取当前时间前推或后推某一时间
     * @param dates
     * @param dateFormat
     * @return
     */
    public static String getCompareDate(String dates,String dateFormat){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(dates.contains("-")){//日期在之前
            boolean yyyyMMdd = dates.substring(0, dates.indexOf("-")).equals("yyyyMMdd");
            if(dates.endsWith("yyyy")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("yyyy")));
                c.add(Calendar.YEAR, - amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("MM")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("MM")));
                c.add(Calendar.MONTH, - amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("dd")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("dd")));
                c.add(Calendar.DATE, - amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("HH")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("HH")));
                c.add(Calendar.HOUR_OF_DAY, - amount);
            }else if(dates.endsWith("mm")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("mm")));
                c.add(Calendar.MINUTE, - amount);
            }else if(dates.endsWith("ss")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("-")+1, dates.lastIndexOf("ss")));
                c.add(Calendar.SECOND, - amount);
            }
        }else if(dates.contains("+")){//日期在之后
            boolean yyyyMMdd = dates.substring(0, dates.indexOf("+")).equals("yyyyMMdd");
            if(dates.endsWith("yyyy")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("yyyy")));
                c.add(Calendar.YEAR, - amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("MM")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("MM")));
                c.add(Calendar.MONTH, + amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("dd")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("dd")));
                c.add(Calendar.DATE, + amount);
                if(yyyyMMdd){
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                }
            }else if(dates.endsWith("HH")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("HH")));
                c.add(Calendar.HOUR_OF_DAY, + amount);
            }else if(dates.endsWith("mm")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("mm")));
                c.add(Calendar.MINUTE, + amount);
            }else if(dates.endsWith("ss")){
                int amount = Integer.parseInt(dates.substring(dates.indexOf("+")+1, dates.lastIndexOf("ss")));
                c.add(Calendar.SECOND, + amount);
            }
        }else{//当前时间

        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String ret = df.format(c.getTime());
        return ret;
    }
}
