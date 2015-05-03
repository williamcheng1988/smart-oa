package com.chz.smartoa.task.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chz.smartoa.common.base.BaseDomain;

public class ReProcdef extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 流程ID
	 */
	private String procdef_id_;
	/**
	 * 流程名称
	 */
	private String name_;
	/**
	 * 流程版本号
	 */
	private String version_;
	/**
	 * 完成提醒：0：不提醒 ，1：提醒
	 */
	private int complete_remind_;
	/**
	 * 关联审批（之前有审批过的，自动审批）0：不关联，1:关联
	 */
	private int uplink_;
	/**
	 * 负责人
	 */
	private String manager_;
	/**
	 * 启用优先级：0：不启用，1：启用（0：一般，1：高，2：紧急）
	 */
	private int important_grade_;
	/**
	 * 流程状态:0：初始化 1：启用 2：挂起 3:删除
	 */
	private int procdef_status_;
	/**
	 * 创建时间
	 */
	private String  create_time_;
	
	private List<ReConf> reConfs;

	public String getProcdef_id_() {
		return procdef_id_;
	}

	public void setProcdef_id_(String procdef_id_) {
		this.procdef_id_ = procdef_id_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getVersion_() {
		return version_;
	}

	public void setVersion_(String version_) {
		this.version_ = version_;
	}

	public int getComplete_remind_() {
		return complete_remind_;
	}

	public void setComplete_remind_(int complete_remind_) {
		this.complete_remind_ = complete_remind_;
	}

	public int getUplink_() {
		return uplink_;
	}

	public void setUplink_(int uplink_) {
		this.uplink_ = uplink_;
	}

	public String getManager_() {
		return manager_;
	}

	public void setManager_(String manager_) {
		if(manager_ != null){
			this.manager_ =  replaceBlank(manager_);
		}else{
			this.manager_ = manager_;
		}
	}

	public int getImportant_grade_() {
		return important_grade_;
	}

	public void setImportant_grade_(int important_grade_) {
		this.important_grade_ = important_grade_;
	}

	public String getCreate_time_() {
		return create_time_;
	}

	public void setCreate_time_(String create_time_) {
		this.create_time_ = create_time_;
	}

	public List<ReConf> getReConfs() {
		return reConfs;
	}

	public void setReConfs(List<ReConf> reConfs) {
		this.reConfs = reConfs;
	}

	public int getProcdef_status_() {
		return procdef_status_;
	}

	public int getOpert_status_() {
		return procdef_status_;
	}
	public void setProcdef_status_(int procdef_status_) {
		this.procdef_status_ = procdef_status_;
	}
	
	private String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

	@Override
	public String toString() {
		return "ReProcdef [procdef_id_=" + procdef_id_ + ", name_=" + name_
				+ ", version_=" + version_ + ", complete_remind_="
				+ complete_remind_ + ", uplink_=" + uplink_ + ", manager_="
				+ manager_ + ", important_grade_=" + important_grade_
				+ ", procdef_status_=" + procdef_status_ + ", create_time_="
				+ create_time_ + ", reConfs=" + reConfs + "]";
	}
}
