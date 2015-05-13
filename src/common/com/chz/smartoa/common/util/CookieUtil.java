package com.chz.smartoa.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.pojo.Staff;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CookieUtil {
	private static final Logger logger = Logger.getLogger(CookieUtil.class);
	// 保存cookie时的cookieName
	private final static String cookieDomainName = "smartoa_auth";
	// 加密cookie时的网站自定码
	private final static String webKey = "smaroa2015";
	// 设置cookie有效期是两个星期，根据需要自定义
	private final static int cookieMaxAge = 60 * 60 * 24 * 7 * 2;

	/**
	 * 写入cookie算法
	 * @param staff
	 * @param response
	 */
	public static void saveLoginCookie(Staff staff,HttpServletResponse response){
		//cookie的有效期  自定义
		long validTime = System.currentTimeMillis() + (CookieUtil.cookieMaxAge * 1000);
		//MD5加密用户详细信息
		String cookieValueWithMd5 = DigestUtils.md5DigestAsHex((staff.getLoginName() + ":" + staff.getPassword() + ":" + validTime + ":" + CookieUtil.webKey).getBytes());
		//将要被保存的完整的Cookie值
		String cookieValue = staff.getLoginName() + ":" + validTime + ":" + cookieValueWithMd5;
		//再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
		//开始保存Cookie
		Cookie cookie = new Cookie(CookieUtil.cookieDomainName, cookieValueBase64);
		//(这个值应该大于或等于validTime)
		cookie.setMaxAge(CookieUtil.cookieMaxAge);
		//cookie有效路径是网站根目录
		cookie.setPath("/");
		//向客户端写入
		response.addCookie(cookie);
	}
	public static void readCookieAndLogin(HttpServletRequest request ,HttpServletResponse response,FilterChain chain) throws ServletException, IOException{
		Boolean isAuth = false;
		//根据cookieName取cookieValue
		Cookie[] cookies = request.getCookies();
		//取出来校验登陆的cookie
		String cookieValue = null;
		if (null != cookies) {
			for (int i = 0; i <  cookies.length ; i++) {
				if (CookieUtil.cookieDomainName.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		if (StringUtils.isNotEmpty(cookieValue)) {
			//先得到的CookieValue进行Base64解码
			String cookieValueAfterDecode = null;
			try {
				cookieValueAfterDecode = new String (Base64.decode(cookieValue),"utf-8");
			} catch (UnsupportedEncodingException e) {
				CookieUtil.logger.error("解密cookie : " +cookieValueAfterDecode + ",失败" + e );
			}
			//对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
			if (StringUtils.isNotEmpty(cookieValueAfterDecode)) {
				String cookieValues[] = cookieValueAfterDecode.split(":");
				if(cookieValues.length == 3){
					//判断是否在有效期内,过期就删除Cookie
					long validTimeInCookie = new Long(cookieValues[1]);
					if(validTimeInCookie < System.currentTimeMillis()){
						//删除Cookie
						CookieUtil.clearCookie(response);
					} else {
						//根据用户名查询staff信息
						StaffDao staffDao = (StaffDao) ServerInfo.wac.getBean("staffDao");
						Staff staff = staffDao.findStaffByLoginName(cookieValues[0]);
						if (null != staff) {
							String md5ValueInCookie = cookieValues[2];
							String md5ValueFromUser =DigestUtils.md5DigestAsHex((staff.getLoginName() + ":" + staff.getPassword()
									+ ":" + validTimeInCookie + ":" + CookieUtil.webKey).getBytes());
							//将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
							if(md5ValueFromUser.equals(md5ValueInCookie)){
								LoginUtils.setLoginStaff(staff, request.getSession(true));
								//这个会导致客户端地址不改变 其实已经到主页
								//request.getRequestDispatcher("home.do").forward(request, response);
								isAuth = true;
								response.sendRedirect("home.do");
							} else {
								CookieUtil.clearCookie(response);
							}
						} else {
							//删除Cookie  用户都不存在了要cookie何用。
							CookieUtil.clearCookie(response);
						}
					}
				}
			}
		}
		if (!isAuth) {
			//没有通过就继续走
			chain.doFilter(request, response);
		}
	}
	//清除Cookie
	public static void clearCookie( HttpServletResponse response){
		Cookie cookie = new Cookie(CookieUtil.cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
