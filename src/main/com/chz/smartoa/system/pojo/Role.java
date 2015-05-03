package com.chz.smartoa.system.pojo;

import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * Role对象.
 * @author huangdhui
 */
public class Role extends BaseDomain {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID.
	 */
	private String roleId;
	/**
	 * 角色描述.
	 */
	private String roleDesc;
	/**
	 * 角色类型 1：公司角色  2：项目角色 
	 */
	private String roleType;
	/**
	 * 角色外码.
	 */
	private String roleKey;
	/**
	 * 角色创建者.
	 */
	private String createUser;
	/**
	 * 角色创建时间.
	 */
	private String createDate;
	/**
	 * 角色名称.
	 */
	private String roleName;
	/**
	 * 角色最后修改时间.
	 */
	private String lastUpdateDate;
	/**
	 * 是否是超级管理员：Y：是   N：不是 
	 */
	private String superAdmin;
	/**
	 * 是否是职级
	 */
	private int level = 0;
	/**
	 * 数据权限：1，个人数据;2，项目数据;3，公司数据
	 */
	private String dataType;
	
	/**
	 * 角色对应资源
	 */
	private List<String> resourceIds;
	/**
	 * 是否被选中
	 */
	private boolean checked;

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleKey() {
		return this.roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}
	public List<String> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
//		if("1".equals(roleType)){
//			this.dataType = "0";
//			return;
//		}
		this.dataType = dataType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleDesc=" + roleDesc
				+ ", roleType=" + roleType + ", roleKey=" + roleKey
				+ ", createUser=" + createUser + ", createDate=" + createDate
				+ ", roleName=" + roleName + ", lastUpdateDate="
				+ lastUpdateDate + ", superAdmin=" + superAdmin
				+ ", resourceIds=" + resourceIds + ", checked=" + checked + "]";
	}
}
