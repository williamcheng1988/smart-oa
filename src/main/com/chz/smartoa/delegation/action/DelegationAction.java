package com.chz.smartoa.delegation.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.delegation.pojo.Delegation;
import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.delegation.pojo.DelegationLogVo;
import com.chz.smartoa.delegation.service.DelegationBiz;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.action.StaffAction;

/**
 * 授权管理页面
 * @author wesson
 */
public class DelegationAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(StaffAction.class);

	private String loginName;
	private String delegationId;	
	private Delegation delegation;
	private List<Delegation> delegations;
	
	private DelegationLog delegationLog;
	private List<DelegationLogVo> delegationLogs;
	
	private DelegationBiz delegationBiz;
	
	/**
	 * @return
	 */
	public String insert() {
		delegation.setCreateor(LoginUtils.getLoginStaff().getLoginName());
		if(StringUtils.isEmpty(delegation.getFromUser())){
			delegation.setFromUser(LoginUtils.getLoginStaff().getLoginName());
		}
		//调整授权结束时间
		delegation.setEndDate(delegation.getEndDate()+" 23:59:59");
		//查看当前时间段是否已授权
		Delegation dn = delegationBiz.isExists(delegation);
		if(dn != null){
			operateResult = new OperateResult(-1,"该时间段内已存在授权！"+dn.getFromUserName()+"→"+dn.getToUserName()+":"+dn.getStartDate()+"~"+dn.getEndDate());
		}else{
			try {
				delegationBiz.insertDelegation(delegation);
			} catch (Exception e) {
				System.out.println(e);
			}
			operateResult = new OperateResult(1,"委托成功！");
		}
		return OPER_RESULT;
	}
	
	/**
	 * 撤销授权
	 * @return
	 */
	public String cancel() {
		try {
			delegationBiz.deleteDelegation(delegationId);
			operateResult = new OperateResult(1,"撤消授权成功！");
		} catch (Exception e) {
			logger.error("撤销授权异常："+e);
			operateResult = new OperateResult(-1,"未知异常：撤消授权失败！");
		}
		
		return OPER_RESULT;
	}
	
	// 授权页面
	public String manageList() {
		if (delegation == null) {
			delegation = new Delegation();
		}
		return "manageList";
	}
	
	// 授权列表
	public String manage() {
		if (delegation == null) {
			delegation = new Delegation();
		}		
		// 如果是分页查询，调用基类方法设置分页属性start,limit,sort,order等）
		setPagination(delegation);
		// 得到分页结果
		delegations = delegationBiz.listDelegation(delegation);
		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		dataGrid = new DataGrid(delegationBiz.listDelegationCount(delegation),delegations);
		return DATA_GRID;
	}
	
	
	//我的授权页面
	public String delegationSelfPage(){
		loginName  = LoginUtils.getLoginStaff().getLoginName();
		return "delegationSelf";
	}

	//我的授权列表
	public String list(){
		delegation = new Delegation();
		delegation.setFromUser(LoginUtils.getLoginStaff().getLoginName());
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(delegation);

		// 得到分页结果
		delegations = delegationBiz.listDelegation(delegation);

		dataGrid = new DataGrid(delegationBiz.listDelegationCount(delegation),delegations);
		return DATA_GRID;
	}
	
	
	// 待办页面
	public String undoLog() {
			return "undoLog";
	}
	
	// 待办任务日志列表
	public String undo() {
		delegationLog = new DelegationLog();
		delegationLog.setDelegationId(delegationId);

		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(delegationLog);
		delegationLogs = delegationBiz.listDelegationUndoLog(delegationLog);

		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		dataGrid = new DataGrid(delegationBiz.listDelegationUndoLogCount(delegationLog),delegationLogs);
		return DATA_GRID;
	}
	
	// 已办页面
	public String logPage() {
		return "logPage";
	}
	
	//已办任务日志列表
	public String log(){
		delegationLog = new DelegationLog();
		delegationLog.setDelegationId(delegationId);
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(delegationLog);

		try {
			delegationLogs = delegationBiz.listDelegationLog(delegationLog);

			// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
			dataGrid = new DataGrid(delegationBiz.listDelegationLogCount(delegationLog),delegationLogs);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	public String getDelegationId() {
		return delegationId;
	}
	public void setDelegationId(String delegationId) {
		this.delegationId = delegationId;
	}
	
	public List<Delegation> getDelegations() {
		return delegations;
	}

	public Delegation getDelegation() {
		return delegation;
	}
	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}
	public DelegationLog getDelegationLog() {
		return delegationLog;
	}
	public void setDelegationLog(DelegationLog delegationLog) {
		this.delegationLog = delegationLog;
	}
	public List<DelegationLogVo> getDelegationLogs() {
		return delegationLogs;
	}
	public void setDelegationBiz(DelegationBiz delegationBiz) {
		this.delegationBiz = delegationBiz;
	}
	public String getLoginName() {
		return loginName;
	}
}
