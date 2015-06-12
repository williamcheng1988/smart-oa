package com.chz.smartoa.system.constant;

public enum TemplateType {
	//任务到达邮件提醒
	HTML_TASK_ARRIVE("html_task_arrive.vm"),
	TASK_ARRIVE("task_arrive.vm"),
	//任务退回邮件提醒
	HTML_TASK_RETURN("html_task_return.vm"),
	TASK_RETURN("task_return.vm"),
	//任务超时邮件提醒
	HTML_TASK_EXPIRY("html_task_expiry.vm"),
	TASK_EXPIRY("task_expiry.vm"),
	//任务审批通过邮件提醒
	HTML_TASK_COMPLETE("html_task_complete.vm"),
	TASK_COMPLETE("task_complete.vm"),
	//任务审批不通过邮件提醒
	HTML_TASK_NO_PASS("html_task_no_pass.vm"),
	TASK_NO_PASS("task_no_pass.vm"),
	//任务废弃邮件提醒
	HTML_TASK_ABOLISH("html_task_abolish.vm"),
	TASK_ABOLISH("task_abolish.vm");
	
	private TemplateType(String path) {
		this.path = path;
	}
	
	private String path;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
