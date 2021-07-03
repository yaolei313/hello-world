package com.yao.app.protocol.thrift.service.impl;

import com.yao.app.protocol.thrift.service.TUser;
import com.yao.app.protocol.thrift.service.TUserService.Iface;
import java.util.List;
import org.apache.thrift.TException;

public class UserServiceImpl implements Iface {

    //@Autowired
    //UserService userService;
    
    //@Autowired
    //TypeConverterRegistry registry;
    
    @Override
    public TUser queryUserById(String id) throws TException {
//        UserBean user = userService.queryUserById(userId);
//        return (YUser) registry.getRegisterConverter(UserBean.class).convert(user);
        System.out.println("enter call...");
        TUser user = new TUser();
        user.id="y00196907";
        user.name="姚磊";
        user.email="yaolei313@gmail.com";
        //user.gravatarMail="yaolei313@gmail.com";
        return user;
    }

    @Override
    public List<TUser> queryUserByIds(List<String> ids) throws TException {
        return null;
    }

    @Override
    public String addUser(TUser user) throws TException {
        // TODO Auto-generated method stub
        return null;
    }

}
