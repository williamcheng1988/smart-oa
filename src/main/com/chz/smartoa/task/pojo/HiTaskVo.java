package com.chz.smartoa.task.pojo;

/**
 * 历史任务 VO
 * @author wesson
 */
public class HiTaskVo {
	/**
	 * 流程实例ID
	 */
	private String executionId;
	/**
	 * ID
	 */
	private String businessKey;
	/**
	 * 主题
	 */
	private String businessTitle;
	/**
	 * 发起人
	 */
	private String realName;
	/**
	 * 发起时间
	 */
	private String startTime;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 当前处理步骤
	 */
	private String dealSteps;
	/**
	 * 当前处理人
	 */
	private String dealMans;
	
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
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getDealSteps() {
		return dealSteps;
	}
	public void setDealSteps(String dealSteps) {
		this.dealSteps = dealSteps;
	}
	public String getDealMans() {
		return dealMans;
	}
	public void setDealMans(String dealMans) {
		this.dealMans = dealMans;
	}
	
	@Override
	public String toString() {
		return "HiTaskVo [executionId=" + executionId + ", businessKey="
				+ businessKey + ", businessTitle=" + businessTitle
				+ ", realName=" + realName + ", startTime=" + startTime
				+ ", templateId=" + templateId + ", dealSteps=" + dealSteps
				+ ", dealMans=" + dealMans + "]";
	}
}
