package com.yao.app.nebula.web.filter;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;

        JSONPResponseWrapper jsonsprw = new JSONPResponseWrapper(rsp);
        chain.doFilter(request, jsonsprw);

        String callback = req.getParameter("callback");

        byte[] b = null;

        if (StringUtils.isNotBlank(callback)) {
            StringBuffer sb = new StringBuffer();
            sb.append(callback).append("(").append(new String(jsonsprw.getResponseData())).append(")");
            b = sb.toString().getBytes();
        } else {
            b = jsonsprw.getResponseData();
        }

        rsp.getOutputStream().write(b);
        rsp.getOutputStream().flush();

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
