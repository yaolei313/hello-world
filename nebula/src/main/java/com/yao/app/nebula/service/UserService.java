package com.yao.app.nebula.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yao.app.nebula.domain.UserBean;

public interface UserService {
    UserBean queryUserByUsername(@Param("test") String username);
    
    List<UserBean> queryUsers();
    
    int addUser(UserBean user);
}
