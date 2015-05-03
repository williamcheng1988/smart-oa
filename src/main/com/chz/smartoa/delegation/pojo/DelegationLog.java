package com.chz.smartoa.delegation.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * DelegationLog 委托处理日志
 * 
 * @author wesson
 */
public class DelegationLog extends BaseDomain{
	
	private static final long serialVersionUID = 8384368650870216440L;
	/**
	 * 委托ID.
	 */
	private String delegationId;
	/**
	 * 流程实例ID.
	 */
	private String executionId;
	/**
	 * 到达时间.
	 */
	private String createDate;
	/**
	 * 处理时间：时间为null表示未处理
	 */
	private String dealDate;
	
	//查询参数,委托用户
	private String fromUser;

	public String getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(String delegationId) {
		this.delegationId = delegationId;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
}
