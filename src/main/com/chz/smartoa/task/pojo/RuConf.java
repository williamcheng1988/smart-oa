package com.chz.smartoa.task.pojo;

import java.io.Serializable;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 运行中的流程步骤
 */
public class RuConf extends BaseDomain implements Serializable{
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
	 * 实例ID
	 */
	protected String execution_id_;
	/**
	 * 任务描述
	 */
	protected String task_desc_;
	/**
	 * 处理动作：1：审批【并行】，2：审批【异或】，3：阅处，4：传阅
	 */
	protected int action_type_;
	/**
	 * 处理对象来源（适用场景：指定部门岗位审批，如果此值为空就是相对岗位审批）
	 */
	protected String action_obj_src_;
	/**
	 * 处理对象
	 */
	protected String action_obj_;
	/**
	 * 处理对象类型：1：个人，2：角色，3：岗位
	 */
	protected int action_obj_type_;
	/**
	 * 转办：0：不转办，1：可转办
	 */
	protected int is_turn_;
	/**
	 * 征询：0：不征询，1：可征询
	 */
	protected int is_ask_;
	/**
	 * 是否允许流程变更:0：不允许，1：允许
	 */
	protected int is_modify_;
	/**
	 * 时效（天） 0代表无限制
	 */
	protected int expiry_days_;
	/**
	 * 任务到达邮件提醒：0：不提醒，1：提醒
	 */
	protected int arrive_remind_;
	/**
	 * 任务超时邮件提醒：0：不提醒，1：提醒
	 */
	protected int expiry_remind_;
	/**
	 * 模板ID
	 */
	protected String template_id_;
	
	/**
	 * 处理对象名称
	 */
	protected String action_obj_name_;
	/**
	 * 是否允许修改
	 */
	private int is_edit_;
	
	public int getConf_id_() {
		return conf_id_;
	}
	public void setConf_id_(int conf_id_) {
		this.conf_id_ = conf_id_;
	}
	public String getExecution_id_() {
		return execution_id_;
	}
	public void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}
	public String getTask_desc_() {
		return task_desc_;
	}
	public void setTask_desc_(String task_desc_) {
		this.task_desc_ = task_desc_;
	}
	public int getAction_type_() {
		return action_type_;
	}
	public void setAction_type_(int action_type_) {
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
	public String getAction_obj_src_() {
		return action_obj_src_;
	}
	public void setAction_obj_src_(String action_obj_src_) {
		this.action_obj_src_ = action_obj_src_;
	}
	public int getIs_turn_() {
		return is_turn_;
	}
	public void setIs_turn_(int is_turn_) {
		this.is_turn_ = is_turn_;
	}
	public int getIs_ask_() {
		return is_ask_;
	}
	public void setIs_ask_(int is_ask_) {
		this.is_ask_ = is_ask_;
	}
	public int getIs_modify_() {
		return is_modify_;
	}
	public void setIs_modify_(int is_modify_) {
		this.is_modify_ = is_modify_;
	}
	public int getExpiry_days_() {
		return expiry_days_;
	}
	public void setExpiry_days_(int expiry_days_) {
		this.expiry_days_ = expiry_days_;
	}
	public int getArrive_remind_() {
		return arrive_remind_;
	}
	public void setArrive_remind_(int arrive_remind_) {
		this.arrive_remind_ = arrive_remind_;
	}
	public int getExpiry_remind_() {
		return expiry_remind_;
	}
	public void setExpiry_remind_(int expiry_remind_) {
		this.expiry_remind_ = expiry_remind_;
	}
	public String getTemplate_id_() {
		return template_id_;
	}
	public void setTemplate_id_(String template_id_) {
		this.template_id_ = template_id_;
	}
	public int getSort_num_() {
		return sort_num_;
	}
	public void setSort_num_(int sort_num_) {
		this.sort_num_ = sort_num_;
	}
	public String getAction_obj_name_() {
		return action_obj_name_;
	}
	public void setAction_obj_name_(String action_obj_name_) {
		this.action_obj_name_ = action_obj_name_;
	}
	public int getIs_edit_() {
		return is_edit_;
	}
	public void setIs_edit_(int is_edit_) {
		this.is_edit_ = is_edit_;
	}
	@Override
	public String toString() {
		return "RuConf [conf_id_=" + conf_id_ + ", sort_num_=" + sort_num_
				+ ", execution_id_=" + execution_id_ + ", task_desc_="
				+ task_desc_ + ", action_type_=" + action_type_
				+ ", action_obj_src_=" + action_obj_src_ + ", action_obj_="
				+ action_obj_ + ", action_obj_type_=" + action_obj_type_
				+ ", is_turn_=" + is_turn_ + ", is_ask_=" + is_ask_
				+ ", is_modify_=" + is_modify_ + ", expiry_days_="
				+ expiry_days_ + ", arrive_remind_=" + arrive_remind_
				+ ", expiry_remind_=" + expiry_remind_ + ", template_id_="
				+ template_id_ + ", action_obj_name_=" + action_obj_name_
				+ ", is_edit_=" + is_edit_ + "]";
	}
}
