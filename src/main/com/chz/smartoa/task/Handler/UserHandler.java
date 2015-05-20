package com.chz.smartoa.task.Handler;

import java.util.List;

import com.chz.smartoa.task.exception.NotFoundUserByPostException;
import com.chz.smartoa.task.exception.NotFoundUserByRoleException;

/**
 * 流程用户接口
 * @author wesson
 */
public interface UserHandler {
	/**
	 * 查询角色对应用户
	 * @param roleId 角色ID
	 * @param projectId 项目ID
	 * @return
	 */
	List<String> listUsersByRole(String roleId,String projectId) throws NullPointerException,NotFoundUserByRoleException;
	
	/**
	 * 查询岗位对应用户
	 * @param onwerDeptId 发起人所在部门
	 * @param roleId 角色ID
	 * @param projectId 项目ID
	 * @return
	 */
	List<String> listUsersByPost(String onwerDeptId,String postId,String deptId) throws NullPointerException,NotFoundUserByPostException;
}
