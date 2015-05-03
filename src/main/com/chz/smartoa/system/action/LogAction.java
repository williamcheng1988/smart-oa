package com.chz.smartoa.system.action;

import java.util.List;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.OperateLog;
import com.chz.smartoa.system.service.OperateLogBiz;

public class LogAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private OperateLog log;
	
	private List<OperateLog> logList;
	
	private OperateLogBiz operateLogBiz;

	//登录日志
	public String login() {
		if(log == null){
			log = new OperateLog();
		}
		log.setLogType(OperateLogType.STAFF_LOGIN.toString());
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(log);

		// 得到分页结果
		logList = operateLogBiz.listLoginLog(log);

		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		dataGrid = new DataGrid(operateLogBiz.listLoginLogCount(log),logList);
		return DATA_GRID;
	}
	
	// 操作日志
	public String opt() {
		if(log == null){
			log = new OperateLog();
		}
		log.setLogType(OperateLogType.STAFF_LOGIN.toString());
		
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(log);
		// 得到分页结果
		logList = operateLogBiz.listOperateLog(log);
		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		dataGrid = new DataGrid(operateLogBiz.listOperateLogCount(log),logList);
		return DATA_GRID;
	}

	public OperateLog getLog() {
		return log;
	}
	public void setLog(OperateLog log) {
		this.log = log;
	}
	public List<OperateLog> getLogList() {
		return logList;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
}
