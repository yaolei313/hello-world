package com.yao.app.springmvc.thrift.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.service.UserService;
import com.yao.app.springmvc.thrift.convertor.TypeConverterRegistry;
import com.yao.app.springmvc.thrift.service.YUser;
import com.yao.app.springmvc.thrift.service.UserService.Iface;

@Component
public class UserServiceImpl implements Iface {

    @Autowired
    UserService userService;
    
    @Autowired
    TypeConverterRegistry registry;
    
    @Override
    public YUser queryUserInfo(String userId) throws TException {
        UserBean user = userService.queryUserById(userId);
        return (YUser) registry.getRegisterConverter(UserBean.class).convert(user);
    }

}
