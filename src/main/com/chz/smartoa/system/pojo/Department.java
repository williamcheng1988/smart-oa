package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * Department对象.
 * 
 * @author huangdhui
 * 
 */
public class Department extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组织ID.
	 */
	private String departmentId;
	/**
	 * 组织创建者.
	 */
	private String createUser;
	/**
	 * 组织名称.
	 */
	private String departmentName;
	/**
	 * 组织的邮件.
	 */
	private String email;
	/**
	 * 组织创建时间.
	 */
	private String createDate;
	/**
	 * 组织描述.
	 */
	private String departmentDesc;
	/**
	 * 组织最后修改时间.
	 */
	private String lastUpdateDate;
	/**
	 * 父级组织ID.
	 */
	private String parentId;

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDepartmentDesc() {
		return this.departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("departmentId:").append(departmentId).append(",");
		sb.append("createUser:").append(createUser).append(",");
		sb.append("departmentName:").append(departmentName).append(",");
		sb.append("email:").append(email).append(",");
		sb.append("createDate:").append(createDate).append(",");
		sb.append("departmentDesc:").append(departmentDesc).append(",");
		sb.append("lastUpdateDate:").append(lastUpdateDate).append(",");
		sb.append("parentId:").append(parentId).append(",");
		sb.append("}");
		return sb.toString();
	}

}
