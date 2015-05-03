package com.chz.smartoa.system.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permission implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//返回空或int  1:可查看所有数据，0：不可查看所有数据
	public static String ALL_DATA = "allData";
	//返回空或List 查询查看的项目全部数据的项目ID
	public static String PROJECT_IDS = "projectIds";
	//返回空或String 当前用户ID
	public static String LOGIN_NAME = "loginName";
	
	/**
	 * 是否具有超级管理员权限
	 */
	private boolean isAdmin;
	/**
	 * 可操作Uri
	 */
	private List<String> operationUris;
	/**
	 * 数据权限
	 */
	private Map<String,Object> dataPermissions = new HashMap<String, Object>();
	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public List<String> getOperationUris() {
		return operationUris;
	}
	public void setOperationUris(List<String> operationUris) {
		this.operationUris = operationUris;
	}
	public Map<String, Object> getDataPermissions() {
		return dataPermissions;
	}
	public void setDataPermissions(Map<String, Object> dataPermissions) {
		this.dataPermissions = dataPermissions;
	}
}
