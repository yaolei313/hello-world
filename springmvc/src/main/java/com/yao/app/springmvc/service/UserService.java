package com.yao.app.springmvc.service;

import java.util.List;

import com.yao.app.springmvc.domain.UserBean;

public interface UserService {
    UserBean queryUserById(String id);
    
    List<UserBean> queryUsers();
}
