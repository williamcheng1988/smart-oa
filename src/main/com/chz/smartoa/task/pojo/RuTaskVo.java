package com.chz.smartoa.task.pojo;

import java.io.Serializable;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 待办任务 VO
 * @author wesson
 */
public class RuTaskVo extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 紧急程度
	 */
	private int priority;
	/**
	 * ID
	 */
	private String businessKey;
	/**
	 * 主题
	 */
	private String businessTitle;
	/**
	 * 发起人Id
	 */
	private String owner;
	/**
	 * 发起人
	 */
	private String realName;
	/**
	 * 发起时间
	 */
	private String startTime;
	/**
	 * 到达时间
	 */
	private String arriveTime;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 项目ID
	 */
	private String projectId;
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	@Override
	public String toString() {
		return "RuTaskVo [taskId=" + taskId + ", priority=" + priority
				+ ", businessKey=" + businessKey + ", businessTitle="
				+ businessTitle + ", owner=" + owner + ", realName=" + realName
				+ ", startTime=" + startTime + ", templateId=" + templateId
				+ ", projectId=" + projectId + "]";
	}
}
