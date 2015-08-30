package com.yao.app.nebula.service;

import static org.assertj.core.api.Assertions.*;

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
        UserBean user = service.queryUserByUsername("y00196907");
        
        
        assertThat(user.getNickname()).isEqualTo("李白");
        System.out.println(user.getNickname());
    }
}
