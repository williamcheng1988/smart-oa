package com.chz.smartoa.task.service;

import java.util.Map;

import com.chz.smartoa.task.exception.TaskInvalidException;

/**
 * 流程统一处理接口
 * 发起、流转、委托、跳过、废除
 * @author wesson
 */
public interface FlowService {
	/**
	 * 打开流程填报页面
	 * @param templateId 模板Id
	 * @return
	 */
	Map<String, Object> getPage(String templateId) throws Exception ;
	
	/**
	 * 打开查询流程填报明细
	 * @param templateId 模板Id
	 * @param businessKey 业务主键
	 * @return
	 */
	Map<String, Object> getPage(String templateId,String businessKey) throws Exception ;
	
	/**
	 * 查询流程紧急程度
	 * @param templateId
	 * @return 启用优先级：0：不启用，1：启用
	 */
	int getImportantGrade(String templateId);
	
	/**
	 * 创建流程实例
	 * @param templateId 模板ID
	 * @param procdefName 流程名称
	 * @param businessTitle 任务标题
	 * @param desc 任务描述
	 * @param status
	 * @param priority 紧急程度（0：一般，1：高，2：紧急）
	 * @param params
	 */
	String createProcess(String templateId,String businessTitle,String desc,int status,int priority,String execution,Map<String, Object> params) throws Exception;
	
	/**
	 * 发起新流程
	 * @param procdefName 流程定义名称
	 * @param businessKey 业务ID
	 * @param businessTitle 业务主题
	 * @param templateId 模板ID
	 * @param projectId 项目ID
	 * @param priority 紧急程度（0：一般，1：高，2：紧急）
	 * @param executionId 不为空则为修改
	 * @return executionId
	 */
	String startProcess(String procdefName,String businessKey,String businessTitle,String templateId,String projectId,String desc_,int priority,int status,String executionId);
	/**
	 * 退回修改后重新提交
	 * @param taskId 待办任务Id
	 */
	public void startProcess(String taskId);
	/**
	 * 查看待办任务
	 * @param taskId
	 * @return
	 */
	Map<String, Object> findTodoTask(String taskId,String user);
	/**
	 * 流程审批（适用于“退回修改”后提交）
	 * @param taskId 审批任务ID
	 */
	void approveProcess(String taskId);
	/**
	 * 流程审批
	 * @param taskId 审批任务ID
	 * @param variables
	 */
	void approveProcess(String taskId,Map<String, Object> variables)throws TaskInvalidException,Exception;
	
	/**
	 * 流程委托
	 * @param taskId 审批任务ID
	 * @param toUser
	 */
	void delegationProcess(String taskId,String toUser);
	
	/**
	 * 跳过流程
	 * @param exectuionId 任务实例ID
	 */
	void skipProcess(String exectuionId);
	
	/**
	 * 废除流程
	 * @param exectuionId 任务实例ID 
	 */ 
	void abolishProcess(String exectuionId);
	
}
