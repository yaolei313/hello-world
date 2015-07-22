package com.yao.app.springmvc.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.yao.app.springmvc.basic.AbstractTest;
import com.yao.app.springmvc.domain.UserBean;

public class UserServiceTest extends AbstractTest {

    @Resource
    private UserService service;
    
    @Test
    public void getTest(){
        UserBean user = service.queryUserById("y00196907");
        
        System.out.println(user.getName());
    }
}
