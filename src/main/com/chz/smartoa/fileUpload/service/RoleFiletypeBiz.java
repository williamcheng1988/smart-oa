package com.chz.smartoa.fileUpload.service;

import java.util.List;

import com.chz.smartoa.fileUpload.pojo.RoleFiletype;

public interface RoleFiletypeBiz {

	// 根据组合条件查看list数据
	public List<String> getListByRole(RoleFiletype roleFile,String loginName);
	
	public List<String> getListByRole(RoleFiletype roleFile);
	
	// 批量保存指定角色的可看或不可看的文件类型
	public void batchSaveRoleFile(String[]fileTypes,Integer isSee,Integer roleId);
	
	// 根据是否可看进行批量删除指定角色的类型权限
	public void batchDeleteByIsSee(RoleFiletype roleFile);
	
	// 批量更新文件角色权限
	public void batchSaveUpdate(String[]fileTypes,RoleFiletype roleFile);
	
}
