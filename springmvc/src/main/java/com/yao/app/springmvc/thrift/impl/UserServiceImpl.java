package com.yao.app.springmvc.thrift.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yao.app.springmvc.service.UserService;
import com.yao.app.springmvc.thrift.UserService.Iface;
import com.yao.app.springmvc.thrift.YUser;

@Component
public class UserServiceImpl implements Iface {

    @Autowired
    UserService userService;
    
    @Override
    public YUser queryUserInfo(String userId) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

}
