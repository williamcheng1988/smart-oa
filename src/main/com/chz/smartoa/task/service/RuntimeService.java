package com.chz.smartoa.task.service;

import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuConf;
import com.chz.smartoa.task.pojo.RuTask;

/**
 * 负责启动一个流程定义的新实例
 * 也能查询流程实例和执行
 * @author wesson
 */
public interface RuntimeService{
	/**
	 * 新增流程实例
	 * @param procdefId 流程定义
	 * @param businessKey 业务ID
	 * @param businessTitle 业务标题
	 * @param templateId 模板ID
	 * @param projectId 项目ID
	 * @param desc 备注
	 * @param priority 紧急程度
	 * @param status 提交状态：0：暂存;1:提交
	 * @param executionId 提交状态：0：暂存;1:提交
	 * @return
	 */
	String insertGeExcution(String procdefId, String businessKey,String businessTitle,String templateId,String projectId,String desc,int priority,int status,String executionId);
	/**
	 * 修改流程实例projectId
	 * @param executionId 实例ID
	 * @param businessTitle 业务标题
	 * @param projectId 项目ID
	 * @param desc 备注
	 * @return 
	 */
	Integer updateGeExcution(String executionId,String businessTitle,String projectId,String desc,int priority);
	/**
	 * 新增待办任务
	 * @param ruTask
	 * @return
	 */
	String insertRuTasks(RuTask ruTask);
	/**
	 * 新增待办任务
	 * @param ruconf
	 * @param executionId
	 * @param upLink
	 * @return
	 */
	int insertRuTasks(RuConf ruconf,GeExecution execution,int upLink);
	/**
	 * 删除单条待办任务
	 * @param taskId 任务ID
	 */
	void deleteRuTaskByTaskId(String taskId);
	/**
	 * 删除指定任务指定节点的所有任务（应用场景：审批异或）
	 * @param confId
	 */
	void deleteRuTaskByConfId(String ExecutionId,Integer confId);
	/**
	 * 删除当前步骤的所有任务（应用场景：跳过当前步骤）
	 * @param ExecutionId 
	 */
	void deleteRuTaskByTaskNum(String ExecutionId);
	/**
	 * 删除指定任务指定节点的所有任务（应用场景：退回修改、不通过、跳过、废弃任务）
	 * @param confId
	 */
	void deleteRuTaskByExecutionId(String ExecutionId);
	/**
	 * 委托  处理
	 * 委托与转办的区别在于，委托有委托记录
	 * @param taskId 任务Id
	 * @param fromUser 委托人
	 * @param toUser 被委托人
	 */
	void delegation(RuTask ruTask,String toUser);
	/**
	 * 委托  所有任务
	 * @param fromUser 委托人
	 * @param toUser 被委托人
	 */
	void delegation(String fromUser,String toUser);
	/**
	 * 委托  撤销
	 * @param delegationId 委托ID
	 */
	void delegationCancel(String delegationId);
	
	/**
	 * 转办 处理
	 * @param taskId 任务Id
	 * @param toUser 被转办人
	 */
	void toTurn(String taskId,String toUser);
	/**
	 * 征询 处理
	 * @param ruTask 任务
	 * @param toUser 被征询人
	 */
	void toConsult(RuTask ruTask,String toUser);
	
	/**
	 * 被征询人完成征询
	 * @param fromUser 任务
	 */
	void doneConsult(RuTask ruTask);
	
	/**
	 * 结束流程
	 * @param executionId
	 * @param status 0:审批中,1：审批通过，2：审批不通过，5：废除
	 */
	void completeTask(String executionId,int status);
	/**
	 * 更新流程当前处理节点
	 * @param executionId
	 * @param sortNum
	 */
	void updateGeExecutionTaskNum(String executionId,int sortNum);
	/**
	 * 重置流程实例
	 * @param executionId
	 */
	void resetGeExecution(String executionId);
}
