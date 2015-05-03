package com.chz.smartoa.task.pojo;

/**
 * 待办任务
 * @author wesson
 * 
 */
public class RuTask {
	/**
	 * 任务ID
	 */
	private Integer taskId;
	/**
	 * 到达时间
	 */
	private String arriveTime;
	/**
	 * 征询人
	 */
	private String consult;
	/**
	 * 流程明细
	 */
	private Integer confId;
	/**
	 * 委托人
	 */
	private String delegation;
	/**
	 * 处理人
	 */
	private String assignee;
	/**
	 * 审批时长
	 */
	private Integer expiryDays;
	/**
	 * 流程实例ID
	 */
	private String executionId;

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getArriveTime() {
		return this.arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getConsult() {
		return this.consult;
	}

	public void setConsult(String consult) {
		this.consult = consult;
	}

	public Integer getConfId() {
		return this.confId;
	}

	public void setConfId(Integer confId) {
		this.confId = confId;
	}

	public String getDelegation() {
		return this.delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Integer getExpiryDays() {
		return this.expiryDays;
	}

	public void setExpiryDays(Integer expiryDays) {
		this.expiryDays = expiryDays;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("taskId:").append(taskId).append(",");
		sb.append("arriveTime:").append(arriveTime).append(",");
		sb.append("consult:").append(consult).append(",");
		sb.append("confId:").append(confId).append(",");
		sb.append("delegation:").append(delegation).append(",");
		sb.append("assignee:").append(assignee).append(",");
		sb.append("expiryDays:").append(expiryDays).append(",");
		sb.append("executionId:").append(executionId).append(",");
		sb.append("}");
		return sb.toString();
	}

}
