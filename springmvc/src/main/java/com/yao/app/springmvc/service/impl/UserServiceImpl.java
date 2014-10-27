package com.yao.app.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.persistence.UserMapper;
import com.yao.app.springmvc.service.UserService;

@Service("db.user.service")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean queryUserById(String id) {
        return userMapper.findUser(id);
    }

    @Override
    public List<UserBean> queryUsers() {
        return userMapper.queryUsers();
    }

}
