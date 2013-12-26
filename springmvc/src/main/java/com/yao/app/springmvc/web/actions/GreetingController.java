package com.yao.app.springmvc.web.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.yao.app.springmvc.service.UserService;

@Controller
public class GreetingController {

    @Autowired
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
    public String greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String findUser(@PathVariable String userId, Model model) {
        System.out.println("userId:" + userId);
        model.addAttribute("userId", userId);

        System.out.println(service.fetchUserInfo(userId));

        return "user";
    }

    @RequestMapping("/spring-web/{symbolicName:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}")
    public void handle(@PathVariable String symbolicName,
            @PathVariable String version, @PathVariable String extension) {

    }

    @RequestMapping("/ajax")
    @ResponseBody
    public List<String> getUserList() {
        List<String> result = new ArrayList<String>();
        result.add("电视");
        result.add("洗衣机");
        result.add("冰箱");

        return result;
    }

    @RequestMapping("/ajaxstring")
    @ResponseBody
    public String getUserName() {
        String name = "李白路过";

        return name;
    }

    @RequestMapping("/testexp")
    public void testException() throws Exception {
        throw new Exception("自己的spring异常处理测试");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
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
        return new ResponseEntity<String>("异常信息为：" + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }
}
