package com.yao.app.springmvc.thrift;

import org.apache.thrift.TException;

import com.yao.app.springmvc.thrift.HelloWorldService.Iface;

public class HelloWorldImpl implements Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to my website.";
    }

}
