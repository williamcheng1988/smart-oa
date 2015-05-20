package com.chz.smartoa.common.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.chz.smartoa.common.listerner.SessionListener;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.pojo.Permission;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;

public class LoginUtils {
	/*** 登录用户.*/    
	public static final String LOGIN_STAFF = "LOGIN_STAFF";
	
	/*** IP.*/    
	public static final String CLIENT_IP = "CLIENT_IP";	
	
	public static void setLoginStaff(Staff staff, HttpSession httpSession) {
		//设置权限
		setPermission(staff);
		// 写在会话中.
		httpSession.setAttribute(LOGIN_STAFF, staff);
		// 同时写入客户端IP
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request != null) { 
        	String clientIp =  getClientIp(request);
        	httpSession.setAttribute(CLIENT_IP, clientIp);
        } 
	}
	
	public static void setLoginStaff(Staff staff, HttpSession httpSession ,HttpServletRequest request) {
		//设置权限
		setPermission(staff);
		// 写在会话中.
		httpSession.setAttribute(LOGIN_STAFF, staff);
		// 同时写入客户端IP
        if (request != null) { 
        	String clientIp =  getClientIp(request);
        	httpSession.setAttribute(CLIENT_IP, clientIp);
        } 
	}
	
	private static void setPermission(Staff staff) {
		// 查询用户的相关角色列表
		Permission per = new Permission();
		List<Role> roles = ((RoleDao)ServerInfo.wac.getBean("roleDao")).listStaffRole(staff.getLoginName());
		if (roles != null && roles.size() > 0) {
			staff.setRoles(roles);
			// 加载权限
			for (Role role : roles) {
				if ("Y".equals(role.getSuperAdmin())) {
					per.setAdmin(true);
					break;
				}
			}
			List<String> uris = ((StaffDao)ServerInfo.wac.getBean("staffDao")).listOperationUri(staff.getLoginName());
			per.setOperationUris(uris);
		}
		staff.setPermission(per);

		// 查询用户所属的组织
		if (!StringUtils.isEmpty(staff.getDepartmentId())) {
			Department department = ((DepartmentDao)ServerInfo.wac.getBean("departmentDao")).findDepartment(staff.getDepartmentId());
			staff.setDepartment(department);
		}
	}

	public static Staff getLoginStaff() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession(true);        
        if (session == null) {
            return null;
        }
        return getLoginStaff(session);
	}
	
	public static String getClientIp() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null) {
            return null;
        }
        
        HttpSession session = request.getSession(true);        
        if (session == null) {
            return null;
        }

        return getClientIp(session);
	}
	
	
	public static String getClientIp(HttpSession httpSession) {
		String clientIp = null;
		
		// 从会话中得到客户端IP
		if (httpSession != null) {
			clientIp = (String)httpSession.getAttribute(CLIENT_IP);
		}		

		return clientIp;		
	}		
	
	public static Staff getLoginStaff(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Staff staff = null;
		// 从会话中得到登录用户
		if (httpSession != null) {
			staff = (Staff)httpSession.getAttribute(LOGIN_STAFF);
		}
		return staff;		
	}
	
	public static Staff getLoginStaff(HttpSession httpSession) {
		Staff staff = null;
		// 从会话中得到登录用户
		if (httpSession != null) {
			staff = (Staff)httpSession.getAttribute(LOGIN_STAFF);
		}
		return staff;		
	}	
	
	public static Staff getLoginStaff(String sessionId) {
		// 根据会话ID得到会话
		HttpSession httpSession = SessionListener.getSession(sessionId);
		
		return getLoginStaff(httpSession);		
	}	
	
    /**
     * 得到客户端真实IP
     * @param request 请求
     * @return 客户端真实IP
     */
    private static String getClientIp(HttpServletRequest request) {   
        String ip = request.getHeader("x-forwarded-for");   
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("Proxy-Client-IP");   
        }   
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getHeader("WL-Proxy-Client-IP");   
        }   
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
            ip = request.getRemoteAddr();   
        }   
        return ip;   
    }  	
}
