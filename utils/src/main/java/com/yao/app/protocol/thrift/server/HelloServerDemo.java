package com.yao.app.protocol.thrift.server;

import com.yao.app.protocol.thrift.service.THelloWorldService;
import com.yao.app.protocol.thrift.service.impl.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.layered.TFramedTransport;

public class HelloServerDemo {

    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) {
        // 注意优先使用TFramedTransport，由于buffer存在的关系，可以保证序列化过程中有错误时，不完整的数据包不会发送。
        startService2();
    }

    public static void startService1() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");

            TProcessor tprocessor = new THelloWorldService.Processor<THelloWorldService.Iface>(
                new HelloWorldImpl());

            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // tArgs.protocolFactory(new TCompactProtocol.Factory());
            // tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * TThreadPoolServer 服务模型 TCompactProtocol
     * 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
     */
    public static void startService2() {
        try {
            System.out.println("HelloWorld TThreadPoolServer start ....");

            TProcessor tprocessor = new THelloWorldService.Processor<THelloWorldService.Iface>(
                new HelloWorldImpl());

            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);

            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
            ttpsArgs.processor(tprocessor);
            ttpsArgs.protocolFactory(new TCompactProtocol.Factory());

            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * TNonblockingServer 服务模型
     * 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
     */
    public static void startService3() {
        try {
            System.out.println("HelloWorld TNonblockingServer start ....");

            TProcessor tprocessor = new THelloWorldService.Processor<THelloWorldService.Iface>(
                new HelloWorldImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(
                tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());

            // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
            TServer server = new TNonblockingServer(tnbArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * THsHaServer服务模型
     * 半同步半异步的服务端模型，需要指定为： TFramedTransport 数据传输的方式。
     */
    public static void startService4() {
        try {
            System.out.println("HelloWorld THsHaServer start ....");

            TProcessor tprocessor = new THelloWorldService.Processor<THelloWorldService.Iface>(
                new HelloWorldImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                SERVER_PORT);
            THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
            thhsArgs.processor(tprocessor);
            thhsArgs.transportFactory(new TFramedTransport.Factory());
            thhsArgs.protocolFactory(new TBinaryProtocol.Factory());

            //半同步半异步的服务模型
            TServer server = new THsHaServer(thhsArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * TThreadedSelectorServer服务模型
     * 一个AcceptThread用于处理监听socket上的新连接
     * 线程池selector threads用来处理网络I/O，
     * 线程池worker threads用来进行请求的处理
     */
    public static void startService5() {
        try {
            System.out.println("HelloWorld TThreadedSelectorServer start ....");

            TProcessor processor = new THelloWorldService.Processor<THelloWorldService.Iface>(
                new HelloWorldImpl());

            TNonblockingServerSocket transport = new TNonblockingServerSocket(
                SERVER_PORT);

            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(transport);
            args.processor(processor);
            args.selectorThreads(2);
            args.workerThreads(5);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TThreadedSelectorServer(args);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

}
