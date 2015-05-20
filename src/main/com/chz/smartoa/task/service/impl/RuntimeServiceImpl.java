package com.chz.smartoa.task.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.delegation.dao.DelegationDao;
import com.chz.smartoa.delegation.dao.DelegationLogDao;
import com.chz.smartoa.delegation.pojo.Delegation;
import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.task.Handler.NoticeHandler;
import com.chz.smartoa.task.Handler.UserHandler;
import com.chz.smartoa.task.dao.GeExecutionDao;
import com.chz.smartoa.task.dao.RuTaskDao;
import com.chz.smartoa.task.enumcode.ExecutionStatus;
import com.chz.smartoa.task.exception.NotFoundUserByPostException;
import com.chz.smartoa.task.exception.NotFoundUserByRoleException;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuConf;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.service.HistoryService;
import com.chz.smartoa.task.service.RuntimeService;


public class RuntimeServiceImpl implements RuntimeService {
	
	private static final Logger logger = Logger.getLogger(RuntimeServiceImpl.class);
	
	private GeExecutionDao geExecutionDao;
	private RuTaskDao ruTaskDao;
	private DelegationDao delegationDao;
	private DelegationLogDao delegationLogDao;
	private UserHandler userHandler;
	
	private NoticeHandler noticeHandler;
	private HistoryService historyService;
	
	
	@Override
	public String insertGeExcution(String procdefId, String businessKey,
			String businessTitle,String templateId,String projectId,String desc,int priority,int status,String executionId){
		GeExecution execution = getGeExecution(procdefId, businessKey, businessTitle,templateId,projectId,desc,priority,status);
		if(StringUtils.isEmpty(executionId)){
			return geExecutionDao.insertGeExecution(execution);
		}else{
			execution.setExecutionId(executionId);
			geExecutionDao.updateGeExecution(execution);
			return executionId;
		}
		
	}
	
	@Override
	public Integer updateGeExcution(String executionId, String businessTitle,String projectId, String desc,int priority) {
		GeExecution execution =  new GeExecution();
		execution.setExecutionId(executionId);
		execution.setBusinessTitle(businessTitle);
		execution.setProjectId(projectId);
		execution.setPriority(priority);
		execution.setDesc(desc);
		return geExecutionDao.updateGeExecution(execution);
	}
	
	@Override
	public String insertRuTasks(RuTask ruTask) {
		return ruTaskDao.insertRuTask(ruTask);
	}

	@Override
	public int insertRuTasks(RuConf ruconf,GeExecution execution,int upLink)throws NotFoundUserByRoleException, DataAccessException, NotFoundUserByPostException, NullPointerException{
		int cnt = 0;//插入待办条数记录
		RuTask task = new RuTask();// 待办任务
		task.setExecutionId(execution.getExecutionId());
		task.setConfId(ruconf.getConf_id_());
		task.setExpiryDays(ruconf.getExpiry_days_());
		
		logger.debug("处理对象：" + ruconf.getAction_obj_type_());
		
		if (ruconf.getAction_obj_type_() == 1) {// 个人
			task.setAssignee(ruconf.getAction_obj_());
			cnt += insertRuTask(task, ruconf.getArrive_remind_(), upLink);
		}else if (ruconf.getAction_obj_type_() == 2) {// 角色
			List<String> userList = userHandler.listUsersByRole(ruconf.getAction_obj_(),execution.getProjectId());
			for (String user : userList) {
				task.setAssignee(user);
				cnt += insertRuTask(task, ruconf.getArrive_remind_(), upLink);
			}
		}else if(ruconf.getAction_obj_type_() == 3) {// 岗位
			List<String> userList = userHandler.listUsersByPost(execution.getDepartmentId(),ruconf.getAction_obj_(), ruconf.getAction_obj_src_());
			for (String user : userList) {
				task.setAssignee(user);
				cnt += insertRuTask(task, ruconf.getArrive_remind_(), upLink);
			}
		}
		return cnt;
	}
	
	private int insertRuTask(RuTask task, int arriveRemind, int upLink) {
		// 查询是否有委托
		Delegation delegation = this.findDelegationByFromuser(task.getAssignee());
		logger.debug("查询用户" + task.getAssignee() + "委托！" + delegation);
		if (delegation != null) {
			task.setDelegation(task.getAssignee());
			task.setAssignee(delegation.getToUser());
			//插入授权处理日志
			insertDelegationLog(delegation.getDelegationId(),task.getExecutionId());
		}
		logger.debug(task.getAssignee() + "是否级联审批：" + upLink);
		if (upLink == 1) { // 级联审批
			// 查询是否有审批记录
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("execution_id_", task.getExecutionId());
			params.put("assignee_", task.getAssignee());
			boolean isAprroved = historyService.getIsApproved(params);
			if(isAprroved){
				logger.info(task.getExecutionId()+"："+task.getAssignee()+"已经审批过了！");
				return 0;
			}
			// 查询是否已有待办记录
			int cnt = ruTaskDao.getRuTaskCount(params);
			if(cnt > 0) {
				logger.info(task.getExecutionId()+"："+task.getAssignee()+"已经存在待办任务！");
				return 0;
			}
		}
		// 插入待办任务到DB
		ruTaskDao.insertRuTask(task);
		// 发送到达提醒邮件
		noticeHandler.arriveNotice(arriveRemind,task.getAssignee(),task.getExecutionId());
		return 1;
	}

	@Override
	public void deleteRuTaskByTaskId(String taskId) {
		ruTaskDao.deleteRuTask(taskId);
	}

	@Override
	public void deleteRuTaskByConfId(String executionId, Integer confId) {
		ruTaskDao.deleteRuTaskByConfId(executionId, confId);
	}

	@Override
	public void deleteRuTaskByExecutionId(String executionId) {
		ruTaskDao.deleteRuTaskByExecutionId(executionId);
	}
	
	@Override
	public void deleteRuTaskByTaskNum(String executionId) {
		ruTaskDao.deleteRuTaskByTaskNum(executionId);
	}

	@Override
	public void delegation(RuTask ruTask,String toUser) {
		RuTask task = new RuTask();
		ruTask.setTaskId(Integer.valueOf(ruTask.getTaskId()));
		ruTask.setDelegation(ruTask.getAssignee());
		ruTask.setAssignee(toUser);
		ruTaskDao.updateRuTask(task);
	}
	
	@Override
	public void delegation(String fromUser, String toUser) {
		ruTaskDao.delegation(fromUser, toUser);
	}
	
	@Override
	public void delegationCancel(String delegationId) {
		ruTaskDao.delegationCancel(delegationId);
	}

	@Override
	public void toTurn(String taskId,String toUser) {
		RuTask ruTask = new RuTask();
		ruTask.setTaskId(Integer.valueOf(taskId.trim()));
		ruTask.setAssignee(toUser);
		ruTask.setArriveTime("now");
		ruTaskDao.updateRuTask(ruTask);
	}
	
	@Override
	public void toConsult(RuTask ruTask,String toUser) {
		ruTask.setConsult(ruTask.getAssignee());
		ruTask.setAssignee(toUser);
		ruTask.setArriveTime("now");
		ruTaskDao.updateRuTask(ruTask);
	}
	
	@Override
	public void doneConsult(RuTask ruTask) {
		ruTask.setAssignee(ruTask.getConsult());
		ruTask.setConsult("");
		ruTask.setArriveTime("now");		
		ruTaskDao.updateRuTask(ruTask);
	}
	
	@Override
	public void completeTask(String executionId,int status) {
		GeExecution execution = new GeExecution();
		execution.setExecutionId(executionId);
		execution.setTaskStatus(status);
		execution.setEndTime("now");
		geExecutionDao.updateGeExecution(execution);
	}
	@Override
	public void resetGeExecution(String executionId) {
		geExecutionDao.updateGeExecution(executionId);
	}

	@Override
	public void updateGeExecutionTaskNum(String executionId, int sortNum) {
		GeExecution execution = new GeExecution();
		execution.setExecutionId(executionId);
		execution.setTaskNum(sortNum);
		geExecutionDao.updateGeExecution(execution);
	}

	// 初始化流程实例
	private GeExecution getGeExecution(String procdefId, String businessKey,
			String businessTitle, String templateId,String projectId,String desc,int priority,int status) {
		GeExecution execution = new GeExecution();
		execution.setProcdefId(procdefId);
		execution.setBusinessKey(businessKey);
		execution.setBusinessTitle(businessTitle);
		execution.setTemplateId(templateId);
		execution.setProjectId(projectId);
		execution.setPriority(priority);
		execution.setOwner(LoginUtils.getLoginStaff().getLoginName());
		execution.setDepartmentId(LoginUtils.getLoginStaff().getDepartmentId());
		if(status == 1){
			execution.setTaskStatus(ExecutionStatus.Approving.getVal());
		}else{
			execution.setTaskStatus(ExecutionStatus.Cache.getVal());
		}
		execution.setVersion(1);
		execution.setTaskNum(1);
		execution.setDesc(desc);
		return execution;
	}
	
	
	//查询委托
	private Delegation findDelegationByFromuser(String fromUser){
		return delegationDao.findDelegationByFromuser(fromUser);
	}
	
	//插入委托日志
	private void insertDelegationLog(String delegationId,String executionId){
		DelegationLog log = new DelegationLog();
		log.setDelegationId(delegationId);
		log.setExecutionId(executionId);
		delegationLogDao.insertDelegationLog(log);
	}
	
	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}
	public void setGeExecutionDao(GeExecutionDao geExecutionDao) {
		this.geExecutionDao = geExecutionDao;
	}
	public void setRuTaskDao(RuTaskDao ruTaskDao) {
		this.ruTaskDao = ruTaskDao;
	}
	public void setDelegationDao(DelegationDao delegationDao) {
		this.delegationDao = delegationDao;
	}
	public void setDelegationLogDao(DelegationLogDao delegationLogDao) {
		this.delegationLogDao = delegationLogDao;
	}
	public void setNoticeHandler(NoticeHandler noticeHandler) {
		this.noticeHandler = noticeHandler;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}
