package com.chz.smartoa.system.pojo;

/**
 * StaffRole对象.
 * 
 * @author huangdhui
 */
public class StaffRole {
	/**
	 * 角色ID.
	 */
	private String roleId;
	/**
	 * 成员ID.
	 */
	private String staffId;
	/**
	 * 项目ID
	 */
	private String projectId;

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("roleId:").append(roleId).append(",");
		sb.append("staffId:").append(staffId).append(",");
		sb.append("}");
		return sb.toString();
	}

}
