package com.yao.app.protocol.thrift.service.impl;

import org.apache.thrift.TException;

import com.yao.app.protocol.thrift.service.HelloWorldService.Iface;

public class HelloWorldImpl implements Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to my website.";
    }

}
