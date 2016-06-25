package com.yao.app.nebula.web.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yao.app.nebula.domain.UserBean;
import com.yao.app.nebula.service.UserService;

@RestController
public class UserRestController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);

    @Resource(name = "db.user.service")
    private UserService service;
    
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET)
    public UserBean findUserForAjax(@PathVariable String userId) {
        LOG.info("userId:" + userId);
        UserBean user = service.queryUserByUsername(userId);
        LOG.info(user.getUsername());

        return user;
    }

    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, String> updateUser(@PathVariable String userId, @RequestParam String newEmail, Model model) {
        LOG.info("userId:" + userId);
        LOG.info("newEmail:" + newEmail);

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("newEmail", newEmail);

        return map;
    }
    
    @RequestMapping("/api/test")
    public List<String> getUserList() {
        List<String> result = new ArrayList<String>();
        result.add("电视");
        result.add("洗衣机");
        result.add("冰箱");

        return result;
    }
    
    /**
     * 内容协商，不涉及viewresolver
     * 
     * @return
     */
    @RequestMapping("/api/ajaxObject")
    public UserBean getUser() {
        UserBean user = new UserBean();
        user.setUsername("y00196907");
        user.setNickname("李白路过");
        user.setRegisterTime(new Date());
        user.setEmail("yaolei313@gmail.com");

        return user;
    }
}
