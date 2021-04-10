package com.yao.app.protocol.thrift.server;

import com.yao.app.protocol.thrift.service.THelloWorldService;
import com.yao.app.protocol.thrift.service.TUserService;
import com.yao.app.protocol.thrift.service.impl.HelloWorldImpl;
import com.yao.app.protocol.thrift.service.impl.UserServiceImpl;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.transport.layered.TFramedTransport;

public class MyThriftServer {

    public static final int SERVER_PORT = 8091;

    public void provideService() throws TTransportException {
        // 阻塞IO,单个线程接受连接，多个消息处理线程 服务端
        System.out.println("启动TThreadPoolServer");

        TServerTransport transport = new TServerSocket(SERVER_PORT);

        TMultiplexedProcessor processor = new TMultiplexedProcessor();

        processor.registerProcessor("HELLO_SERVICE", new THelloWorldService.Processor<THelloWorldService.Iface>(
            new HelloWorldImpl()));
        processor
            .registerProcessor("USER_SERVICE", new TUserService.Processor<TUserService.Iface>(new UserServiceImpl()));

        TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

        TThreadPoolServer.Args args = new TThreadPoolServer.Args(transport);
        args.minWorkerThreads(5).maxWorkerThreads(10000);
        args.processor(processor);
        args.protocolFactory(protocolFactory);

        TServer server = new TThreadPoolServer(args);
        server.serve();

    }

    public void provideService2() throws TTransportException {
        // 非阻塞IO,多个网络IO线程，多个消息处理线程 服务端
        System.out.println("启动TThreadedSelectorServer");

        TNonblockingServerTransport transport = new TNonblockingServerSocket(SERVER_PORT);

        TMultiplexedProcessor processor = new TMultiplexedProcessor();

        processor.registerProcessor("HELLO_SERVICE", new THelloWorldService.Processor<THelloWorldService.Iface>(
            new HelloWorldImpl()));
        processor
            .registerProcessor("USER_SERVICE", new TUserService.Processor<TUserService.Iface>(new UserServiceImpl()));

        TTransportFactory transportFactory = new TFramedTransport.Factory();
        TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(transport);
        args.processor(processor);
        args.selectorThreads(2);
        args.workerThreads(5);
        args.transportFactory(transportFactory);
        args.protocolFactory(protocolFactory);

        TServer server = new TThreadedSelectorServer(args);
        server.serve();
    }

    public static void main(String[] args) throws Exception {
        MyThriftServer thriftService = new MyThriftServer();
        try {
            // thriftService.provideService();
            thriftService.provideService2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
