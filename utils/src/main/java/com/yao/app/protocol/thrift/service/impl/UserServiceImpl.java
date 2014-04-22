package com.yao.app.protocol.thrift.service.impl;

import java.sql.Timestamp;

import org.apache.thrift.TException;

import com.yao.app.protocol.thrift.convertor.TimestampTypeConverter;
import com.yao.app.protocol.thrift.service.UserService.Iface;
import com.yao.app.protocol.thrift.service.YTimestamp;
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
        YUser user = new YUser();
        user.id="y00196907";
        user.name="姚磊";
        user.email="yaolei313@gmail.com";
        user.gravatarMail="yaolei313@gmail.com";
        user.registerTime = (YTimestamp)new TimestampTypeConverter().convert(new Timestamp(System.currentTimeMillis()));
        return new YUser();
    }

}
