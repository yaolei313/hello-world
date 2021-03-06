package com.yao.app.nebula.service.impl;

import java.util.List;

import com.yao.app.nebula.service.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yao.app.nebula.domain.UserBean;
import com.yao.app.nebula.persistence.UserMapper;
import com.yao.app.nebula.service.UserService;
import com.yao.app.nebula.util.TraceAvailable;

@Service("db.user.service")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Test testA;

    @Override
    @TraceAvailable
    public UserBean queryUserByUsername(String userName) {
        testA.test();
    	System.out.println("hello a");
    	// 不会被aop拦截，代理对象决定的，因为this就是target，而不是delegateObject
    	this.test();
        return userMapper.findUser(userName);
    }
    
    @TraceAvailable
    public void test(){
    	System.out.println("hello b");
    }

    @Override
    public List<UserBean> queryUsers() {
        return userMapper.queryUsers();
    }

    @Override
    public int addUser(UserBean user) {
        return userMapper.insertUser(user);
    }

}
