package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * ResourceOption对象.
 * 
 * @author huangdhui
 * 
 */
public class ResourceOption extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 资源ID.
	 */
	private String resourceId;
	/**
	 * 资源操作名称.
	 */
	private String optionName;
	/**
	 * 资源操作地址.
	 */
	private String addressUrl;
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getAddressUrl() {
		return addressUrl;
	}
	public void setAddressUrl(String addressUrl) {
		this.addressUrl = addressUrl;
	}
}
