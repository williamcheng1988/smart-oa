package com.chz.smartoa.fileUpload.util;

import java.util.Date;
import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;

public class FileQuery extends BaseDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 页面查询帮助字段
	 */
	public String queryName;
	public Date startDt;
	public Date endDt;
	public Integer fileTypeId;
	public String menuType;
	public Integer isValid;
	
	private String loginName;
	private List<String> projectIds;
	private List<String> fileIds;
	private Integer dicType; 
	private List<String> allowFileTypes;
	private List<String> unAllowFileTypes;
	private String createUser;
	private String selectType;
	
	private String groupId;
	private String fileStatus;   // 0:暂存,1:审批中2:审批通过,3:审批不通过,4:任务作废
	
	
	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Integer getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<String> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	public Integer getDicType() {
		return dicType;
	}
	public void setDicType(Integer dicType) {
		this.dicType = dicType;
	}

	public List<String> getAllowFileTypes() {
		return allowFileTypes;
	}

	public void setAllowFileTypes(List<String> allowFileTypes) {
		this.allowFileTypes = allowFileTypes;
	}

	public List<String> getUnAllowFileTypes() {
		return unAllowFileTypes;
	}

	public void setUnAllowFileTypes(List<String> unAllowFileTypes) {
		this.unAllowFileTypes = unAllowFileTypes;
	}

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

}
