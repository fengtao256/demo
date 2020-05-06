package com.example.rabbitmq.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvMessage {
    private final static String QUEUE_NAME = "queue-name";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1 获取工厂连接
        Connection conn = RabbitmqConnectionUtil.getInstance().getConnection() ;
        //4 获取channel
        Channel channel = conn.createChannel();
        //5 为通道绑定队列
        Boolean durable = true ;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        channel.basicQos(1);
        System.out.println("[*] Waiting for messages. To exit press CTRL+C");

        //6 定义回调函数
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            //此处在处理过程中可捕捉异常，捕捉到异常则往回发送ack，消息转发
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        //7 消费消息
        Boolean autoAck = false ;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,consumerTag->{ });

    }
}
