package com.yao.app.protocol.thrift.service.impl;

import com.yao.app.protocol.thrift.service.THelloWorldService.Iface;
import org.apache.thrift.TException;

public class HelloWorldImpl implements Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to my website.";
    }
}
