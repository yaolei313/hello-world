package com.yao.app.protocol.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.yao.app.protocol.thrift.service.BusinessBasicException;
import com.yao.app.protocol.thrift.service.HelloWorldService;

public class HelloClientDemo {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {
        sendServiceReq2();
    }
    
    public static void sendServiceReq1(){
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(
                    protocol);
            transport.open();
            String result = client.sayHelloWithException("libai");
            System.out.println("Thrify client result =: " + result);
        } catch (BusinessBasicException e1){
            e1.printStackTrace();
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
    
    public static void sendServiceReq2(){
        TTransport transport = null;
        try {
            // transport不一致会出现connect reset
            transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            // transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            // TProtocol protocol = new TCompactProtocol(transport);
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(
                    protocol);
            transport.open();
            String result = client.sayHello("libai");
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

}
