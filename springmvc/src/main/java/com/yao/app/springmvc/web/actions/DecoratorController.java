package com.yao.app.springmvc.web.actions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DecoratorController {
    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
