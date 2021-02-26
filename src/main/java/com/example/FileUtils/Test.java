package com.example.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("算法测试");
        String host = System.getProperty("http.proxyHost") ;
        Map<String,Object> tmpRS = new HashMap<>() ;
        tmpRS.put("defaultValue" , 1) ;
        String defaultValue = (tmpRS.get("defaultValue")==null||tmpRS.get("defaultValue")+""==""||"null".equals(tmpRS.get("defaultValue")+""))
                ? null :"'"+tmpRS.get("defaultValue")+"'";
        System.out.println(defaultValue);

//        String port = System.getProperty("http.proxyPort") ;
//        Writer writer = null;
//        try {
//            writer = new FileWriter("E:/Test.txt", true);
//            writer.write("host:"+host+"\n");
//            writer.write("port:"+port+"\n");
//
//            writer.flush();
//
//            if (writer != null) {
//                writer.close();
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
    public static String getCard(){
        Random rand=new Random();//生成随机数
        String cardNnumer="";
        for(int a=0;a<9;a++){
            cardNnumer+=rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }

}
