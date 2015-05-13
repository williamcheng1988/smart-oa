package com.chz.smartoa.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.chz.smartoa.common.util.CookieUtil;

/**
 * 记住密码自动登录
 * <pre>
 * *********************************************
 * Copyright sf-express.
 * All rights reserved.
 * Description:
 * HISTORY
 * ****************************************************************
 * DATE           PERSON          REASON
 * 2015年5月13日        sfit0472         Create
 * ****************************************************************
 * </pre>
 */
public class AutoLoginAuth implements Filter {


	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String contextPath = httpRequest.getContextPath();
		String requestUri = httpRequest.getRequestURI();
		String addressUri = StringUtils.substringAfter(requestUri, contextPath);
		if("/login.html".equals(addressUri)||"/".equals(addressUri)){
			CookieUtil.readCookieAndLogin(httpRequest, (HttpServletResponse)response,chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
