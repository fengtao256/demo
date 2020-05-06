package com.example.rabbitmq.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvMessage1 {
    private final static String QUEUE_NAME = "queue-name";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1 连 接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2 设置 连接属性
        factory.setHost("192.168.133.128");
        factory.setUsername("admin");
        factory.setPassword("root");
        //3 获取 连接
        Connection conn = factory.newConnection();
        //4 获取 channel
        Channel channel = conn.createChannel();
        //5 为通 道绑定队列
        Boolean durable = true ;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        channel.basicQos(1);
        System.out.println("[*] Waiting for messages1. To exit press CTRL+C");

        //6 定义回调函数
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        //7 消费消息
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,consumerTag->{ });

    }
}
