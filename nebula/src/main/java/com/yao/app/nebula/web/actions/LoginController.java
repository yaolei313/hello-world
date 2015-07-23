package com.yao.app.nebula.web.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @ModelAttribute
    public void addAttribute(Model model) {
        // 每个方法执行前被执行
        model.addAttribute("test", "yaolei's test");
        model.addAttribute("hello", "world");
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        log.debug("jump to login page");

        return "login";
    }

    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }
}
