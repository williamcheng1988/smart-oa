package com.chz.smartoa.delegation.pojo;


/**
 * DelegationLog 委托处理日志
 * 
 * @author wesson
 */
public class DelegationLogVo{
	
	private String executionId;
	private String businessKey;
	private String businessTitle;
	private String loginName;
	private String createDate;
	private String delegationTime;
	private String dealTime;
	
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getBusinessTitle() {
		return businessTitle;
	}
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDelegationTime() {
		return delegationTime;
	}
	public void setDelegationTime(String delegationTime) {
		this.delegationTime = delegationTime;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
}
