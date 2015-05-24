package com.chz.smartoa.task.pojo;

import java.io.Serializable;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * @author wesson
 * @date 2015-05-24
 */
public class HiTaskAssignee  extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	public HiTaskAssignee() {
		
	}
	
	public HiTaskAssignee(String execution_id_, String assignee_, String conf_id_) {
		super();
		this.execution_id_ = execution_id_;
		this.assignee_ = assignee_;
		this.conf_id_ = conf_id_;
	}

	//实例ID
	private String execution_id_;
	//用户ID
	private String assignee_;
	//节点ID
	private String conf_id_;
	
	public String getExecution_id_() {
		return execution_id_;
	}
	public void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}
	public String getAssignee_() {
		return assignee_;
	}
	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}
	public String getConf_id_() {
		return conf_id_;
	}
	public void setConf_id_(String conf_id_) {
		this.conf_id_ = conf_id_;
	}
}
