package com.chz.smartoa.common.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.global.ResourceMgr;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.AuthorizeBiz;

public class AuthorizeFilter implements Filter {
    /*** logger.*/
    private static final Logger logger = Logger.getLogger(AuthorizeFilter.class);

    /*** 缺省鉴权失败重定向页. */
    public static final String LOGIN_URL = "/login.html";
    /*** 缺省未授权提醒页面 */
    public static final String AUTH_ERROR_URL = "/common/authError.html";

    /***鉴权接口. */
    AuthorizeBiz authorizeBiz;
    
    String loginUrl = LOGIN_URL;
    String authErrorUrl = AUTH_ERROR_URL;
    
    public void setAuthorizeBiz(AuthorizeBiz authorizeBiz) {
		this.authorizeBiz = authorizeBiz;
	}

	public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
    	HttpServletResponse httpResponse = (HttpServletResponse)response;        
        HttpSession session = httpRequest.getSession(false);
        String loginName = null;

        // 是否登录标志.
        boolean isLogin = false;
        List<String> operationUris = null;
        if (session !=null ){
        	Staff loginStaff = LoginUtils.getLoginStaff(session);
        	if(loginStaff != null){
        		operationUris = loginStaff.getPermission().getOperationUris();
        		loginName = loginStaff.getLoginName();
                isLogin = true;
        	}
        }
        
        // 得到访问的URL和参数MAP
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();
        String addressUri = StringUtils.substringAfter(requestUri, contextPath);
        
        boolean bAuth = authorizeAddress(loginName, addressUri, isLogin,operationUris);
        if (bAuth) {
            // 鉴权成功
            chain.doFilter(request, response);
            return;        	
        } else {
        	if (isLogin) {
        		// 跳转到鉴权出错页
        		httpResponse.sendRedirect(httpRequest.getContextPath() + authErrorUrl);     
        	} else {
        		// 跳转到登录页
        		if("/index.do".equals(addressUri)||"/".equals(addressUri)){
        			httpResponse.sendRedirect(httpRequest.getContextPath() + loginUrl);  
        		}else{
        			httpResponse.sendRedirect(httpRequest.getContextPath() + loginUrl+"?lastUri="+requestUri);  
        		}
        	}
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化参数
        String tmpLoginUrl = filterConfig.getInitParameter("loginUrl");
        if (tmpLoginUrl != null) {
            loginUrl = tmpLoginUrl;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("鉴权失败登录页：" + loginUrl);
        }
        // 初始化参数
        String tmpAuthErrorUrl = filterConfig.getInitParameter("authErrorUrl");
        if (tmpAuthErrorUrl != null) {
        	authErrorUrl = tmpAuthErrorUrl;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("鉴权失败提示页：" + authErrorUrl);
        }
    }
    
    /**
     * 地址鉴权.
     * @param loginName    登录用户名
     * @param addressUrl   地址url
     * @param isLogin 登录状态 
     * @return 成功|失败
     */
    private boolean authorizeAddress(String loginName, String addressUrl,boolean isLogin,List<String> operationUris) {
    	if(logger.isDebugEnabled()){
 			logger.debug("进入authorizeAddress方法，参数loginName=" +loginName+",addressUrl="+addressUrl);
 		}
    	
    	if (StringUtils.isEmpty(addressUrl)) {
            logger.debug("addressUrl为空！");
    		return false;
        }

        // 是否是特殊资源不需要鉴权就能访问 
    	if (isSpecialAddress(addressUrl)) {
    		return true;
    	}
    	
    	// 如果不是特殊资源，是必须要登录的
    	if (StringUtils.isEmpty(loginName)) {
            logger.debug("loginName为空！");
    		return false;
        }
    	
    	// 需要登录才能访问 
    	if (isLoginAddress(addressUrl)) {
    		return true;
    	}
    	// 根据登录用户名和地址查询用户的可访问的资源url
        if (operationUris != null && (operationUris.contains(addressUrl)||operationUris.contains(StringUtils.substringAfter(addressUrl,"/")))) {
            // 有访问权限
        	return true;
        }
        
        logger.debug("无权访问："+addressUrl);
        return false;
    }
    
    
    /**
     * // 不需要登录就能访问 
     * @return
     */
    private boolean isLoginAddress(String addressUrl) {
    	List<String> addressUrls = ResourceMgr.getInstance().getLoginAuths();
    	if(addressUrls!=null){
    		for (String url : addressUrls) {
				if(addressUrl.equals(url)||StringUtils.substringAfter(addressUrl,"/").equals(url)){
					return true;
				}
			}
    	}
        //需要登录地址
        return false;
    }
  
    /**
     * 是否登录就可访问资源或无需鉴权资源.
     * @return
     */
    private boolean isSpecialAddress(String addressUrl) {
    	//不需要鉴权地址列表
    	List<String> addressUrls = ResourceMgr.getInstance().getUnAuths();
    	if(addressUrls!=null){
    		for (String url : addressUrls) {
				if(addressUrl.equals(url)||StringUtils.substringAfter(addressUrl,"/").equals(url)){
					return true;
				}
			}
    	}
        //需要鉴权地址
        return false;
    }
}
