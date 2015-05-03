package com.chz.smartoa.fileUpload.dao;

import java.util.List;

import com.chz.smartoa.fileUpload.pojo.RoleFiletype;

public interface RoleFiletypeDao {

	public List<String> getListByRole(RoleFiletype roleFile);
	
	public List<String> getListByRoleId(RoleFiletype roleFile);
	
	public void insertRoleFile(RoleFiletype roleFile);
	
	// 根据是否可看进行批量删除指定角色的类型权限
	public void batchDeleteByIsSee(RoleFiletype roleFile);
	
}
