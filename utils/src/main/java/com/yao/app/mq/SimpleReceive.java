package com.yao.app.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class SimpleReceive {

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
            
            // 给服务器发了某些参数，未收到ack前，阻塞frame的读。
            channel.basicQos(1);
            
            //final String QUEUE_NAME = "hello";
            final String QUEUE_NAME = "task_queue";
            boolean durable = true;// 确保queue不会丢失
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            
            QueueingConsumer consumer = new QueueingConsumer(channel);
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
            
            while(true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Thread.sleep(1000);
                System.out.println("Received "+ message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }

        } catch (IOException | TimeoutException | ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
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