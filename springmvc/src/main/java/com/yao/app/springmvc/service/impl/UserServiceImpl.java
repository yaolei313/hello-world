package com.yao.app.springmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.persistence.UserMapper;
import com.yao.app.springmvc.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserBean fetchUserInfo(String id) {
		return userMapper.findUser(id);
	}

}
