package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 部门岗位.
 * @author huangdhui
 */
public class DepartmentPostStaffs extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	
	//部门ID
	private String departmentId;
	//部门名称 
	private String departmentName;
	//岗位ID
	private String postId;
	//岗位名称
	private String postName;
	//用户账号，多个账号以英文逗号分隔
	private String staffIds;
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getStaffIds() {
		return staffIds;
	}
	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}
}