package com.yao.app.springmvc.service;

import com.yao.app.springmvc.domain.UserBean;

public interface UserService {
    UserBean queryUserById(String id);
}
