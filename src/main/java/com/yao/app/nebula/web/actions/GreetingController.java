package com.yao.app.nebula.web.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yao.app.nebula.domain.UserBean;
import com.yao.app.nebula.service.UserService;

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

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    public String findUser(@PathVariable String userName, Model model) {
        LOG.info("userId:" + userName);
        model.addAttribute("userId", userName);

        UserBean user = service.queryUserByUsername(userName);
        model.addAttribute("user",user);

        LOG.info(user.getUsername());

        return "user";
    }
    
    

    @RequestMapping("/spring-web/{symbolicName:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}")
    public void handle(@PathVariable String symbolicName, @PathVariable String version, @PathVariable String extension) {
        LOG.info("test");
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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIOException(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<String>("异常信息为：" + ex.getMessage(), responseHeaders, HttpStatus.CREATED);
    }
}
