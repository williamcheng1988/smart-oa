package com.chz.smartoa.system.pojo;

import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;

public class AfficheInfo extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String afficheTitle;
	private String afficheContent;
	private Integer afficheStatus;   // 1 生效  2 暂存   
	private Date startLifedate;      // 有效起始日期
	private Date endLifedate;        // 有效截止日期
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
	
	// 查询条件字段(辅助字段)
	private String startDate;
	private String endDate;
	private String updateDtStr;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAfficheTitle() {
		return afficheTitle;
	}
	public void setAfficheTitle(String afficheTitle) {
		this.afficheTitle = afficheTitle;
	}
	public String getAfficheContent() {
		return afficheContent;
	}
	public void setAfficheContent(String afficheContent) {
		this.afficheContent = afficheContent;
	}
	
	public Integer getAfficheStatus() {
		return afficheStatus;
	}
	public void setAfficheStatus(Integer afficheStatus) {
		this.afficheStatus = afficheStatus;
	}
	
	public Date getStartLifedate() {
		return startLifedate;
	}
	public void setStartLifedate(Date startLifedate) {
		this.startLifedate = startLifedate;
	}
	public Date getEndLifedate() {
		return endLifedate;
	}
	public void setEndLifedate(Date endLifedate) {
		this.endLifedate = endLifedate;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUpdateDtStr() {
		return updateDtStr;
	}
	public void setUpdateDtStr(String updateDtStr) {
		this.updateDtStr = updateDtStr;
	}

}
