package com.yao.app.protocol.thrift.client;

import com.yao.app.protocol.thrift.service.THelloWorldService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloClientDemo {

    public static final String SERVER_IP = "localhost";

    public static final int SERVER_PORT = 8090;

    public static final int ASYNC_SERVER_PORT = 8091;

    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {
        callSayHello();
        //callSayHelloAsync();
    }

    public static void callSayHello() {
        TTransport transport = null;
        try {
            // transport不一致会出现connect reset
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            THelloWorldService.Client client = new THelloWorldService.Client(protocol);
            transport.open();
            String result = client.sayHello("libai");
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e2) {
            e2.printStackTrace();
        } catch (TException e3) {
            e3.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    public static void callSayHelloAsync() {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, ASYNC_SERVER_PORT, TIMEOUT);

            //TProtocolFactory tprotocol = new TCompactProtocol.Factory();
            TProtocolFactory tprotocol = new TBinaryProtocol.Factory();
            THelloWorldService.AsyncClient asyncClient = new THelloWorldService.AsyncClient(tprotocol, clientManager, transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsyncMethodCallback callBack = new AsynCallback(latch);
            System.out.println("call method sayHello start ...");
            asyncClient.sayHello("yaolei", callBack);
            System.out.println("call method sayHello .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("startClient end.");
    }

    public static class AsynCallback implements AsyncMethodCallback<String> {

        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(String response) {
            System.out.println("onComplete");
            // Thread.sleep(1000L * 1);
            System.out.println("AsynCall result =:"
                + response);
            latch.countDown();
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }
}
