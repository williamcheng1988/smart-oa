package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.exception.DepartmentNotFoundException;
import com.chz.smartoa.system.exception.StaffNotFoundException;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.RoleBiz;

public class RoleBizImpl implements RoleBiz {
	private RoleDao roleDao;
	private StaffDao staffDao;
	private DepartmentDao departmentDao;	
	private OperateLogBiz operateLogBiz;
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void deleteRole(String[] ids) {
		if (ids == null) {
			return;
		}
		// 删除相应的角色
		for (int i = 0; i < ids.length; i++) {
			Role role = roleDao.findRole(ids[i].trim());
			
			if (role != null) {
				roleDao.deleteRole(ids[i].trim());
				// 写业务日志
				operateLogBiz.info(OperateLogType.ROLE_MANAGE, role.getRoleId(),role.getRoleName(), "删除成功");
			}
		}
	}

	public Role findRole(String id) {
		Role role = roleDao.findRole(id);
		return role;
	}

	public Role findRoleByName(String name) {
		Role role = roleDao.findRoleByName(name);
		return role;
	}

	public String insertRole(Role role, String[] operationIds) {
		String roleId = roleDao.insertRole(role, operationIds);
		return roleId;
	}

	public List<Role> listRole(Role role) {
		List<Role> list = roleDao.listRole(role);
		return list;
	}
	
	public List<Role> listRole(Role role, List<Role> includeRoles) {
		List<Role> list = roleDao.listRole(role);
		
		// 对包括的角色设置标置
//		if (includeRoles != null && list != null) {
//			Map includeMap = new HashMap();
//			for (Iterator it = includeRoles.iterator(); it.hasNext();) {
//				Role tmpRole = (Role)it.next();
//				includeMap.put(tmpRole.getRoleId(), Role.CHECKED);
//			}
//			
//			for (Iterator it2 = list.iterator(); it2.hasNext();) {
//				Role tmpRole2 = (Role)it2.next();
//				if (includeMap.get(tmpRole2.getRoleId()) != null) {
//					tmpRole2.setField(Role.CHECKED, Role.CHECKED);
//				}
//			}			
//		}
		
		return list;
	}	
	
	public List<Role> listStaffRole(String loginName) {
		List<Role> list = roleDao.listStaffRole(loginName);
		return list;
	}
	
	public List<Role> listDepartmentRole(String departmentId) {
		List<Role> list = roleDao.listDepartmentRole(departmentId);
		return list;
	}	

	public int listRoleCount(Role role) {
		int count = roleDao.listRoleCount(role);
		return count;
	}

	public void updateRole(Role role, String[] operationIds) {
		roleDao.updateRole(role, operationIds);
	}

	public void updateStaffRole(String loginName, String[] roleIds) throws StaffNotFoundException{
		Staff staff = staffDao.findStaffByLoginName(loginName);
		if (loginName == null) {
			throw new StaffNotFoundException("成员不存在");
		}
		roleDao.updateStaffRole(loginName, roleIds);
		// 写业务日志
		operateLogBiz.info(OperateLogType.ROLE_MANAGE, staff.getLoginName(), staff.getLoginName(), "赋予角色成功："+roleIds);		
	}

	public void updateDepartmentRole(String departmentId, String[] roleIds) throws DepartmentNotFoundException{
		Department department = departmentDao.findDepartment(departmentId);
		if (departmentId == null) {
			throw new DepartmentNotFoundException("组织不存在");
		}
		
		roleDao.updateDepartmentRole(departmentId, roleIds);
		
		// 写业务日志
		operateLogBiz.info(OperateLogType.ROLE_MANAGE, 
				department.getDepartmentId(), 
				department.getDepartmentName(), "赋予角色成功："+roleIds);		
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}
}
