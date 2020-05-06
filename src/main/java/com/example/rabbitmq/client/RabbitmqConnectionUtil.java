package com.example.rabbitmq.client;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.FileReader;
import java.util.Properties;

public class RabbitmqConnectionUtil {
//    //编程消息的交换机名称
//    public static final String SCOREMES_EXCHANGE_NAME = "ex.program";
//    //编程消息的路由名称
//    public static final String ROUTE_KEY = "route.program.score";
    private static Connection conn;
    private static class RabbitHandler{
        private static final RabbitmqConnectionUtil rabbitInstance = new RabbitmqConnectionUtil();
    }
    private RabbitmqConnectionUtil(){
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //获取配置文件中的连接信息

        String path = new Object() {
            public String getPath() {
                return this.getClass().getResource("").getPath();
            }
        }.getPath();
        path = path.substring(1, path.indexOf(".war") + 5);
        try {
            //拼接配置文件路径
            FileReader reader = new FileReader(path + "properties/rabbitmq.properties");
            Properties pp = new Properties();
            pp.load(reader);

            //设置服务地址
            factory.setHost(pp.getProperty("severAddressIP"));
            //端口
            factory.setPort(Integer.valueOf(pp.getProperty("severAddressPort")));
            //设置账号信息，用户名、密码、vhost
            factory.setVirtualHost(pp.getProperty("virtualHost"));
            factory.setUsername(pp.getProperty("username"));
            factory.setPassword(pp.getProperty("password"));
            //通过工厂获取连接
            conn = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static RabbitmqConnectionUtil getInstance(){
        return RabbitHandler.rabbitInstance;
    }
    public  Connection getConnection() {

        return conn;
    }
}
