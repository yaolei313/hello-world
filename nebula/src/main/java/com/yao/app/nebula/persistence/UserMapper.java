package com.yao.app.nebula.persistence;

import java.util.List;

import com.yao.app.nebula.domain.UserBean;

public interface UserMapper {
    UserBean findUser(String id);
    
    List<UserBean> queryUsers();
}
