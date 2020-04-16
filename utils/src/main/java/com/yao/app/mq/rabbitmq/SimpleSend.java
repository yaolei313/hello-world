package com.yao.app.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleSend {

    public static void main(String[] args) {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.86.142.123");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection conn = null; // 可以多线程共享
        Channel channel = null; // 不能多线程共享
        try {
            conn = factory.newConnection();

            channel = conn.createChannel();

            // final String QUEUE_NAME = "hello";
            // channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            final String QUEUE_NAME = "task_queue";
            boolean durable = true;// 确保queue不会丢失
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            for (int i = 1; i <= 5; i++) {
                String message = i + "th message.";

                // MessageProperties.PERSISTENT_TEXT_PLAIN 确保message不会丢失
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            }
            //String message = "Hello world";
            //channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                }
            }
        }

    }

}
