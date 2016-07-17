package com.yao.app.nebula.web.actions;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DecoratorController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DecoratorController.class);
	
    @RequestMapping("test")
    public String test(Model model){
        // fixbug-1010 git practice
        model.addAttribute("today", new Date());
        
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(DecoratorController.class.getClassLoader());
        
        return "test";
    }
}
