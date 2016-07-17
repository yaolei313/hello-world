package com.yao.app.nebula.service;

import java.util.List;

import com.yao.app.nebula.domain.UserBean;

public interface UserService {
    UserBean queryUserByUsername(String username);
    
    List<UserBean> queryUsers();
}
