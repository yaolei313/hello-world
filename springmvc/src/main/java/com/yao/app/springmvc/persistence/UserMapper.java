package com.yao.app.springmvc.persistence;

import java.util.List;

import com.yao.app.springmvc.domain.UserBean;

public interface UserMapper {
    UserBean findUser(String id);
    
    List<UserBean> queryUsers();
}
