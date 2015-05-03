package com.chz.smartoa.task.action;

import java.io.Serializable;

/**
 * 流程定义配置明细
 */
public class ReConfVo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID 自增长
	 */
	protected int conf_id_;
	/**
	 * 序号
	 */
	protected int sort_num_;
	/**
	 * 流程ID
	 */
	protected String procdef_id_;
	/**
	 * 任务描述
	 */
	protected String task_desc_;
	/**
	 * 处理动作：1：审批【并行】，2：审批【异或】，3：阅处，4：传阅
	 */
	protected String action_type_;
	/**
	 * 处理对象
	 */
	protected String action_obj_;
	/**
	 * 处理对象类型：1：个人，2：角色
	 */
	protected int action_obj_type_;
	/**
	 * 转办：0：不转办，1：可转办
	 */
	protected String is_turn_;
	/**
	 * 征询：0：不征询，1：可征询
	 */
	protected String is_ask_;
	/**
	 * 时效（天） 0代表无限制
	 */
	protected int expiry_days_;
	/**
	 * 任务到达邮件提醒：0：不提醒，1：提醒
	 */
	protected String arrive_remind_;
	/**
	 * 任务超时邮件提醒：0：不提醒，1：提醒
	 */
	protected String expiry_remind_;
	/**
	 * 模板ID
	 */
	protected String template_id_;
	
	/**
	 * 处理对象名称
	 */
	protected String action_obj_name_;

	public int getConf_id_() {
		return conf_id_;
	}

	public void setConf_id_(int conf_id_) {
		this.conf_id_ = conf_id_;
	}

	public int getSort_num_() {
		return sort_num_;
	}

	public void setSort_num_(int sort_num_) {
		this.sort_num_ = sort_num_;
	}

	public String getProcdef_id_() {
		return procdef_id_;
	}

	public void setProcdef_id_(String procdef_id_) {
		this.procdef_id_ = procdef_id_;
	}

	public String getTask_desc_() {
		return task_desc_;
	}

	public void setTask_desc_(String task_desc_) {
		this.task_desc_ = task_desc_;
	}

	public String getAction_type_() {
		if("1".equals(action_type_)){
			return "审批【并行】";
		}else if ("2".equals(action_type_)) {
			return "审批【异或】";
		}else if ("3".equals(action_type_)) {
			return "阅处";
		}else if ("4".equals(action_type_)) {
			return "传阅";
		}
		return "";
	}

	public void setAction_type_(String action_type_) {
		this.action_type_ = action_type_;
	}

	public String getAction_obj_() {
		return action_obj_;
	}

	public void setAction_obj_(String action_obj_) {
		this.action_obj_ = action_obj_;
	}

	public int getAction_obj_type_() {
		return action_obj_type_;
	}

	public void setAction_obj_type_(int action_obj_type_) {
		this.action_obj_type_ = action_obj_type_;
	}

	public String getIs_turn_() {
		return is_turn_;
	}

	public void setIs_turn_(String is_turn_) {
		this.is_turn_ = is_turn_;
	}

	public String getIs_ask_() {
		return is_ask_;
	}

	public void setIs_ask_(String is_ask_) {
		this.is_ask_ = is_ask_;
	}

	public int getExpiry_days_() {
		return expiry_days_;
	}

	public void setExpiry_days_(int expiry_days_) {
		this.expiry_days_ = expiry_days_;
	}

	public String getArrive_remind_() {
		return arrive_remind_;
	}

	public void setArrive_remind_(String arrive_remind_) {
		this.arrive_remind_ = arrive_remind_;
	}

	public String getExpiry_remind_() {
		return expiry_remind_;
	}

	public void setExpiry_remind_(String expiry_remind_) {
		this.expiry_remind_ = expiry_remind_;
	}

	public String getTemplate_id_() {
		return template_id_;
	}

	public void setTemplate_id_(String template_id_) {
		this.template_id_ = template_id_;
	}

	public String getAction_obj_name_() {
		return action_obj_name_;
	}

	public void setAction_obj_name_(String action_obj_name_) {
		this.action_obj_name_ = action_obj_name_;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
