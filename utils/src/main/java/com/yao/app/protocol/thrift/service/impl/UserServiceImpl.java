package com.yao.app.protocol.thrift.service.impl;

import org.apache.thrift.TException;

import com.yao.app.protocol.thrift.service.UserService.Iface;
import com.yao.app.protocol.thrift.service.YUser;

public class UserServiceImpl implements Iface {

    //@Autowired
    //UserService userService;
    
    //@Autowired
    //TypeConverterRegistry registry;
    
    @Override
    public YUser queryUserInfo(String userId) throws TException {
//        UserBean user = userService.queryUserById(userId);
//        return (YUser) registry.getRegisterConverter(UserBean.class).convert(user);
        return null;
    }

}
