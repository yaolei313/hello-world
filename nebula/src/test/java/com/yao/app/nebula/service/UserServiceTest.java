package com.yao.app.nebula.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import javax.annotation.Resource;

import org.junit.Test;

import com.yao.app.nebula.basic.AbstractTest;
import com.yao.app.nebula.domain.UserBean;
import com.yao.app.nebula.service.UserService;
import com.yao.app.nebula.util.ApplicationContextUtil;
import com.yao.app.nebula.web.vo.Student;

public class UserServiceTest extends AbstractTest {

    @Resource
    private UserService service;
    
    @Test
    public void test1(){
        UserBean user = service.queryUserByUsername("y00196907");
        
        
        assertThat(user.getNickname()).isEqualTo("李白");
        System.out.println(user.getNickname());
    }

    @Test
    public void test2() {
        UserBean user = new UserBean();
        user.setUsername("y00196907");
        user.setEmail("123@123.com");
        user.setNickname("李白路过");
        user.setSex("M");
        user.setGravatarMail("");
        user.setRegisterTime(new Date());

        service.addUser(user);
    }
    
    /*@Test
    public void testAop(){
    	// 配置已注释，包括Aspect和spring xml
    	Student s1 = ApplicationContextUtil.getBean(Student.class);
    	s1.setName("s1");
    	Student s2 = ApplicationContextUtil.getBean(Student.class);
    	//s2.setName("s2");
    	Student s3 = ApplicationContextUtil.getBean(Student.class);
    	//s3.setName("s3");
    	
    	System.out.println(s1.getName());
    	s1=null;
    	System.gc();
    	
    	System.out.println(s2.getName());
    	System.out.println(s3.getName());
    	
    	
    }*/
}
