package com.chz.smartoa.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.fileUpload.service.FileGroupBiz;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.form.service.FormBiz;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.task.Handler.NoticeHandler;
import com.chz.smartoa.task.enumcode.ExecutionStatus;
import com.chz.smartoa.task.enumcode.TaskError;
import com.chz.smartoa.task.enumcode.TaskResult;
import com.chz.smartoa.task.exception.NotFoundReConfException;
import com.chz.smartoa.task.exception.NotFoundReProcedfException;
import com.chz.smartoa.task.exception.TaskInvalidException;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.ReProcdef;
import com.chz.smartoa.task.pojo.RuConf;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.service.FlowService;
import com.chz.smartoa.task.service.HistoryService;
import com.chz.smartoa.task.service.RepositoryService;
import com.chz.smartoa.task.service.RuntimeService;
import com.chz.smartoa.task.service.TaskService;

public class FlowServiceImpl implements FlowService {
	
	private static final Logger logger = Logger.getLogger(FlowServiceImpl.class);
	
	private RepositoryService repositoryService;
	private FileGroupBiz fileGroupBiz;
	private RuntimeService runtimeService;
	private HistoryService historyService;
	private TaskService taskService;
	private NoticeHandler noticeHandler;
	private OperateLogBiz operateLogBiz;
	private FormBiz formBiz;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getPage(String templateId) throws Exception {
		return formBiz.reachFormTemplate(templateId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getPage(String templateId, String businessKey) throws Exception {
		return formBiz.reachFormTemplate(templateId,businessKey);
	}
	
	@Override
	public int getImportantGrade(String templateId){
		//模板对应流程名称
		String  processName = formBiz.getProcessNamebyFtId(templateId);
		if(StringUtils.isEmpty(processName)){
			throw new NullPointerException("查询流程名称为空,templateId:"+templateId);
		}
		//查询最新版本流程 By 流程名称
		ReProcdef procdef = repositoryService.findReProcdefByName(processName);
		if(procdef == null){
			return 0;
		}
		return procdef.getImportant_grade_();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String createProcess(String templateId,String businessTitle, String desc, int status,int priority,String executionId,Map<String, Object> params) throws Exception {
		//模板对应流程名称
		String  processName = formBiz.getProcessNamebyFtId(templateId);
		if(StringUtils.isEmpty(processName)){
			throw new NullPointerException("查询流程名称为空,templateId:"+templateId);
		}
		//保存上报信息
		params.put(FormConstants.FORM_STATUS, status);//0：暂存，1：提交
		Map<String,Object> map = formBiz.saveForm(params);
		String businessKey =  String.valueOf(map.get(FormConstants.FORMRECORD_ID));
		String projectId =  String.valueOf(map.get(FormConstants.PROJECT_ID)); 
		
		//启动流程
		executionId = startProcess(processName,businessKey,businessTitle,templateId,projectId,desc,priority,status,executionId);
		params.put("executionId", executionId);
		//保存流程附件
		if(params.get("file_params") != null && ((String[])params.get("file_params")).length >=1){
			params.put("fileIds", (String[])params.get("file_params"));   // 页面有新增的
		}
		fileGroupBiz.insertFileGroup(params);  
		return executionId;
	}
	
	@Override
	public String startProcess(String procdefName, String businessKey,
			String businessTitle, String templateId, String projectId,String desc_,int priority,int status,String executionId){
		if(logger.isDebugEnabled()){
			logger.debug("into startProcess(String procdefName, String businessKey,"
					+ "String businessTitle,String templateId)"+procdefName+","+businessKey+","+businessTitle+","+templateId+","+projectId+",executionId"+executionId);
		}
		//插入流程实例
		if(status == 1){//提交
			//查询最新版本流程 By 流程名称
			ReProcdef procdef = repositoryService.findReProcdefByName(procdefName);
			if(procdef == null){
				throw new NotFoundReProcedfException(TaskError.NotFoundReProcdef.getVal(), "未找到流程："+procdef);
			}
			String procdefId = procdef.getProcdef_id_();
			//插入流程实例
			executionId = runtimeService.insertGeExcution(procdefId,businessKey,businessTitle,templateId,projectId,desc_,priority,status,executionId);
			
			//生成运行流程节点
			repositoryService.insertRuConfs(executionId,procdefId);
			
			GeExecution execution = taskService.getGeExecution(executionId);
			//插入待办任务
			addTodoTask(execution,1,procdef);
			//插入办结任务
			historyService.insertTaskHistory(execution);
		}else{
			//暂存
			executionId = runtimeService.insertGeExcution(null,businessKey,businessTitle,templateId,projectId,desc_,priority,status,executionId);
		}
		return executionId;
	}
	
	@Override
	public void startProcess(String taskId){
		RuTask ruTask = taskService.getRutask(taskId);
		if(ruTask == null){
			throw new TaskInvalidException(TaskError.TaskInvalid.getVal(), "任务已失效");
		}
		GeExecution execution = taskService.getGeExecution(ruTask.getExecutionId());
		//删除待办任务
		runtimeService.deleteRuTaskByTaskId(taskId);
		//记录历史
		ApproveResult approveResult = new ApproveResult();
		approveResult.setApproveType(TaskResult.Submit.getVal());
		historyService.insertTaskHistory(execution,ruTask,approveResult);
		//当前是否还有待办任务（阅处除外）
		boolean isNext = taskService.getIsNext(ruTask.getExecutionId());
		if(isNext){
			//进入流程下一步
			addTodoTask(execution);
		}
	}
	
	@Override
	public Map<String, Object> findTodoTask(String taskId,String user) {
		Map<String, Object> map =  taskService.getTodoTask(taskId,user);
		/* 2015-05-17
		if("4".equals(String.valueOf(map.get("action_type_")))){//传阅
			RuTask ruTask = taskService.getRutask(taskId);
			GeExecution execution = taskService.getGeExecution(ruTask.getExecutionId());
			//删除待办任务
			runtimeService.deleteRuTaskByTaskId(taskId);
			//插入历史记录
			historyService.insertTaskHistory(execution, ruTask, TaskResult.View.getVal());
		}*/
		
		//查询当前节点处理意见
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("task_id_", taskId);
		List<ApproveResult> results = historyService.listApproveResult(params);
		if(results != null && results.size() > 0){
			map.put("taskApproveResults", results);
		}
		return map;
	}
	
	@Override
	public void approveProcess(String taskId) {
		RuTask ruTask = taskService.getRutask(taskId);
		if (ruTask == null) {
			throw new TaskInvalidException(TaskError.TaskInvalid.getVal(),"任务已失效");
		}
		GeExecution execution = taskService.getGeExecution(ruTask.getExecutionId());
		runtimeService.deleteRuTaskByTaskId(taskId);
		// 当前是否还有待办任务（阅处除外）
		boolean isNext = taskService.getIsNext(ruTask.getExecutionId());
		if (isNext) {
			// 进入流程下一步
			addTodoTask(execution);
		}
		historyService.insertTaskHistory(execution,ruTask,TaskResult.Modify.getVal());
	}
	

	@Override
	public void approveProcess(String taskId, Map<String, Object> variables) throws TaskInvalidException,Exception{
		ApproveResult approveResult = (ApproveResult)variables.get("approveResult");
		// 审批结果:1：同意，2：不同意，3：退回修改，4：已阅，
		RuTask ruTask = taskService.getRutask(taskId);
		if(ruTask == null){
			throw new TaskInvalidException(TaskError.TaskInvalid.getVal(), "任务已失效");
		}
		GeExecution execution = taskService.getGeExecution(ruTask.getExecutionId());
		
		//处理审批
		int approveType = approveResult.getApproveType();
		if(approveType != TaskResult.Submit.getVal()){//如果是申请提交则不记录审批历史，否则记录审批历史
			//记录历史
			historyService.insertTaskHistory(execution,ruTask,approveResult);
		}
		if(approveType == TaskResult.Submit.getVal()){//申请提交
			//保存业务数据
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put(FormConstants.FORMTEMPLATE_ID, execution.getTemplateId());
			paramMap.put(FormConstants.FLOW_REQUEST_MAP,variables.get(FormConstants.FLOW_REQUEST_MAP));
			paramMap.put(FormConstants.LOGIN_NAME,variables.get(FormConstants.LOGIN_NAME));
			paramMap.put(FormConstants.FORM_STATUS, 2);
			Map<String,Object> map = formBiz.saveForm(paramMap);
			//更新流程实例相关数据
			String projectId =  String.valueOf(map.get(FormConstants.PROJECT_ID)); 
			runtimeService.updateGeExcution(execution.getExecutionId(),String.valueOf(variables.get("title_")), projectId,String.valueOf(variables.get("desc_")),Integer.valueOf(String.valueOf(variables.get("priority_"))));
			//删除当前待办
			runtimeService.deleteRuTaskByTaskId(taskId);
			//进入流程下一步
			addTodoTask(execution);
		}else if(approveType == TaskResult.Agree.getVal()||approveType ==TaskResult.Read.getVal()){//同意||已阅
			//查看审批类型
			int actionType = repositoryService.getActionType(ruTask.getConfId());
			if(actionType == 2){//审批异或
				runtimeService.deleteRuTaskByConfId(ruTask.getExecutionId(),ruTask.getConfId());
			}else{
				runtimeService.deleteRuTaskByTaskId(taskId);
			}
			//当前是否还有待办任务（阅处除外）
			boolean isNext = taskService.getIsNext(ruTask.getExecutionId());
			if(isNext){
				//进入流程下一步
				addTodoTask(execution);
			}
		}else if(approveType == TaskResult.Disagree.getVal()){//不同意
			//删除所有待办任务
			runtimeService.deleteRuTaskByExecutionId(ruTask.getExecutionId());
			//完成流程，审批不通过
			runtimeService.completeTask(ruTask.getExecutionId(), ExecutionStatus.NoPass.getVal());
			//发送完成邮件
			noticeHandler.completeNotice(ruTask.getExecutionId(),approveResult);
			
		}else if(approveType == TaskResult.Back.getVal()){//退回修改
			//删除所有待办任务
			runtimeService.deleteRuTaskByExecutionId(ruTask.getExecutionId());
			//重置流程实例 步骤为0、版本加1
			runtimeService.resetGeExecution(ruTask.getExecutionId());
			//给发起者加一条待办任务
			addTodoTaskForOnwer(ruTask.getExecutionId());
			//发送到达邮件给发起者
			noticeHandler.returnToOwnerNotice(ruTask.getExecutionId(),approveResult);
		}else if(approveType == TaskResult.Turn.getVal()){//转办
			String to_user  = String.valueOf(variables.get("to_user"));
			runtimeService.toTurn(taskId,to_user);
			//发送到达邮件
			noticeHandler.arriveNotice(1,to_user, execution.getExecutionId());
		}else if(approveType == TaskResult.Consult.getVal()){//征询
			String to_user  = String.valueOf(variables.get("to_user"));
			runtimeService.toConsult(ruTask, to_user);
			//发送到达邮件
			noticeHandler.arriveNotice(1,to_user, execution.getExecutionId());
		}else if(approveType == TaskResult.DoneConsult.getVal()){//被征询人完成征询
			runtimeService.doneConsult(ruTask);
			//发送到达邮件
			noticeHandler.arriveNotice(1,ruTask.getConsult(),execution.getExecutionId());
		}else if(approveType == TaskResult.Readonly.getVal()){//传阅
			//删除待办任务
			runtimeService.deleteRuTaskByTaskId(taskId);
			//插入历史记录
			historyService.insertTaskHistory(execution, ruTask, TaskResult.View.getVal());
		}
	}
	
	// 新增待办任务
	private void addTodoTaskForOnwer(String executionId) {
		GeExecution execution = taskService.getGeExecution(executionId);
		RuTask ruTask = new RuTask();
		ruTask.setAssignee(execution.getOwner());
		ruTask.setExecutionId(executionId);
		ruTask.setConfId(0);
		runtimeService.insertRuTasks(ruTask);
	}
	
	//新增待办任务
	private void addTodoTask(GeExecution execution){
		//查询流程
		ReProcdef procdef = repositoryService.findReProcdefByExecutionId(execution.getExecutionId());
		//查询下个流程sort_num_
		Integer sortNum = taskService.getNextSortNum(execution.getExecutionId());
		if(sortNum == null){ //没找到下个节点
			//结束流程，审批完成
			runtimeService.completeTask(execution.getExecutionId(),ExecutionStatus.Complete.getVal());
			//发送完成邮件
			noticeHandler.completeNotice(procdef.getComplete_remind_(),execution);
		}else{
			addTodoTask(execution,sortNum, procdef);
		}
	}
	
	// 新增待办任务(此方法不通用，提供给【修改流程】使用)
	private void addTodoTask(GeExecution execution, Integer sortNum) {
		// 查询流程
		ReProcdef procdef = repositoryService.findReProcdefByExecutionId(execution.getExecutionId());
		if (sortNum == null) { // 没找到下个节点
			// 结束流程，审批完成
			runtimeService.completeTask(execution.getExecutionId(),ExecutionStatus.Complete.getVal());
			// 发送完成邮件
			noticeHandler.completeNotice(procdef.getComplete_remind_(),execution);
		} else {
			addTodoTask(execution, sortNum, procdef);
		}
	}
	
	//新增待办任务
	private void addTodoTask(GeExecution execution,Integer sortNum,ReProcdef procdef){
		int isNext = 1; //是否流转到下一步：1，是; 0，否
		int cnt = 0;//插入任务条数
		do {
			//查询流程节点
			List<RuConf> ruConfs = repositoryService.listRuConf(execution.getExecutionId(),sortNum);
			if(ruConfs == null||ruConfs.size()==0){
				throw new NotFoundReConfException(TaskError.NotFoundReConf.getVal(), "未找到流程["+procdef.getProcdef_id_()+"]步骤："+sortNum);
			}
			for (RuConf ruconf : ruConfs) {
				if(ruconf.getAction_type_() != 4){//不是阅处
					isNext = 0;
				}
				cnt += runtimeService.insertRuTasks(ruconf,execution,procdef.getUplink_());
			}
			//更新流程步骤
			runtimeService.updateGeExecutionTaskNum(execution.getExecutionId(), sortNum);
			if(isNext == 1 || cnt ==0){//流转至下个流程,
				//查询下个流程sort_num_
				sortNum = taskService.getNextSortNum(execution.getExecutionId());
				if(sortNum == null){ //没找到下个节点
					//结束流程，审批完成
					runtimeService.completeTask(execution.getExecutionId(),ExecutionStatus.Complete.getVal());
					noticeHandler.completeNotice(procdef.getComplete_remind_(),execution);
					//结束循环
					isNext = 0;
				}
			}
		} while (isNext == 1|| cnt == 0);//流转至下个流程
	}
	
	@Override
	public void delegationProcess(String taskId,String toUser) {
		RuTask ruTask = taskService.getRutask(taskId);
		if(ruTask == null){
			throw new TaskInvalidException(TaskError.TaskInvalid.getVal(), "任务已失效");
		}
		runtimeService.delegation(ruTask,toUser);
		GeExecution execution = taskService.getGeExecution(ruTask.getExecutionId());
		historyService.insertTaskHistory(execution, ruTask,TaskResult.Delegation.getVal());
	}
	
	@Override
	public void skipProcess(String executionId) {
		GeExecution execution = taskService.getGeExecution(executionId);
		//删除当前步骤所有审批任务
		runtimeService.deleteRuTaskByTaskNum(executionId);
		//记录操作日志
		operateLogBiz.info(OperateLogType.TASK_MANAGE,execution.getExecutionId(),execution.getBusinessTitle(),"成功","跳过步骤："+execution.getTaskNum());
		//当前是否还有待办任务（阅处除外）
		boolean isNext = taskService.getIsNext(executionId);
		if(isNext){
			//进入流程下一步
			addTodoTask(execution);
		}
	}

	@Override
	public void abolishProcess(String executionId) {
		GeExecution execution = taskService.getGeExecution(executionId);
		//删除所有待办任务
		runtimeService.deleteRuTaskByExecutionId(executionId);
		//完成流程，废弃
		runtimeService.completeTask(executionId, ExecutionStatus.Invalid.getVal());
		//记录操作日志
		operateLogBiz.info(OperateLogType.TASK_MANAGE,execution.getExecutionId(), execution.getBusinessTitle(),"流程废弃成功");
		//发送完成邮件，废弃
		noticeHandler.abolishNotice(execution);
	}
	
	@Override
	public void saveRuConfs(List<RuConf> confs, String executionId,String toDeleteIds) {
		//删除可修改节点
		if(toDeleteIds != null){
			String[] confIds = toDeleteIds.split(",");
			if(confIds.length > 0)
				repositoryService.deleteRuConfs(confIds);
		}
		//保存修改后节点
		if(confs != null && confs.size() > 0){
			repositoryService.insertRuConfs(confs, executionId);
		}
		//删除当前任务
		runtimeService.deleteRuTaskByExecutionId(executionId);
		
		//进入流程下一步
		GeExecution execution = taskService.getGeExecution(executionId);
		//查询当前流程sort_num_
		Integer sortNum = taskService.getCurrentSortNum(execution.getExecutionId());
		addTodoTask(execution, sortNum);
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	public void setNoticeHandler(NoticeHandler noticeHandler) {
		this.noticeHandler = noticeHandler;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	public void setFormBiz(FormBiz formBiz) {
		this.formBiz = formBiz;
	}
	public void setFileGroupBiz(FileGroupBiz fileGroupBiz) {
		this.fileGroupBiz = fileGroupBiz;
	}
}
