package com.yao.app.nebula.util;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 
 * @author yaolei
 *
 */
public class LogFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger("uncaughtLog");
	
	private static final String DEFAULT_MAP = "OTHERS";
	
	private Map<String,String> mappingRule;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				LOG.error("Uncaught error in thread " + t.getName(), e);
			}
		});
		
		mappingRule = new HashMap<String,String>();
		mappingRule.put("login", "LOGIN");
		mappingRule.put("users", "USER");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// 为每个请求处理的线程增加一个日志的UUID
		MDC.put("log-uuid", UUID.randomUUID().toString());
		// 获取busitype使用SiftingAppender
		String busiType = getBusiType(httpRequest.getServletPath());
		MDC.put("sifting-id", busiType);
		chain.doFilter(request, response);
		MDC.remove("sifting-id");
		MDC.remove("log-uuid");
	}

	@Override
	public void destroy() {

	}
	
	private String getBusiType(String servletPath) {
		// TODO Auto-generated method stub
		return DEFAULT_MAP;
	}

}
