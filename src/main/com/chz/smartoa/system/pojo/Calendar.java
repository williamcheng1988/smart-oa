package com.chz.smartoa.system.pojo;

import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;

public class Calendar extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date dateSet;
	private Integer holidayType;  // 1：国家法定假日  2：周末休息日    3：公司福利假日
	private String holidayDesc;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
	
	private String startDt;
	private String endDt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateSet() {
		return dateSet;
	}
	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
	public Integer getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(Integer holidayType) {
		this.holidayType = holidayType;
	}
	public String getHolidayDesc() {
		return holidayDesc;
	}
	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
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

}
