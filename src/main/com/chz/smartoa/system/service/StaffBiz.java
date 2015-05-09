package com.chz.smartoa.system.service;

import java.sql.SQLException;
import java.util.List;

import com.chz.smartoa.system.exception.PasswordMustChangeException;
import com.chz.smartoa.system.exception.StaffLockException;
import com.chz.smartoa.system.exception.StaffPasswordErrorException;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;

public interface StaffBiz {
	
	Staff login(String loginName, String password)throws StaffPasswordErrorException, StaffLockException,PasswordMustChangeException;

	void changePassword(String loginName, String oldPassword, String newPassword)throws StaffPasswordErrorException;

	Staff findStaffByLoginName(String name);

	List<Staff> listStaff(Staff staff);
	
	List<Staff> listAllStaffs(Staff staff);
	
	List<Staff> listStaff(String loginName);
	
	List<String> listResourceIds(String loginName);

	int listStaffCount(Staff staff);

	String insertStaff(Staff staff,String roleIds);
	
	void updateStaff(Staff staff);

	void updateStaff(Staff staff,String roleIds);
	
	String resetPwds(String[] loginNames);

	void deleteStaffs(String[] loginNames);

	void assignRole(String id, String[] roleIds);

	int updatePwdErrCount(String loginName);

	List<Role> getRoles(String loginName);
	List<Role> getRoles(Role role);
	
	/**
	 * 新增项目角色 
	 * @param staffId 用户ID
	 * @param roleId 角色ID
	 * @param projectId 项目ID
	 */
	void insertStaffRole(String staffId,String roleId,String projectId);
	
	/**
	 * 通过角色名称查询角色ID
	 * @param roleName
	 * @return
	 */
	String getRoleName(String roleName);
	
	/**
	 * 删除指定项目用户角色关联关系
	 * @param projectId
	 * @return
	 */
	Integer deleteStaffRoleByProjectId(String projectId);
	
	/**
	 * 查询查看的项目全部数据的项目ID
	 * @param loginName
	 * @return list project id
	 */
	List<String> getOnwerProjectId(String loginName) throws SQLException;
	
	/**
	 * 查询是否具有查看所有数据权限
	 * @param loginName
	 * @return 1:可查看所有数据，0：不可查看所有数据
	 */
	int getHasAllDataPermission(String loginName) throws SQLException;
}
