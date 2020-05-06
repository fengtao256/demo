package com.example.rabbitmq.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMessage {
    private final static String QUEUE_NAME="queue-name";

    public static void main(String[] args) {
        //1 获取工厂连接
        Connection conn = RabbitmqConnectionUtil.getInstance().getConnection() ;
        try{
            //3 创建channel.接下来，我们创建一个通道，用于完成任务的大部分API都位于这个通道中。
            Channel channel = conn.createChannel();
            //4 队列持久化
            //Boolean durable = true ;
            //队列申明，第一个参数是队列名称，第二个参数是队列是否持久化
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            //5 发送消息
            String message = "我是消息发送者";
            //MessageProperties.PERSISTENT_TEXT_PLAIN消息持久化
            //第一个参数是交换机的名称，默认是‘’，直连交换机，第二个是queue_name，也就是绑定的消息的队列，第三个参数是消息持久化参数。
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, ("消息--"+":" + message).getBytes());
            System.out.println("[send]"+message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}