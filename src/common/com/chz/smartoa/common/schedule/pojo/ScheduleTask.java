package com.chz.smartoa.common.schedule.pojo;

import java.io.Serializable;

public class ScheduleTask implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 任务ID
	 */
	private String   id;
	
	/**
	 * 任务名称
	 */
	private String task_name=null;
	
	/**
	 * 任务说明
	 */
	private String task_desc=null;
	
	/**
	 * 模块ID
	 */
	private String group_id =null;
	
	/**
	 * 模块名称
	 */
	private String group_name=null;
	
	/**
	 * 表达式
	 */
	private String express=null;
	
	/**
	 * 处理类
	 */
	private String handler_class=null;
	
	/**
	 * 参数
	 */
	private String parameters = null;
	
	/**
	 * 状态
	 */
	private long status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getTask_desc() {
		return task_desc;
	}
	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}

	public String getHandler_class() {
		return handler_class;
	}
	public void setHandler_class(String handler_class) {
		this.handler_class = handler_class;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
		
}
