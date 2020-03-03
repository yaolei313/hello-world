package com.yao.app.protocol.thrift.client;

import com.yao.app.protocol.thrift.client.pool.ThriftConfig;
import com.yao.app.protocol.thrift.client.pool.ThriftServiceClientBuilder;
import com.yao.app.protocol.thrift.service.THelloWorldService;
import com.yao.app.protocol.thrift.service.THelloWorldService.Client;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * TODO 增加下client pool
 */
public class MyThriftClient {

    public static final String SERVER_HOST = "127.0.0.1";

    public static final int SERVER_PORT = 8091;

    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {
        testService1();
        //testService2();
        //testService3();
    }

    public static void testService1() {
        System.out.println("TCompactProtocol客户端");

        TTransport transport = null;
        try {
            // transport = new TSocket(SERVER_HOST, SERVER_PORT, TIMEOUT);
            transport = new TSocket(SERVER_HOST, SERVER_PORT);
            //transport = new TFramedTransport(new TSocket(SERVER_HOST, SERVER_PORT));

            TCompactProtocol tprotocol = new TCompactProtocol(transport);
            TMultiplexedProtocol protocol = new TMultiplexedProtocol(tprotocol, "HELLO_SERVICE");
            THelloWorldService.Client client = new THelloWorldService.Client(protocol);

            transport.open();

            String result = client.sayHello("李白路过");
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void testService2() {
        System.out.println("TFramedTransport+TCompactProtocol客户端");
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket(SERVER_HOST, SERVER_PORT));

            TCompactProtocol tprotocol = new TCompactProtocol(transport);
            TMultiplexedProtocol protocol = new TMultiplexedProtocol(tprotocol, "HELLO_SERVICE");

            THelloWorldService.Client client = new THelloWorldService.Client(protocol);

            transport.open();

            String result = client.sayHello("李白路过");
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void testService3() {
        // 测试异步客户端
        System.out.println("TCompactProtocol异步客户端，不需要指定frametransport");
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();

            TNonblockingTransport transport = new TNonblockingSocket(SERVER_HOST,
                SERVER_PORT, TIMEOUT);

            TProtocolFactory protocolFactory = new TMultiplexedProtocolFactory(new TCompactProtocol.Factory(), "HELLO_SERVICE");

            THelloWorldService.AsyncClient client = new THelloWorldService.AsyncClient(protocolFactory, clientManager, transport);

            System.out.println("Client start ...");

            final CountDownLatch latch = new CountDownLatch(1);
            AsyncMethodCallback<String> resultHandler = new AsyncMethodCallback<String>() {

                @Override
                public void onComplete(String response) {
                    System.out.println("onComplete");
                    System.out.println("AsynCall result =:" + response);
                    latch.countDown();
                }

                @Override
                public void onError(Exception exception) {
                    System.out.println("onError :" + exception.getMessage());
                    latch.countDown();
                }
            };

            System.out.println("call method sayHello start ...");
            client.sayHello("summer", resultHandler);
            System.out.println("call method sayHello end ...");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("如果计数到达零，则该方法返回 true 值,结果为" + wait);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
