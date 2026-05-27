package com.yao.app.nebula.web.actions;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yaolei02 on 2018/11/30.
 */
@RestController
public class LogController {
    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);

    @RequestMapping(value = "/manager/log/{name}/{level}", method = RequestMethod.PUT)
    public String changeLevel(@PathVariable String name, @PathVariable String level) {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.getLogger(name).setLevel(Level.toLevel(level));

            return "SUCCESS";
        } catch (Exception e) {
            LOG.error("change log level fail.", e);
        }
        return "FAIL";
    }
}
