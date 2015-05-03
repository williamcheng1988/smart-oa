package com.chz.smartoa.fileUpload.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chz.smartoa.fileUpload.dao.RoleFiletypeDao;
import com.chz.smartoa.fileUpload.pojo.RoleFiletype;
import com.chz.smartoa.fileUpload.service.RoleFiletypeBiz;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.pojo.Role;

public class RoleFiletypeBizImpl implements RoleFiletypeBiz{
	
	private RoleFiletypeDao roleFiletypeDao;
	public RoleFiletypeDao getRoleFiletypeDao() {
		return roleFiletypeDao;
	}
	public void setRoleFiletypeDao(RoleFiletypeDao roleFiletypeDao) {
		this.roleFiletypeDao = roleFiletypeDao;
	}
	
	private RoleDao roleDao;
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}


	/**
	 *  根据组合条件查看list数据
	 */
	@Override
	public List<String> getListByRole(RoleFiletype roleFile,String loginName) {
		List<Role> roles = roleDao.listStaffRole(loginName);
		List<String> roleIds = null;
		if(roles != null && roles.size() >= 1){
			roleIds = new ArrayList<String>();
			for(int i=0;i<roles.size();i++){
				roleIds.add(roles.get(i).getRoleId());
			}
		}
		roleFile.setRoleIds(roleIds);
		return roleFiletypeDao.getListByRole(roleFile);
	}
	
	
	
	@Override
	public List<String> getListByRole(RoleFiletype roleFile) {
		return roleFiletypeDao.getListByRoleId(roleFile);
	}

	
	/**
	 * 批量保存指定角色的可看或不可看的文件类型
	 */
	@Override
	public void batchSaveRoleFile(String[]fileTypes,Integer isSee,Integer roleId){
		if(fileTypes != null && fileTypes.length >= 1){
			RoleFiletype roleFiletype = null;
			for(int i=0;i<fileTypes.length;i++){
				roleFiletype = new RoleFiletype();
				roleFiletype.setRoleId(roleId);
				roleFiletype.setIsSee(isSee);
				roleFiletype.setFileId(fileTypes[i]);
				roleFiletypeDao.insertRoleFile(roleFiletype);
			}
		}
	}

	
	/**
	 * 根据是否可看进行批量删除指定角色的类型权限
	 */
	@Override
	public void batchDeleteByIsSee(RoleFiletype roleFile) {
		roleFiletypeDao.batchDeleteByIsSee(roleFile);
	}
	
	
	@Override
	public void batchSaveUpdate(String[] fileTypes, RoleFiletype roleFile) {
		this.batchDeleteByIsSee(roleFile);
		this.batchSaveRoleFile(fileTypes, roleFile.getIsSee(), roleFile.getRoleId());
	}
	

}
