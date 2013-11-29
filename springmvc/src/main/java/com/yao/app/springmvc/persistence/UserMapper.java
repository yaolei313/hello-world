package com.yao.app.springmvc.persistence;

import com.yao.app.springmvc.domain.UserBean;

public interface UserMapper {
	UserBean findUser(String id);
}
