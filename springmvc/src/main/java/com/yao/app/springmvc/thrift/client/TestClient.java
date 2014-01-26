package com.yao.app.springmvc.thrift.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
import org.apache.thrift.transport.TTransportException;

import com.yao.app.springmvc.thrift.service.HelloWorldService;
import com.yao.app.springmvc.thrift.service.HelloWorldService.AsyncClient.sayHello_call;

public class TestClient {
    public static final String SERVER_HOST = "127.0.0.1";

    public static final int SERVER_PORT = 8091;

    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {
        testService2();
        testService3();
    }
    
    public static void testService(){
        System.out.println("TCompactProtocol客户端");
        
        TTransport transport = null;
        try {
            // transport = new TSocket(SERVER_HOST, SERVER_PORT, TIMEOUT);
            transport = new TSocket(SERVER_HOST, SERVER_PORT);
            //transport = new TFramedTransport(new TSocket(SERVER_HOST, SERVER_PORT));
            
            TCompactProtocol tprotocol = new TCompactProtocol(transport);
            TMultiplexedProtocol protocol = new TMultiplexedProtocol(tprotocol, "HELLO_SERVICE");
            
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            
            transport.open();
            
            String result = client.sayHello("李白路过");
            System.out.println(result);
            
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if(transport != null){
                transport.close();
            }
        }
    }
    
    public static void testService2(){
        System.out.println("TFramedTransport TCompactProtocol客户端");
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket(SERVER_HOST, SERVER_PORT));
            
            TCompactProtocol tprotocol = new TCompactProtocol(transport);
            TMultiplexedProtocol protocol = new TMultiplexedProtocol(tprotocol, "HELLO_SERVICE");
            
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            
            transport.open();
            
            String result = client.sayHello("李白路过");
            System.out.println(result);
            
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if(transport != null){
                transport.close();
            }
        }
    }
    
    public static void testService3(){
        // 测试异步客户端
        System.out.println("TCompactProtocol异步客户端，不需要指定frametransport");
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_HOST,
                    SERVER_PORT, TIMEOUT);
            
            TProtocolFactory protocolFactory = new TMultiplexedProtocolFactory(new TCompactProtocol.Factory(),"HELLO_SERVICE");
            
            HelloWorldService.AsyncClient client = new HelloWorldService.AsyncClient(protocolFactory, clientManager, transport);
            
            System.out.println("Client start ...");
            
            final CountDownLatch latch = new CountDownLatch(1);
            AsyncMethodCallback<sayHello_call> resultHandler = new AsyncMethodCallback<sayHello_call>(){

                @Override
                public void onComplete(sayHello_call response) {
                    System.out.println("onComplete");
                    try {
                        System.out.println("AsynCall result =:"
                                + response.getResult());
                    } catch (TException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                    
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
