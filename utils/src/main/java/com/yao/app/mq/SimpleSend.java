package com.yao.app.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleSend {

    public static void main(String[] args) {
        final String QUEUE_NAME = "hello";
        
        ConnectionFactory factory= new ConnectionFactory();
        factory.setHost("10.86.142.123");
        factory.setUsername("guest");
        factory.setPassword("guest");
        
        Connection conn = null; // 可以多线程共享
        Channel channel = null; // 不能多线程共享
        try {
            conn = factory.newConnection();
            
            channel = conn.createChannel();
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            
            String message = "Hello world";
            
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                }
            }
            
            if(conn != null){
                try {
                    conn.close();
                } catch (IOException e) {
                }
            }
        }
        
    }

}
