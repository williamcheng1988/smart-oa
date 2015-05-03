package com.chz.smartoa.task.service;

import java.util.List;
import java.util.Map;

import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.pojo.RuTaskVo;

/**
 * 任务查询接口
 * @author wesson
 */
public interface TaskService {
	
	/**
	 * 查询草稿
	 * @param userName
	 * @return 草稿列表
	 */
	List<GeExecution> listDrafts(String userName);
	
	/**
	 * 查询用户待办任务
	 * @param userName
	 * @return 待办任务列表
	 */
	List<RuTaskVo> listTodoTask(String userName);
	
	
	/**
	 * 查询用户待办任务
	 * @param userName 用户名称 
	 * @param sort 排序字段
	 * @param order 升序|降序
	 * @return 待办任务列表
	 */
	List<RuTaskVo> listTodoTask(String userName,String sort,String order);
	
	/**
	 * 查询用户待办任务条数
	 * @param userName
	 * @return 待办任务条数
	 */
	int listTodoTaskCount(String userName);
	
	/**
	 * 查看任务实例
	 * @param executionId 实例ID
	 * @return 任务实例
	 */
	Map<String,Object> getTaskExecution(String executionId);
	
	/**
	 * 查询用户待办任务属性
	 * @param taskId
	 * @param user 任务所属者ID
	 * @return 待办任务
	 */
	Map<String,Object> getTodoTask(String taskId,String user);
	
	/**
	 * 获取下一个审批节点
	 * @param executionId
	 * @return SortNum
	 */
	Integer getNextSortNum(String executionId);
	
	/**
	 * 查询待办任务
	 * @param taskId
	 * @return
	 */
	RuTask getRutask(String taskId);
	
	/**
	 * 是否流转至下一步
	 * @param executionId
	 * @return
	 */
	boolean getIsNext(String executionId);
	/**
	 * 查询流程实例
	 * @param executionId
	 * @return
	 */
	GeExecution getGeExecution(String executionId);
	
}
