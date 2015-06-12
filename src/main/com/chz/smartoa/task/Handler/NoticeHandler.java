package com.chz.smartoa.task.Handler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.email.EmailHelper;
import com.chz.smartoa.common.email.EmailMessage;
import com.chz.smartoa.common.service.BaseService;
import com.chz.smartoa.common.util.VelocityUtils;
import com.chz.smartoa.fileUpload.util.PerpertiesTool;
import com.chz.smartoa.system.constant.TemplateType;
import com.chz.smartoa.system.pojo.Notice;
import com.chz.smartoa.system.service.NoticeBiz;
import com.chz.smartoa.task.enumcode.RecodeType;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.service.HistoryService;
import com.chz.smartoa.task.service.RepositoryService;
import com.chz.smartoa.task.service.TaskService;

/**
 * 消息通知处理类
 * @author wesson
 */

public class NoticeHandler {
	private static final Logger logger = Logger.getLogger(NoticeHandler.class);
	
	private RepositoryService repositoryService;
	
	private NoticeBiz noticeBiz;
	
	private BaseService baseService;
	
	private HistoryService historyService;
	
	/**
	 * 任务到达提醒
	 * @param user
	 * @param executionId
	 */
	public void arriveNotice(final int isSend, final String user,String executionId) {
		final Map<String, Object> content = getExectionInfo(executionId);
		content.put("WWW_ADDRSS", PerpertiesTool.getPro("WWW_ADDRSS"));
		if(content != null){
			new Thread() { //异步处理
				public void run() {
					try {
						Notice notice = new Notice();
						notice.setToPeople(user);
						notice.setTitle(String.valueOf(content.get("business_title_")));
						notice.setStation(0);
						if (isSend == 1) {
							notice.setEmail(0);
						}
						notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_ARRIVE, content));
						notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_ARRIVE, content));
						noticeBiz.insert(notice);
					} catch (Exception e) {
						logger.error("组装到达提醒失败："+e);
					}
				}
			}.start();
		}
	}
	
	/**
	 * 任务退回给发起人
	 * @param executionId
	 * @param approveResult
	 */
	public void returnToOwnerNotice(final String executionId,final ApproveResult approveResult) {
		final Map<String, Object> content = getExectionInfo(executionId);
		content.put("WWW_ADDRSS", PerpertiesTool.getPro("WWW_ADDRSS"));
		if(content != null){
			new Thread() { // 异步处理
				public void run() {
					    // 是否完成提醒 
						int completeRemind = repositoryService.getCompleteRemind(String.valueOf(content.get("procdef_id_")));
						try {
							Notice notice = new Notice();
							notice.setToPeople(String.valueOf(content.get("owner_")));
							notice.setTitle(String.valueOf(content.get("business_title_")));
							notice.setStation(0);
							if (completeRemind == 1) {
								notice.setEmail(0); 
							}
							notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_RETURN, content));
							notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_RETURN, content));
							noticeBiz.insert(notice);
						} catch (Exception e) {
							logger.error("发送邮件失败");
						}
				}
			}.start();
		}
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
	 * 
	 * @param isSend 是否发送邮件提醒 1，是；0,否
	 * @param executionId 任务实例ID
	 */

	public void completeNotice(final int isSend,String executionId) {
		final Map<String, Object> content = getExectionInfo(executionId);
		content.put("WWW_ADDRSS", PerpertiesTool.getPro("WWW_ADDRSS"));
		if(content != null){
			new Thread() { // 异步处理
				public void run() {
						try {
							Notice notice = new Notice();
							notice.setToPeople(String.valueOf(content.get("owner_")));
							notice.setTitle(String.valueOf(content.get("business_title_")));
							notice.setStation(0);
							if (isSend == 1) {
								notice.setEmail(0); 
							}
							notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_COMPLETE, content));
							notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_COMPLETE, content));
							noticeBiz.insert(notice);
						} catch (Exception e) {
							logger.error("发送邮件失败");
						}
				}
			}.start();
		}
	}
	
	/**
	 * 审批不通过提醒
	 * @param executionId
	 * @param approveResult 审批意见
	 */
	public void completeNotice(final String executionId,final ApproveResult approveResult){
		final Map<String, Object> content = getExectionInfo(executionId);
		content.put("WWW_ADDRSS", PerpertiesTool.getPro("WWW_ADDRSS"));
		if(content != null){
			new Thread() { // 异步处理
				public void run() {
					    // 是否完成提醒 
						int completeRemind = repositoryService.getCompleteRemind(String.valueOf(content.get("procdef_id_")));
						try {
							Notice notice = new Notice();
							notice.setToPeople(String.valueOf(content.get("owner_")));
							notice.setTitle(String.valueOf(content.get("business_title_")));
							notice.setStation(0);
							if (completeRemind == 1) {
								notice.setEmail(0); 
							}
							notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_NO_PASS, content));
							notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_NO_PASS, content));
							noticeBiz.insert(notice);
						} catch (Exception e) {
							logger.error("发送邮件失败");
						}
				}
			}.start();
		}
	}
	
	/**
	 * 任务废弃提醒
	 * @param execution
	 */
	public void abolishNotice(String executionId){
		final Map<String, Object> content = getExectionInfo(executionId);
		content.put("WWW_ADDRSS", PerpertiesTool.getPro("WWW_ADDRSS"));
		if(content != null){
			new Thread() { // 异步处理
				public void run() {
					    // 是否完成提醒 
						int completeRemind = repositoryService.getCompleteRemind(String.valueOf(content.get("procdef_id_")));
						try {
							Notice notice = new Notice();
							notice.setToPeople(String.valueOf(content.get("owner_")));
							notice.setTitle(String.valueOf(content.get("business_title_")));
							notice.setStation(0);
							if (completeRemind == 1) {
								notice.setEmail(0); 
							}
							notice.setContent(VelocityUtils.parseVm(TemplateType.TASK_ABOLISH, content));
							notice.setHtmlContent(VelocityUtils.parseVm(TemplateType.HTML_TASK_ABOLISH, content));
							noticeBiz.insert(notice);
						} catch (Exception e) {
							logger.error("发送邮件失败");
						}
				}
			}.start();
		}
	}
	
	private Map<String, Object> getExectionInfo(String executionId){
		try {
		    Map<String,Object>	exectionInfo = (Map<String, Object>) baseService.queryForObject("Notice_TaskExecutionInfo", executionId);
		    return exectionInfo;
		} catch (SQLException e1) {
			logger.error("组装邮件内容查询失败！"+e1);
		}
		return null;
	}
	
	private List<ApproveResult> getApproveList(String executionId){
		// 审批意见
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("execution_id_", executionId);
		params.put("record_type_", RecodeType.User.getVal());
		List<ApproveResult> list = historyService.listApproveResult(params);
		return list;
	}
	
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public void setNoticeBiz(NoticeBiz noticeBiz) {
		this.noticeBiz = noticeBiz;
	}
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}
