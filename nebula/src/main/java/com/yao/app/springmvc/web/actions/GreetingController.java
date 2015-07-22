package com.yao.app.springmvc.web.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.service.UserService;

@Controller
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @Resource(name = "db.user.service")
    private UserService service;

    @ModelAttribute
    public String addString() {
        return "yaolei";
    }

    @ModelAttribute("name")
    public String addString2() {
        return "yaolei";
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("test", "yaolei's test");
        model.addAttribute("hello", "world");
    }

    @RequestMapping(value = "/greeting")
    public ModelAndView greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name2", name);

        //ModelMap map = new ModelMap();
        //map.put("message", "qwqwqwqqw");   
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "andy");
        mav.addObject("umessage", "<b>哈哈</b>");
        mav.setViewName("greeting");

        return mav;
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model){
        LOG.info("query user list");
        List<UserBean> users = service.queryUsers();
        model.addAttribute("users", users);
        
        
        return "user/list";
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String findUser(@PathVariable String userId, Model model) {
        LOG.info("userId:" + userId);
        model.addAttribute("userId", userId);

        UserBean user = service.queryUserById(userId);
        model.addAttribute("user",user);

        LOG.info(user.getName());

        return "user";
    }
    
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserBean findUserForAjax(@PathVariable String userId) {
        LOG.info("userId:" + userId);
        UserBean user = service.queryUserById(userId);
        LOG.info(user.getName());

        return user;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map<String, String> updateUser(@PathVariable String userId, @RequestParam String newEmail, Model model) {
        LOG.info("userId:" + userId);
        LOG.info("newEmail:" + newEmail);

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("newEmail", newEmail);

        return map;
    }

    @RequestMapping("/spring-web/{symbolicName:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}")
    public void handle(@PathVariable String symbolicName, @PathVariable String version, @PathVariable String extension) {
        LOG.info("test");
    }

    @RequestMapping("/api/test")
    @ResponseBody
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
    @RequestMapping("/ajaxObject")
    @ResponseBody
    public UserBean getUser() {
        UserBean user = new UserBean();
        user.setId("y00196907");
        user.setName("李白路过");
        user.setRegisterTime(new Date());
        user.setEmail("yaolei313@gmail.com");

        return user;
    }

    @RequestMapping("/testexp")
    public void testException() throws IllegalArgumentException {
        throw new IllegalArgumentException("异常处理测试1，走SimpleMappingExceptionResolver");
    }

    @RequestMapping("/testexp2")
    @ResponseBody
    public void testException2() throws IllegalArgumentException {
        throw new IllegalArgumentException("异常处理测试2，走CustomHandlerExceptionResolver，使用了内容协商");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * action中存在ExceptionHandler时，使用action中的处理，而不是SimpleMappingExceptionResolver
     * 
     * @param ex
     * @return
     */
    // @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIOException(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<String>("异常信息为：" + ex.getMessage(), responseHeaders, HttpStatus.CREATED);
    }
}
