package com.yao.app.nebula.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }
    
    public static String getUserIp(){
        return "";
    }
}
