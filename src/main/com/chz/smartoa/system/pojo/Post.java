package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 岗位对象.
 * @author huangdhui
 */
public class Post extends BaseDomain {
	private static final long serialVersionUID = 1L;
	/**
	 * 岗位ID.
	 */
	private String postId;
	/**
	 * 组织创建者.
	 */
	private String createUser;
	/**
	 * 岗位名称.
	 */
	private String departmentName;
	/**
	 * 组织创建时间.
	 */
	private String createDate;
	/**
	 * 组织最后修改时间.
	 */
	private String lastUpdateDate;
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
