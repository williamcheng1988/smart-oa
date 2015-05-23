package com.chz.smartoa.system.pojo;

import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;

public class WorkPlan extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date workDate;
	private String workTime;
	private String workDesc;
	private String noticeType;   // 通知类型   1：消息通知    2：邮件通知
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	private String noticeId;   
	
	
	private String startDt;
	private String endDt;
	private String selectDate;
	
	private String selectHour;
	private String selectMin;
	private String msg;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getSelectHour() {
		return selectHour;
	}
	public void setSelectHour(String selectHour) {
		this.selectHour = selectHour;
	}
	public String getSelectMin() {
		return selectMin;
	}
	public void setSelectMin(String selectMin) {
		this.selectMin = selectMin;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
