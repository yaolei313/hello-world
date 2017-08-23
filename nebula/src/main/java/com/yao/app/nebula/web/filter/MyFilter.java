package com.yao.app.nebula.web.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String callback = req.getParameter("callback");
        if (StringUtils.isNotBlank(callback)) {
            HttpServletResponse rsp = (HttpServletResponse) response;

            JSONPResponseWrapper jsonsprw = new JSONPResponseWrapper(rsp);
            chain.doFilter(request, jsonsprw);

            StringBuffer sb = new StringBuffer();
            sb.append(callback).append("(").append(new String(jsonsprw.getResponseData())).append(")");
            byte[] b = sb.toString().getBytes();

            rsp.getOutputStream().write(b);
            rsp.getOutputStream().flush();
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
