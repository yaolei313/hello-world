package com.yao.app.protocol.thrift.service.impl;

import org.apache.thrift.TException;

import com.yao.app.protocol.thrift.service.BusinessBasicException;
import com.yao.app.protocol.thrift.service.HelloWorldService.Iface;

public class HelloWorldImpl implements Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to my website.";
    }

    @Override
    public String sayHelloWithException(String username) throws BusinessBasicException, TException {
        if("libai".equals(username)){
            throw new BusinessBasicException("出错了");
        } 
        
        if("lisi".equals(username)){
            throw new TException("出错了2");
        }
        
        if("lisan".equals(username)){
            throw new IllegalArgumentException("出错了3");
        }
        
        return "Hi," + username + " welcome to my website.";
    }

}
