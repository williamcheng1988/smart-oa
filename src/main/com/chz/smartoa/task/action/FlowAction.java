package com.chz.smartoa.task.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.dynamicForm.util.PublicFunction;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.StaffBiz;
import com.chz.smartoa.task.enumcode.RecodeType;
import com.chz.smartoa.task.enumcode.TaskError;
import com.chz.smartoa.task.enumcode.TaskResult;
import com.chz.smartoa.task.exception.ApproveResultInvalidException;
import com.chz.smartoa.task.exception.NotFoundUserByRoleException;
import com.chz.smartoa.task.exception.TaskInvalidException;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.HiTask;
import com.chz.smartoa.task.pojo.HiTaskVo;
import com.chz.smartoa.task.pojo.RuConf;
import com.chz.smartoa.task.pojo.RuTaskVo;
import com.chz.smartoa.task.service.FlowService;
import com.chz.smartoa.task.service.HistoryService;
import com.chz.smartoa.task.service.RepositoryService;
import com.chz.smartoa.task.service.TaskService;

public class FlowAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FlowAction.class);
	//模板Id
	private String formTemplateId;
	//流程名称
	private String processName;
	//任务申请用户
	private Staff staff;
	//请求要点
	private String title_;
	//处理类型
	private String approve_type_;
	//启用优先级：0：不启用，1：启用
	private int importantGrade_;
	//优先级  0：一般，1：高，2：紧急
	private int priority_;
	//处理对象（转办、协办）
	private String user_id_;
	//描述
	private String desc_;
	//保存状态： 0暂存，1提交;
	private int status_ = 1;
	//任务ID
	private String taskId;
	//任务实例ID
	private String executionId;
	//任务实例
	private GeExecution exectuion = new GeExecution();
	//任务明细
	private Map<String, Object> task;
	//跟踪任务
	private  HiTask hiTask = new HiTask();
	//流程配置行号
	private String lineNos = "";
	//待删除运行节点
	private String toDeleteIds = "";
	//运行中的流程节点
	private List<RuConf> ruconfs;
	
	private Map<String, Object> formMap;
	
	private FlowService flowService;
	private TaskService taskService;
	private HistoryService historyService;
	private RepositoryService repositoryService;
	
	private OperateLogBiz operateLogBiz;
	
	private StaffBiz staffBiz;
	
	public FlowAction() {
		staff = LoginUtils.getLoginStaff();
	}
	
 	/**
	 * 打开任务填报页面
	 * @return
	 */
	public String launch(){
		try {
			if(StringUtils.isEmpty(executionId)){ //新发起流程
				formMap = flowService.getPage(formTemplateId);
				importantGrade_ = flowService.getImportantGrade(formTemplateId);
			}else{ //暂存后修改
				try {
					GeExecution execution =  taskService.getGeExecution(executionId);
					formTemplateId = execution.getTemplateId();
					//标题
					title_ = execution.getBusinessTitle();
					//描述
					desc_ = execution.getDesc();
					//紧急程度
					importantGrade_ = flowService.getImportantGrade(formTemplateId);
					if(importantGrade_ > 0 && execution.getPriority() > 0){
						importantGrade_ = execution.getPriority();
					}
					//填报基本信息
					formMap = flowService.getPage(execution.getTemplateId(),execution.getBusinessKey());
				} catch (Exception e) {
					logger.error(e);
					throw new Exception("加载模板及其内容失败!");
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "launch";
	}
	
	//保存任务
	@SuppressWarnings("unchecked")
	public String save() throws Exception{
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("发起流程;formTemplateId："+formTemplateId+",title:"+title_+",status"+status_);
			}
			/**
			 * 填报数据
			 */
			Map<String,Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put(FormConstants.FORMTEMPLATE_ID, formTemplateId);
			paramMap.put(FormConstants.FLOW_REQUEST_MAP,PublicFunction.fetchParamsFromHttpRequest(getHttpServletRequest(),null));
			//附件
			Map<String, String[]> parasMap = (Map<String, String[]>)super.getHttpServletRequest().getParameterMap();
			paramMap.put("file_params",parasMap.get("file_params"));			
			//发起用户
			Staff staff = getLoginStaff();
			if(staff!=null){
				paramMap.put(FormConstants.LOGIN_NAME, staff.getLoginName());
			}
			//发起流程
			executionId = flowService.createProcess(formTemplateId,title_,desc_,status_,importantGrade_,executionId,paramMap);
			
			if(status_ == 1){
				operateResult = new OperateResult(1, "流程发起成功！");
			}else{
				String recordId = paramMap.get(FormConstants.FORMRECORD_ID).toString();
				operateResult = new OperateResult(2, executionId+","+recordId);
			}
		} catch(TaskInvalidException e){
			logger.error(e);
			operateResult = new OperateResult(-2, e.getMessage());
		}catch (NotFoundUserByRoleException e) {
			logger.error(e);
			operateResult = new OperateResult(-3, e.getMessage());
		}catch (Exception e) {
			logger.error(e);
			operateResult = new OperateResult(-4, "未知异常:"+e.getMessage());
		}
		return OPER_RESULT;
	}
	
	// 查询用户待办任务列表
	public String todoList() {
		staff = LoginUtils.getLoginStaff();
		// 待办任务列表
		String sort = getHttpServletRequest().getParameter("sort");
		String order = getHttpServletRequest().getParameter("order");
		List<RuTaskVo> todoList = null;
		try {
			todoList = taskService.listTodoTask(staff.getLoginName(), sort,order);
		} catch (Exception e) {
			logger.error(e);
		}
		if(todoList != null){
			dataGrid  = new DataGrid(todoList.size(),todoList);
		}else{
			dataGrid  = new DataGrid(0,null);
		}
		return DATA_GRID;
	}
	
	//草稿箱
	public String draftList() {
			staff = LoginUtils.getLoginStaff();
			List<GeExecution> draftList = null;
			try {
				draftList = taskService.listDrafts(staff.getLoginName());
			} catch (Exception e) {
				logger.error("查询草稿失败："+e);
			}
			if(draftList != null){
				dataGrid  = new DataGrid(draftList.size(),draftList);
			}else{
				dataGrid  = new DataGrid(0,null);
			}
		return DATA_GRID;
	}
	
	//待办任务处理页面
	public String approve() throws Exception {
		// 查询待办任务列
		task = flowService.findTodoTask(taskId,LoginUtils.getLoginStaff().getLoginName());
		if(task == null){
			throw new TaskInvalidException(TaskError.TaskInvalid.getVal(), "未找到指定任务！");
		}
		// 其它节点审批意见
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("execution_id_", task.get("execution_id_"));
		params.put("record_type_", RecodeType.User.getVal());
		params.put("exclude_task_id", taskId);
		List<ApproveResult> list = historyService.listApproveResult(params);
		task.put("approveResults", list);
		
		//任务申请人基本信息
		Staff ower = staffBiz.findStaffByLoginName(String.valueOf(task.get("owner_")));
		task.put("owner_name_", ower.getRealName());
		task.put("owner_mobile_",ower.getMobile());
		try {
			//填报基本信息
			formMap = flowService.getPage(String.valueOf(task.get("template_id_")),String.valueOf(task.get("business_key_")));
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("加载模板及其内容明细失败!");
		}
		this.executionId =String.valueOf(task.get("execution_id_"));
		return "approve";
	}
	
	
	
	// 处理待办任务
	public String deal() {
		Map<String, Object> variables = new HashMap<String, Object>();
		ApproveResult result = new ApproveResult();
		if("SUBMIT".equals(approve_type_)){
			variables.put(FormConstants.FLOW_REQUEST_MAP,PublicFunction.fetchParamsFromHttpRequest(getHttpServletRequest(),null));
			variables.put("title_", title_);
			variables.put("desc_", desc_);
			variables.put("priority_", priority_);
			
			//发起用户
			Staff staff = getLoginStaff();
			if(staff!=null){
				variables.put(FormConstants.LOGIN_NAME,staff.getLoginName());
			}
			result.setApproveType(TaskResult.Submit.getVal());
		}else if("READ".equals(approve_type_)){
			result.setApproveType(TaskResult.Read.getVal());
		}else if("READONLY".equals(approve_type_)){
			result.setApproveType(TaskResult.Readonly.getVal());
		}else if("AGREE".equals(approve_type_)){
			result.setApproveType(TaskResult.Agree.getVal());
		}else if("BACK".equals(approve_type_)){
			result.setApproveType(TaskResult.Back.getVal());
		}else if("DISAGREE".equals(approve_type_)){
			result.setApproveType(TaskResult.Disagree.getVal());
		}else if("TURN".equals(approve_type_)){
			variables.put("to_user", user_id_);
			result.setApproveType(TaskResult.Turn.getVal());
		}else if("CONSULT".equals(approve_type_)){
			variables.put("to_user", user_id_);
			result.setApproveType(TaskResult.Consult.getVal());
		}else if("DONECONSULT".equals(approve_type_)){
			result.setApproveType(TaskResult.DoneConsult.getVal());
		}else{
			operateResult = new OperateResult(-1, "流程操作识别失败！");
			return OPER_RESULT;
		}
		//设置审批意见
		if("READ".equals(approve_type_)){
			result.setApproveDesc("已阅");
		}else{
			result.setApproveDesc(desc_);
		}
		variables.put("approveResult", result);
		try {
			flowService.approveProcess(taskId, variables);
			operateResult = new OperateResult(1, "任务处理成功");
		} catch(TaskInvalidException e){
			logger.error(e);
			operateResult = new OperateResult(-2, e.getMessage());
		}catch (NotFoundUserByRoleException e) {
			logger.error(e);
			operateResult = new OperateResult(-3, e.getMessage());
		}catch (Exception e) {
			logger.error(e);
			operateResult = new OperateResult(-4, "未知异常:"+e.getMessage());
		}
		return OPER_RESULT;
	}
	
	//任务管理
	public String manage(){
		try{
			if("MODIFY".equals(approve_type_)){
				//查询当前流程步骤
				ruconfs = repositoryService.listRuConfWithStatus(executionId);
				if(ruconfs != null && ruconfs.size() > 0){
					int i = 1;
					for (RuConf conf : ruconfs) {
						if(conf.getIs_edit_() == 1){
							lineNos += i+",";
							toDeleteIds += conf.getConf_id_()+",";
						}
						i++;
					}
				}
				return "modifyFlow";
			}else if("SKIP".equals(approve_type_)){
				flowService.skipProcess(executionId);
			}else if("INVALID".equals(approve_type_)){
				flowService.abolishProcess(executionId);
			}else{
				throw new ApproveResultInvalidException(TaskError.ApproveResultInvalid.getVal(), "流程操作识别失败！");
			}
			operateResult = new OperateResult(1,"success");
		}catch(Exception e){
			operateResult = new OperateResult(-1,e.getMessage());
		}
		return OPER_RESULT;
	} 

	/**
	 * 新增流程明细
	 * @return
	 */
	public String saveReconf() {
		List<RuConf> confs = putReconfToList();
		if(confs.size() > 0){
			try {
				flowService.saveRuConfs(confs,executionId,toDeleteIds);
			} catch (Exception e) {
				logger.error(e);
				operateResult = new OperateResult(1,"系统繁忙，请稍后再试！");
				operateLogBiz.info(OperateLogType.FLOW_MANAGE, executionId, "修改流程："+executionId,"修改流程失败");
				return OPER_RESULT;
			}
		}
		operateResult = new OperateResult(1,"流程修改成功！");
		operateLogBiz.info(OperateLogType.FLOW_MANAGE, executionId, "修改流程："+executionId,"修改流程成功");
		return OPER_RESULT;
	}

	private List<RuConf> putReconfToList(){
		List<RuConf> list = new ArrayList<RuConf>();
		Map<String, String[]> parasMap = (Map<String, String[]>)super.getHttpServletRequest().getParameterMap();
		try {
			String[]  nos = lineNos.split(",");
			for (String i : nos) {
				if(i==null||"".equals(i.trim())){
					continue;
				}
				RuConf conf = new RuConf();
				conf.setSort_num_(Integer.parseInt(parasMap.get("sort_num_"+i)[0]));
				conf.setTask_desc_(parasMap.get("task_desc_"+i)[0]);
				conf.setAction_type_(Integer.parseInt(parasMap.get("action_type_"+i)[0]));
				conf.setAction_obj_type_(Integer.parseInt(parasMap.get("action_obj_type_"+i)[0]));
				conf.setAction_obj_(parasMap.get("action_obj_"+i)[0]);
				//转办
				if (parasMap.containsKey("is_turn_"+i)) {
					conf.setIs_turn_(Integer.parseInt(parasMap.get("is_turn_"+i)[0].trim()));
				}else{
					conf.setIs_turn_(0);
				}
				//征询
				if (parasMap.containsKey("is_ask_"+i)) {
					conf.setIs_ask_(Integer.parseInt(parasMap.get("is_ask_"+i)[0].trim()));
				}else{
					conf.setIs_ask_(0);
				}
				//流程变更
				if (parasMap.containsKey("is_modify_"+i)) {
					conf.setIs_modify_(Integer.parseInt(parasMap.get("is_modify_"+i)[0].trim()));
				}else{
					conf.setIs_modify_(0);
				}
				//过期时间
				conf.setExpiry_days_(Integer.parseInt(parasMap.get("expiry_days_"+i)[0]));
				//到达提醒
				if (parasMap.containsKey("arrive_remind_"+i)) {
					conf.setArrive_remind_(Integer.parseInt(parasMap.get("arrive_remind_"+i)[0].trim()));
				}else{
					conf.setArrive_remind_(0);
				}
				//过期提醒
				if (parasMap.containsKey("expiry_remind_"+i)) {
					conf.setExpiry_remind_(Integer.parseInt(parasMap.get("expiry_remind_"+i)[0].trim()));
				}else{
					conf.setExpiry_remind_(0);
				}
				list.add(conf);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return list;
	}
	
	//查看任务
	public String view() {
		task = taskService.getTaskExecution(executionId);
		//查询是否是流程管理员
		task.put("isManager", repositoryService.getIsManager(String.valueOf(task.get("procdef_id_")),getStaff().getLoginName()));
		// 流程图
		List<Map<String, String>> confs = repositoryService.listReporcdefConf(executionId);
		task.put("confs", confs);
		// 审批意见
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("execution_id_", executionId);
		params.put("record_type_", RecodeType.User.getVal());
		List<ApproveResult> list = historyService.listApproveResult(params);
		task.put("approveResults", list);
		//填报基本信息
		try {
			formMap = flowService.getPage(String.valueOf(task.get("template_id_")),String.valueOf(task.get("business_key_")));
		} catch (Exception e) {
			logger.error("获取填报基本信息错误："+e.getMessage());
		}
		return "view";
	}
	
	// 查看任务
	public String approveResult() throws Exception {
		task = taskService.getTaskExecution(executionId);
		//加载业务数据
		try {
			//填报基本信息
			formMap = flowService.getPage(String.valueOf(task.get("template_id_")),String.valueOf(task.get("business_key_")));
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("加载模板及其内容明细失败!");
		}
		//加载审批信息
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("execution_id_", executionId);
		params.put("record_type_", RecodeType.User.getVal());
		List<ApproveResult> list = historyService.listApproveResult(params);
		task.put("approveResults", list);
		return "approveResult";
	}
	
	
	// 跟踪任务列表
	public String historyListPage(){
		return "historyPage";
	}
	
	// 跟踪任务列表
	public String historyList() {
		// 跟踪任务列表
		try {
			setPagination(exectuion);
			staff = LoginUtils.getLoginStaff();
			exectuion.setOwner(staff.getLoginName());
			hiTask.setAssignee(LoginUtils.getLoginStaff().getLoginName());
			List<HiTaskVo> historyList = historyService.listHiTask(exectuion);
			dataGrid = new DataGrid(historyService.listHiTaskCount(exectuion),historyList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	public String getFormTemplateId() {
		return formTemplateId;
	}
	public void setFormTemplateId(String formTemplateId) {
		this.formTemplateId = formTemplateId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Map<String, Object> getFormMap() {
		return formMap;
	}
	public void setFormMap(Map<String, Object> formMap) {
		this.formMap = formMap;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	public void setStaffBiz(StaffBiz staffBiz) {
		this.staffBiz = staffBiz;
	}
	public String getTitle_() {
		return title_;
	}
	public void setTitle_(String title_) {
		this.title_ = title_;
	}
	public String getDesc_() {
		return desc_;
	}
	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}
	public void setUser_id_(String user_id_) {
		this.user_id_ = user_id_;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Map<String, Object> getTask() {
		return task;
	}
	public HiTask getHiTask() {
		return hiTask;
	}
	public void setHiTask(HiTask hiTask) {
		this.hiTask = hiTask;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getApprove_type_() {
		return approve_type_;
	}
	public void setApprove_type_(String approve_type_) {
		this.approve_type_ = approve_type_;
	}
	public GeExecution getExectuion() {
		return exectuion;
	}
	public void setExectuion(GeExecution exectuion) {
		this.exectuion = exectuion;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public int getImportantGrade_() {
		return importantGrade_;
	}
	public int getPriority_() {
		return priority_;
	}
	public void setPriority_(int priority_) {
		this.priority_ = priority_;
	}
	public void setStatus_(int status_) {
		this.status_ = status_;
	}
	public String getLineNos() {
		return lineNos;
	}
	public void setLineNos(String lineNos) {
		this.lineNos = lineNos;
	}
	public String getToDeleteIds() {
		return toDeleteIds;
	}
	public void setToDeleteIds(String toDeleteIds) {
		this.toDeleteIds = toDeleteIds;
	}
	public List<RuConf> getRuconfs() {
		return ruconfs;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
 }
