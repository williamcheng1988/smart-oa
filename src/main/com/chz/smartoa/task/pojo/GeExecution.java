package com.chz.smartoa.task.pojo;

import org.apache.commons.lang.StringUtils;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * GeExecution
 * 流程任务实例
 * @author wesson
 * 
 */
public class GeExecution extends BaseDomain{
	
	private static final long serialVersionUID = 4098297514068322929L;
	/**
	 * 实例ID
	 */
	private String executionId;
	/**
	 * 发起人
	 */
	private String owner;
	/**
	 * 所属部门
	 */
	private String departmentId;
	/**
	 * 0:暂存,1:审批中2:审批通过,3:审批不通过,4:任务作废
	 */
	private Integer taskStatus;
	/**
	 * 版本（第几次发起）.
	 */
	private Integer version;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 业务标题
	 */
	private String businessTitle;
	/**
	 * 当前节点
	 */
	private Integer taskNum;
	/**
	 * 紧急程度（0：一般，1：高，2：紧急）
	 */
	private Integer priority;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 业务ID
	 */
	private String businessKey;
	/**
	 * 流程ID
	 */
	private String procdefId;
	/**
	 * 项目ID
	 */
	private String projectId;
	/**
	 * 描述
	 */
	private String desc;

	public String getExecutionId() {
		return this.executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getOwner() {
		return this.owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getTaskStatus() {
		return this.taskStatus;
	}
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getVersion() {
		return this.version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getBusinessTitle() {
		return this.businessTitle;
	}
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}
	public Integer getTaskNum() {
		return this.taskNum;
	}
	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}
	public Integer getPriority() {
		return this.priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getEndTime() {
		return this.endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return this.startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getBusinessKey() {
		return this.businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getProcdefId() {
		return this.procdefId;
	}
	public String[] getProcdefIdS(){
		if(StringUtils.isNotEmpty(this.procdefId)){
			return this.procdefId.split(",");
		}
		return null;
	}
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("executionId:").append(executionId).append(",");
		sb.append("owner:").append(owner).append(",");
		sb.append("taskStatus:").append(taskStatus).append(",");
		sb.append("version:").append(version).append(",");
		sb.append("templateId:").append(templateId).append(",");
		sb.append("businessTitle:").append(businessTitle).append(",");
		sb.append("taskNum:").append(taskNum).append(",");
		sb.append("priority:").append(priority).append(",");
		sb.append("endTime:").append(endTime).append(",");
		sb.append("startTime:").append(startTime).append(",");
		sb.append("businessKey:").append(businessKey).append(",");
		sb.append("procdefId:").append(procdefId).append(",");

		sb.append("}");
		return sb.toString();
	}

}
