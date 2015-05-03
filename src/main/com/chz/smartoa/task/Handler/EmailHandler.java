package com.chz.smartoa.task.Handler;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.email.EmailHelper;
import com.chz.smartoa.common.email.EmailMessage;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.service.RepositoryService;
import com.chz.smartoa.task.service.TaskService;

/**
 * 定时任务邮件处理类
 * 异步邮件发送
 * @author wesson
 */

public class EmailHandler {
	private static final Logger logger = Logger.getLogger(EmailHandler.class);
	private RepositoryService repositoryService;
	private TaskService taskService;
	
	/**
	 * 任务到达提醒
	 * @param user
	 * @param executionId
	 */
	public void sendArriveEmail(int isSend,String user,String executionId){
		try {
			EmailHelper.send(new EmailMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}
	}
	
	
	/**
	 * 任务到达提醒
	 * @param isSend 是否发送邮件：1,发送;0,不发送;
	 * @param user
	 * @param approveResult 审批意见
	 */
	public void sendArriveEmail(int isSend,String user,ApproveResult approveResult){
		try {
			EmailHelper.send(new EmailMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}
	}
	
	/**
	 * 任务返回给发起人
	 * @param executionId
	 * @param approveResult
	 */
	public void sendReturnToOwnerEmail(String executionId,ApproveResult approveResult){
//		GeExecution execution = taskService.getGeExecution(executionId);
//		int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
		int completeRemind  = 2;
		if(completeRemind == 1){
			try {
				EmailHelper.send(new EmailMessage());
			} catch (Exception e) {
				logger.error("发送邮件失败");
			}
		}
	}
	
	
	/**
	 * 任务超时提醒
	 * @param user
	 * @param executionId
	 */
	public void sendExpiryEmail(String user,String executionId){
		try {
			EmailHelper.send(new EmailMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}
	}
	
	/**
	 * 任务完成提醒
	 * @param executionId
	 */
	public void sendCompleteEmail(int isSend, GeExecution executionId) {
		if (isSend != 1) {
			return;
		}
		try {
			EmailHelper.send(new EmailMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}
	}
	
	/**
	 * 任务完成提醒
	 * @param executionId
	 * @param approveResult 审批意见
	 * 
	 */
	public void sendCompleteEmail(String executionId,ApproveResult approveResult){
//		GeExecution execution = taskService.getGeExecution(executionId);
//		int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
		int completeRemind  = 2;
		if(completeRemind == 1){
			try {
				EmailHelper.send(new EmailMessage());
			} catch (Exception e) {
				logger.error("发送邮件失败");
			}
		}
	}
	
	
	/**
	 * 任务废弃提醒
	 * @param execution
	 */
	public void sendAbolishEmail(GeExecution execution){
//		GeExecution execution = taskService.getGeExecution(executionId);
//		int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
		int completeRemind  = 2;
		if(completeRemind == 1){
			try {
				EmailHelper.send(new EmailMessage());
			} catch (Exception e) {
				logger.error("发送邮件失败");
			}
		}
	}
	
}
