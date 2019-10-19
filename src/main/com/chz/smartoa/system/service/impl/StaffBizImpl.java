package com.chz.smartoa.system.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.util.DigestUtils;

import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.exception.PasswordMustChangeException;
import com.chz.smartoa.system.exception.StaffLockException;
import com.chz.smartoa.system.exception.StaffLoginNameErrorException;
import com.chz.smartoa.system.exception.StaffPasswordErrorException;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.pojo.Permission;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.pojo.StaffRole;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.StaffBiz;

public class StaffBizImpl implements StaffBiz {
	/**
	 * logger.
	 */
	private static final Logger logger = Logger.getLogger(StaffBizImpl.class);
	public static final int PWD_LOCK_COUNT = 5; // 密码错误锁定次数

	StaffDao staffDao;
	DepartmentDao departmentDao;
	RoleDao roleDao;
	OperateLogBiz operateLogBiz;

	
	
	/**
	 * 登录.
	 */
	public Staff login(String loginName, String password)
			throws StaffLoginNameErrorException, StaffPasswordErrorException,
			StaffLockException, PasswordMustChangeException {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			logger.error("用户名或密码为空");
			throw new IllegalArgumentException("用户名或密码为空");
		}

		Staff staff = staffDao.findStaffByLoginName(loginName);
		if (staff == null) {
			logger.error("用户不存在");
			throw new StaffLoginNameErrorException("用户不存在");
		}

		// 用户被锁定
		if (!Staff.STAFF_STATUS_NORMAL.equals(staff.getStatus())) {
			logger.error("非正常用户状态:"+staff.getStatus());
			throw new StaffLockException(staff.getStatus(),"");
		}

		// md5加密后比较密码.
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!md5Password.equals(staff.getPassword())) {
			logger.error("密码不正确");
			throw new StaffPasswordErrorException("密码不正确");
		}

		// 登录成功，密码错误次数清零
		if (!StringUtils.isEmpty(staff.getPwdErrCount())&& !"0".equals(staff.getPwdErrCount())) {
			staff.setPwdErrCount("0");
			staffDao.updateStaff(staff);
		}
		return staff;
	}

	public List<Role> getRoles(String loginName) {
		return roleDao.listStaffRole(loginName);
	}
	
	public List<Role> getRoles(Role role) {
		return roleDao.listRole(role);
	}

	/**
	 * 修改密码.
	 */
	public void changePassword(String loginName, String oldPassword,
			String newPassword) throws StaffPasswordErrorException {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(oldPassword)
				|| StringUtils.isEmpty(newPassword)) {
			logger.error("输入参数为空");
			throw new IllegalArgumentException("输入参数为空");
		}

		Staff staff = staffDao.findStaffByLoginName(loginName);
		if (staff == null) {
			logger.error("用户不存在");
			throw new StaffPasswordErrorException("用户不存在");
		}

		// md5加密后比较密码.
		String md5Password = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
		if (!md5Password.equals(staff.getPassword())) {
			logger.error("原密码不正确");
			throw new StaffPasswordErrorException("原密码不正确");
		}

		// 修改密码.
		String newMd5Password = DigestUtils.md5DigestAsHex(newPassword
				.getBytes());
		Staff newStaff = new Staff();
		newStaff.setLoginName(staff.getLoginName());
		newStaff.setPassword(newMd5Password);
		if (Staff.STAFF_STATUS_INACTIVE.equals(staff.getStatus())) {
			newStaff.setStatus(Staff.STAFF_STATUS_NORMAL);
		}
		staffDao.updateStaff(newStaff);
	}

	public void assignRole(String loginName, String[] roleIds) {
		staffDao.assignRole(loginName, roleIds);
	}

	@Override
	public void deleteStaffs(String[] loginNames) {
		for (String loginName : loginNames) {
			// 删除用户
			staffDao.deleteStaff(loginName);
			// 写业务日志
			operateLogBiz.info(OperateLogType.STAFF_MANAGE, loginName, loginName, "注销成功");
		}
	}

	public Staff findStaffByLoginName(String loginName) {
		Staff staff = staffDao.findStaffByLoginName(loginName);
		// 查询用户所属组织
//		if (staff != null) {
//			Department department = departmentDao.findDepartment(staff.getDepartmentId());
//			staff.setDepartment(department);
//		}

		// 查询用户的相关角色列表
		if (staff != null) {
			List<Role> roles = roleDao.listStaffRole(staff.getLoginName());
			staff.setRoles(roles);
		}
		return staff;
	}
	
	@Override
	public void updateStaff(Staff staff) {
		try {
			staffDao.updateStaff(staff);
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	@Override
	public String resetPwds(String[] loginNames) {
		String result = "";
		try {
			for (String loginName : loginNames) {
				Staff staff = new Staff();
				staff.setLoginName(loginName);
				String newPwd = generatePwd();
				String md5Password = DigestUtils.md5DigestAsHex(newPwd.getBytes());
				staff.setPassword(md5Password);
				//更新密码入库
				staffDao.updateStaff(staff);
				result += loginName+":"+newPwd+"\n";
			}
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateStaff(Staff staff,String roleIds) {
		try {
			staffDao.updateStaff(staff);
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		if(roleIds != null){
			assignRole(staff.getLoginName(), roleIds.split(","));
		}
		// 写业务日志
		operateLogBiz.info(OperateLogType.STAFF_MANAGE, staff.getLoginName(), staff.getLoginName(), "修改成功");
	}

	public String insertStaff(Staff staff,String roleIds) {
		String md5Password = DigestUtils.md5DigestAsHex(staff.getPassword().getBytes());
		staff.setPassword(md5Password);
		String id = staffDao.insertStaff(staff);
		//插入角色
		if(roleIds!=null){
			staffDao.insertStaffRoles(id,roleIds.split(","));
		}
		// 写业务日志
		operateLogBiz.info(OperateLogType.STAFF_MANAGE, id,staff.getLoginName(),"新增成功");

		return id;
	}
	
	@Override
	public void insertStaffRole(String staffId, String roleId,
			String projectId) {
		StaffRole staffRole  = new StaffRole();
		staffRole.setStaffId(staffId);
		staffRole.setRoleId(roleId);
		staffRole.setProjectId(projectId);
		staffDao.insertStaffRole(staffRole);
	}

	/**
	 * 更新密码错误次数
	 */
	public int updatePwdErrCount(String loginName) {
		Staff staff = staffDao.findStaffByLoginName(loginName);

		int errorCount = 0;
		if (StringUtils.isEmpty(staff.getPwdErrCount())) {
			errorCount = 1;
		} else {
			errorCount = Integer.parseInt(staff.getPwdErrCount()) + 1;
		}
		if (errorCount >= PWD_LOCK_COUNT) {
			staff.setStatus(Staff.STAFF_STATUS_LOCK);
		}

		staff.setPwdErrCount(Integer.toString(errorCount));
		staffDao.updateStaff(staff);
		return Integer.valueOf(staff.getPwdErrCount());
	}
	
	@Override
	public String getRoleName(String roleName) {
		Role role =  roleDao.findRoleByName(roleName);
		if(role != null){
			return role.getRoleId();
		}
		return null;
	}
	
	@Override
	public Integer deleteStaffRoleByProjectId(String projectId) {
		Integer cnt = staffDao.deleteStaffRoleByProjectId(projectId);
		if(cnt > 0){
			operateLogBiz.info(OperateLogType.STAFF_ROLE_MANAGE, projectId, projectId, "删除"+cnt+"条数据！");
		}
		return cnt;
	}
	
	@Override
	public List<String> getOnwerProjectId(String loginName) throws SQLException {
		@SuppressWarnings("unchecked")
		List<String> projectIds = (List<String>) roleDao.queryForList("Staff_onwerProjectId",loginName);
		return projectIds;
	}
	
	@Override
	public int getHasAllDataPermission(String loginName) throws SQLException {
		int cnt = (Integer) roleDao.queryForObject("Staff_isAllDataPermission", loginName);
		if(cnt > 0){
			return 1;
		}
		return 0;
	}
	
	
	public List<String> listResourceIds(String loginName) {
		return staffDao.listResourceIds(loginName);
	};

	public List<Staff> listStaff(Staff staff) {
		List<Staff> list = staffDao.listStaff(staff);
		return list;
	}
	
	public List<Staff> listAllStaffs(Staff staff){
		List<Staff> list = staffDao.listAllStaffs(staff);
		return list;
	}
	
	public List<Staff> listStaff(String loginName) {
		List<Staff> list = staffDao.listStaff(loginName);
		return list;
	}

	public int listStaffCount(Staff staff) {
		int count = staffDao.listStaffCount(staff);
		return count;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}
	
	private String generatePwd(){
		char[] en = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		int[] num ={0,1,2,3,4,5,6,7,8,9};
		String pwd = "";
		for (int i = 0; i < 6; i++) {
			if(i<3)
				pwd += String.valueOf(en[(int)(Math.random() * 52)]);
			else	
				pwd += String.valueOf(num[(int)(Math.random() * 10)]);
		}
		return pwd;	
	}
}
