package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.exception.DepartmentNotFoundException;
import com.chz.smartoa.system.exception.StaffNotFoundException;
import com.chz.smartoa.system.pojo.Role;

public interface RoleBiz {
	Role findRole(String id);
	Role findRoleByName(String name);
	List<Role> listRole(Role role);
	List<Role> listRole(Role role, List<Role> includeRoles);	
	List<Role> listStaffRole(String staffId);
	List<Role> listDepartmentRole(String departmentId);	
	int listRoleCount(Role role);	
	String insertRole(Role role, String[] operationIds);
	void updateRole(Role role, String[] operationIds);
	void updateStaffRole(String staffId, String[] roleIds) throws StaffNotFoundException;	
	void updateDepartmentRole(String departmentId, String[] roleIds) throws DepartmentNotFoundException;	
	void deleteRole(String[] ids);
}
