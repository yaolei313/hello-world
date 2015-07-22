package com.yao.app.springmvc.web.actions;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DecoratorController {
    @RequestMapping("test")
    public String test(Model model){
        // fixbug-1010 git practice
        model.addAttribute("today", new Date());
        return "test";
    }
}
