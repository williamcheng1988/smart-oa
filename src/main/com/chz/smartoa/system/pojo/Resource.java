package com.chz.smartoa.system.pojo;

import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * Resource对象.
 * 
 * @author huangdhui
 * 
 */
public class Resource extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	
	public Resource() {
	}
	
	public Resource(String resourceId, String parentId,
			String resourceName, String resourceType,
			String addressUrl, int sortNum) {
		super();
		this.resourceId = resourceId;
		this.parentId = parentId;
		this.authType = AUTH_TYPE_AUTH;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.addressUrl = addressUrl;
		this.sortNum = sortNum;
	}
	
	public Resource(String resourceId, String parentId, String authType,
			String resourceName, String resourceType,
			String addressUrl, int sortNum, String iconName) {
		super();
		this.resourceId = resourceId;
		this.parentId = parentId;
		this.authType = authType;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.addressUrl = addressUrl;
		this.sortNum = sortNum;
		this.iconName = iconName;
	}



	/** 鉴权. */
    public static final String AUTH_TYPE_AUTH = "AUTH";    
    /** 不鉴权. */
    public static final String AUTH_TYPE_UNAUTH = "UNAUTH";
    /** 登录鉴权. */
    public static final String AUTH_TYPE_LOGIN_AUTH = "LOGIN_AUTH";	
	
	/**
	 * 资源ID.
	 */
	private String resourceId;
	/**
	 * 资源外码.
	 */
	private String parentId;
	/**
	 * 鉴权类型(AUTH：鉴权，UNAUTH：不鉴权，LOGIN_AUTH：登录鉴权).
	 */
	private String authType;
	/**
	 * 资源名称.
	 */
	private String resourceName;
	/**
	 * 资源类型:1:目录，2:数据权限
	 */
	private String resourceType;
	/**
	 * 资源描述.
	 */
	private String resourceDesc;
	/**
	 * 资源地址.
	 */
	private String addressUrl;
	/**
	 * 排序号
	 */
	private int sortNum;
	
	/**
	 * 是否选中
	 */
	private boolean checked;
	
	/**
	 * 图标名称
	 */
	private String iconName;
	
	private List<Resource> resources;
	
	
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getAddressUrl() {
		return this.addressUrl;
	}

	public void setAddressUrl(String addressUrl) {
		this.addressUrl = addressUrl;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", parentId=" + parentId
				+ ", authType=" + authType + ", resourceName=" + resourceName
				+ ", resourceType=" + resourceType + ", resourceDesc="
				+ resourceDesc + ", addressUrl=" + addressUrl + ", sortNum="
				+ sortNum + ", checked=" + checked + ", iconName=" + iconName
				+ ", resources=" + resources + "]";
	}
}
