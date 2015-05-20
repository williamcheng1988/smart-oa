package com.chz.smartoa.system.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.dao.BaseDao;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.StaffRole;
import com.chz.smartoa.task.Handler.UserHandler;
import com.chz.smartoa.task.enumcode.TaskError;
import com.chz.smartoa.task.exception.NotFoundUserByPostException;
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
	private BaseDao baseDao;

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
	
	@Override
	public List<String> listUsersByPost(String onwerDeptId,String postId,String deptId) throws NullPointerException,NotFoundUserByPostException {
		logger.debug("进入UserServiceImpl.listUsersByPost( "+onwerDeptId+","+postId+","+deptId+")");
		
		String targetDeptId = deptId;
		if(StringUtils.isEmpty(targetDeptId)){//指定部门为空则取相对部门:指定部门优先于相对部门
			targetDeptId = onwerDeptId;
		}
		
		if (StringUtils.isEmpty(targetDeptId)) {
			throw new NotFoundUserByPostException(TaskError.NotFoundUserByPost.getVal(),"获取部门ID为空：未配置下一步流程的处理部门或发起人部门为空！");
		}
		//查询部门岗位对应用户,指定部门找不到就去其上级部门找
		String staffIds = null;
		try {
			do {
				Map<String, String> params = new HashMap<String, String>();
				params.put("deptId", targetDeptId);
				params.put("postId", postId);
				staffIds = String.valueOf(baseDao.queryForObject("TaskUser_deptPostStaffIds", params));
				if(StringUtils.isEmpty(staffIds)){//查询上级部门
					targetDeptId = String.valueOf(baseDao.queryForObject("TaskUser_parentDeptId",targetDeptId));
				}
			} while (StringUtils.isEmpty(targetDeptId)||StringUtils.isNotEmpty(staffIds));
			
			if(StringUtils.isEmpty(staffIds)){//如果没有找到岗位配置，抛出异常
				String deptName = String.valueOf(baseDao.queryForObject("TaskUser_deptName", deptId));
				String postName = String.valueOf(baseDao.queryForObject("TaskUser_postName", postId));
				throw new NotFoundUserByPostException(TaskError.NotFoundUserByPost.getVal(),"尚未配置"+deptName+"的"+postName+"!");
			}
		} catch (Exception e) {
			throw new NotFoundUserByPostException(TaskError.NotFoundUserByPost.getVal(),"查询岗位SQL异常："+e.getMessage());
		}
		//返回岗位对应用户集合
		return Arrays.asList(staffIds.split(","));
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
