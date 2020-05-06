package com.example.HelloWord;

import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HelloWord {
    public static void main1(String[] args) throws ParseException {

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("test","测试");
        System.out.println(map.get("test1"));
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf1=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = sf.parse("2019-12-10 13:00:00");
        System.out.println(sf1.format(date.getTime())+ File.pathSeparator);
        String baseUrl = "http://localhost:8088/" ;
        System.out.println(baseUrl.endsWith("/"));


    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://10.194.130.43:8480/createFullProductTime");// 创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setReadTimeout(60000);//设置接口响应时间为1分钟
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
        connection.connect();

        //传递JSON参数
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        out.append("{\n" +
                "    \"dataType\": \"TEM\",\n" +
                "    \"qDataType\": \"Q_TEM\",\n" +
                "    \"lonStr\": \"Lon\",\n" +
                "    \"latStr\": \"Lat\",\n" +
                "    \"dateTimeStr\": \"D_Datetime\",\n" +
                "    \"timeType\": \"oneHour\",\n" +
                "    \"areaName\": \"成都市\",\n" +
                "    \"colorCardName\": \"temperature\",\n" +
                "    \"resultType\": \"choroplethic\",\n" +
                "\n" +
                "    \"interfaceId\": \"getSurfEleInRegionByTime\",\n" +
                "    \"dataCode\": \"SURF_WEA_CHN_MUL_HOR\",\n" +
                "    \"adminCodes\": \"510100\",\n" +
                "    \"staTypes\": \"国家站,区域站\"\n" +
                "}");
        out.flush();
        out.close();
        InputStream resultString = connection.getInputStream() ;
        //获取产品生成时间
        if(connection.getResponseCode()==200){
            StringBuffer stringBuffer = new StringBuffer() ;
            int line;
            byte[] bytes = new byte[1024];
            System.out.println(resultString.toString());
            while((line = resultString.read()) != -1){
                System.out.print((char) line);
                stringBuffer.append(line) ;
            }
            System.out.println(stringBuffer.toString());
        }

    }

}
