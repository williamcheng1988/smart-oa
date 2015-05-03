package com.chz.smartoa.task.pojo;

import java.io.Serializable;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 历史任务
 * 
 * @author wesson
 */
public class HiTask extends BaseDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * .
	 */
	private Integer version;
	/**
	 * .
	 */
	private Integer confId;
	/**
	 * .
	 */
	private String assignee;

	/**
	 * .
	 */
	private String delegation;
	/**
	 * .
	 */
	private String approveDesc;
	/**
	 * .
	 */
	private String assigneeName;
	/**
	 * .
	 */
	private Integer taskNum;
	/**
	 * .
	 */
	private String endTime;
	/**
	 * .
	 */
	private String executionId;
	/**
	 * .
	 */
	private String delegationName;
	/**
	 * .
	 */
	private Integer operateResult;
	/**
	 * .
	 */
	private String startTime;
	/**
	 * .
	 */
	private Integer recordType;
	/**
	 * .
	 */
	private Integer expiryDays;

	private Integer approveType;
	
	private Integer taskId;
	
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getConfId() {
		return this.confId;
	}

	public void setConfId(Integer confId) {
		this.confId = confId;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDelegation() {
		return this.delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getApproveDesc() {
		return this.approveDesc;
	}

	public void setApproveDesc(String approveDesc) {
		this.approveDesc = approveDesc;
	}

	public String getAssigneeName() {
		return this.assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Integer getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getDelegationName() {
		return this.delegationName;
	}

	public void setDelegationName(String delegationName) {
		this.delegationName = delegationName;
	}

	public Integer getOperateResult() {
		return this.operateResult;
	}

	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public Integer getExpiryDays() {
		return expiryDays;
	}

	public void setExpiryDays(Integer expiryDays) {
		this.expiryDays = expiryDays;
	}

	public Integer getApproveType() {
		return approveType;
	}

	public void setApproveType(Integer approveType) {
		this.approveType = approveType;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "HiTask [version=" + version + ", confId=" + confId
				+ ", assignee=" + assignee + ", delegation=" + delegation
				+ ", approveDesc=" + approveDesc + ", assigneeName="
				+ assigneeName + ", taskNum=" + taskNum + ", endTime="
				+ endTime + ", executionId=" + executionId
				+ ", delegationName=" + delegationName + ", operateResult="
				+ operateResult + ", startTime=" + startTime + ", recordType="
				+ recordType + ", expiryDays=" + expiryDays + ", approveType="
				+ approveType + ", taskId=" + taskId + "]";
	}

}
