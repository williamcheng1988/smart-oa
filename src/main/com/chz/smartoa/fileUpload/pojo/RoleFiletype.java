package com.chz.smartoa.fileUpload.pojo;

import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;


/**
 * 角色对应特定类型附件权限设置表
 * @author Zhao
 *
 */
public class RoleFiletype extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private Integer roleId;    // 角色ID
	
	private String fileId;     // 文件类型
	
	private Integer isSee;     // 是否可看( 可看:1        不可看：2 )
	
	
	private List<String> roleIds;
	
	

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getIsSee() {
		return isSee;
	}

	public void setIsSee(Integer isSee) {
		this.isSee = isSee;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	

}
