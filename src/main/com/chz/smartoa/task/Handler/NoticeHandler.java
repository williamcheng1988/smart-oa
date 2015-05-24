package com.chz.smartoa.task.Handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.email.EmailHelper;
import com.chz.smartoa.common.email.EmailMessage;
import com.chz.smartoa.common.util.VelocityUtils;
import com.chz.smartoa.system.constant.TemplateType;
import com.chz.smartoa.system.pojo.Notice;
import com.chz.smartoa.system.service.NoticeBiz;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.service.RepositoryService;
import com.chz.smartoa.task.service.TaskService;

/**
 * 消息通知处理类
 * @author wesson
 */

public class NoticeHandler {
	private static final Logger logger = Logger.getLogger(NoticeHandler.class);
	
	private RepositoryService repositoryService;
	
	private TaskService taskService;
	
	private NoticeBiz noticeBiz;
	
	/**
	 * 任务到达提醒
	 * @param user
	 * @param executionId
	 */
	public void arriveNotice(final int isSend, final String user,final String executionId) {
		System.out.println("aa");
		new Thread() { //异步处理
			public void run() {
				try {
					Map<String, Object> task = taskService.getTaskExecution(executionId);
					Notice notice = new Notice();
					notice.setToPeople(user);
					notice.setTitle(String.valueOf(task.get("business_title_")));
					notice.setStation(0);
					if (isSend == 1) {
						notice.setEmail(0);
					}
					notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_ARRIVE, task));
					notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_ARRIVE, task));
					noticeBiz.insert(notice);
				} catch (Exception e) {
					logger.error("组装到达提醒失败："+e);
					System.out.println(e);
				}
			}
		}.start();
	}
	
	/**
	 * 任务退回给发起人
	 * @param executionId
	 * @param approveResult
	 */
	public void returnToOwnerNotice(final String executionId,final ApproveResult approveResult) {
		new Thread() { // 异步处理
			public void run() {
				GeExecution execution = taskService.getGeExecution(executionId);
				int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
					try {
						Notice notice = new Notice();
						notice.setToPeople(execution.getOwner());
						notice.setTitle(execution.getBusinessTitle());
						notice.setStation(0);
						if (completeRemind == 1) {
							notice.setEmail(0);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("execution", execution);
						params.put("approveResult", approveResult);
						notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_RETURN, params));
						notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_RETURN, params));
						noticeBiz.insert(notice);
					} catch (Exception e) {
						logger.error("发送邮件失败");
					}
			}
		}.start();
	}
	
	
	/**
	 * 任务超时提醒
	 * @param user
	 * @param executionId
	 */
	public void expiryNotice(String user,String executionId){
		try {
			EmailHelper.send(new EmailMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}
	}
	
	/**
	 * 审批通过提醒
	 * @param executionId
	 */
	public void completeNotice(final int isSend,final GeExecution execution) {
		
		new Thread() { //异步处理
			public void run() {
				try {
					Notice notice = new Notice();
					notice.setToPeople(execution.getOwner());
					notice.setTitle(execution.getBusinessTitle());
					notice.setStation(0);
					if (isSend == 1) {
						notice.setEmail(0);
					}
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("execution", execution);
					notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_COMPLETE, params));
					notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_COMPLETE, params));
					noticeBiz.insert(notice);
				} catch (Exception e) {
					logger.error("组装到达提醒失败："+e);
				}
			}
		}.start();
	}
	
	/**
	 * 审批不通过提醒
	 * @param executionId
	 * @param approveResult 审批意见
	 */
	public void completeNotice(final String executionId,final ApproveResult approveResult){
		new Thread() { // 异步处理
			public void run() {
				GeExecution execution = taskService.getGeExecution(executionId);
				int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
					try {
						Notice notice = new Notice();
						notice.setToPeople(execution.getOwner());
						notice.setTitle(execution.getBusinessTitle());
						notice.setStation(0);
						if (completeRemind == 1) {
							notice.setEmail(0);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("execution", execution);
						params.put("approveResult", approveResult);
						notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_NO_PASS, params));
						notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_NO_PASS, params));
						noticeBiz.insert(notice);
					} catch (Exception e) {
						logger.error("发送邮件失败");
					}
			}
		}.start();
	}
	
	/**
	 * 任务废弃提醒
	 * @param execution
	 */
	public void abolishNotice(final GeExecution execution){
		new Thread() { // 异步处理
			public void run() {
				int completeRemind = repositoryService.getCompleteRemind(execution.getProcdefId());
					try {
						Notice notice = new Notice();
						notice.setToPeople(execution.getOwner());
						notice.setTitle(execution.getBusinessTitle());
						notice.setStation(0);
						if (completeRemind == 1) {
							notice.setEmail(0);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("execution", execution);
						notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_ABOLISH, params));
						notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_ABOLISH, params));
						noticeBiz.insert(notice);
					} catch (Exception e) {
						logger.error("发送邮件失败");
					}
			}
		}.start();
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setNoticeBiz(NoticeBiz noticeBiz) {
		this.noticeBiz = noticeBiz;
	}
}
