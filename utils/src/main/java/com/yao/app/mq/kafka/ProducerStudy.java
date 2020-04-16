package com.yao.app.mq.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerStudy {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerStudy.class);

    public static void main(String[] args) {
        testSimple();
    }

    public static void testSimple() {
        // producer线程安全，故可以多线程share

        // 有哪些配置项可以参考org.apache.kafka.clients.producer.ProducerConfig类
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.4.44.194:9093");
        // ack TODO
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        LOG.info("start");

        // 生产者包含一个buffer space和background io thread
        // 每个partition都有一个未发送记录的buffer，该buffer大小通过batch.size指定
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            // sends是异步的
            producer.send(new ProducerRecord<>("test", Integer.toString(i), "hello " + i));
        }

        LOG.info("end");

        producer.close();
    }
}
