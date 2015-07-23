package com.yao.app.nebula.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.yao.app.nebula.basic.AbstractTest;
import com.yao.app.nebula.domain.UserBean;
import com.yao.app.nebula.service.UserService;

public class UserServiceTest extends AbstractTest {

    @Resource
    private UserService service;
    
    @Test
    public void getTest(){
        UserBean user = service.queryUserById("y00196907");
        
        System.out.println(user.getName());
    }
}
