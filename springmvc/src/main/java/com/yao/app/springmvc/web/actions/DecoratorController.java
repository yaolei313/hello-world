package com.yao.app.springmvc.web.actions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DecoratorController {
    @RequestMapping("test")
    public String test(){
        // fixbug-1010 git practice
        return "test";
    }
}
