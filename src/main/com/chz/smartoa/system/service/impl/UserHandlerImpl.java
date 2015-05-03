package com.chz.smartoa.system.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.StaffRole;
import com.chz.smartoa.task.Handler.UserHandler;
import com.chz.smartoa.task.enumcode.TaskError;
import com.chz.smartoa.task.exception.NotFoundUserByRoleException;


/**
 * 流程用户相关接口
 * @author wesson
 *
 */
public class UserHandlerImpl implements UserHandler {
	
	  /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(UserHandlerImpl.class);
	
	private StaffDao staffDao;
	private RoleDao roleDao;

	@Override
	public List<String> listUsersByRole(String roleId, String projectId) throws NullPointerException,NotFoundUserByRoleException{
		logger.debug("进入UserServiceImpl.listUsersByRole( "+roleId+","+projectId+")");
		List<String> staffList;
		Role role = roleDao.findRole(roleId);
		StaffRole staffRole = new StaffRole();
		staffRole.setRoleId(roleId);
		if("2".equals(role.getRoleType())){//项目角色
			if(projectId ==null || "".equals(projectId))
				throw new NullPointerException("获取项目信息异常：项目ID为NULL!");
			staffRole.setProjectId(projectId);
		}
		staffList = staffDao.listStaffByRole(staffRole);
		if (staffList == null || staffList.size() == 0) {
			throw new NotFoundUserByRoleException(TaskError.NotFoundUserByRole.getVal(),"未找到\""+role.getRoleName()+"\"对应用户！");
		}
		return staffList;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
