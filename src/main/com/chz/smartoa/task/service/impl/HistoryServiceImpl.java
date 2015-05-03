package com.chz.smartoa.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.dao.HiTaskDao;
import com.chz.smartoa.task.enumcode.RecodeType;
import com.chz.smartoa.task.enumcode.TaskResult;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.HiTask;
import com.chz.smartoa.task.pojo.HiTaskVo;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.service.HistoryService;

public class HistoryServiceImpl implements HistoryService {
	
	private HiTaskDao hiTaskDao;
	@Override
	public void insertTaskHistory(GeExecution execution) {
		HiTask hiTask = new HiTask();
		hiTask.setExecutionId(execution.getExecutionId());
		hiTask.setTaskNum(execution.getTaskNum());
		hiTask.setVersion(execution.getVersion());
		hiTask.setAssignee(execution.getOwner());
		hiTask.setRecordType(RecodeType.System.getVal());
		hiTask.setOperateResult(TaskResult.Submit.getVal());
		hiTask.setStartTime(execution.getStartTime());
		hiTask.setExpiryDays(0);
		hiTask.setTaskId(0);
		hiTaskDao.insertHiTask(hiTask);		
	}
	
	@Override
	public void insertTaskHistory(GeExecution execution, RuTask ruTask,int resultCode) {
		hiTaskDao.insertHiTask(getHitask(execution, ruTask, resultCode,null));
	}
	
	@Override
	public void insertTaskHistory(GeExecution execution,RuTask ruTask,ApproveResult approveResult) {
		hiTaskDao.insertHiTask(getHitask(execution, ruTask, approveResult.getApproveType(), approveResult.getApproveDesc()));
	}
	
	@Override
	public List<HiTaskVo> listHiTaskForIndex(HiTask hiTask) throws DataAccessException {
		hiTask.setLimit(10);
		return hiTaskDao.listHiTaskForIndex(hiTask);
	}
	@Override
	public  List<HiTaskVo> listHiTask(GeExecution exectuion) throws DataAccessException {
		return  hiTaskDao.listHiTask(exectuion);
	}
	@Override
	public Integer listHiTaskCount(GeExecution exectuion)
			throws DataAccessException {
		return hiTaskDao.listHiTaskCount(exectuion);
	}
	@Override
	public List<ApproveResult> listApproveResult(Map<String, Object> params) {
		return hiTaskDao.listApproveResult(params);
	}
	
	@Override
	public boolean getIsApproved(Map<String, Object> params) {
		Integer count =  hiTaskDao.getApproveResultCnt(params);
		if(count != null && count > 0){
			return true;
		}
		return false;
	}
	
	private HiTask getHitask(GeExecution execution,RuTask ruTask,int resultType,String approveDesc){
		HiTask hiTask = new HiTask();
		hiTask.setExecutionId(execution.getExecutionId());
		hiTask.setTaskNum(execution.getTaskNum());
		hiTask.setConfId(ruTask.getConfId());
		hiTask.setVersion(execution.getVersion());
		hiTask.setAssignee(ruTask.getAssignee());
		if (ruTask.getDelegation()!=null) {
			hiTask.setDelegation(ruTask.getDelegation());
		}
		if(resultType < 20){
			hiTask.setRecordType(RecodeType.User.getVal());
		}else{
			hiTask.setRecordType(RecodeType.System.getVal());
		}
		hiTask.setOperateResult(resultType);
		hiTask.setApproveDesc(approveDesc);
		hiTask.setStartTime(ruTask.getArriveTime());
		hiTask.setExpiryDays(ruTask.getExpiryDays());
		hiTask.setApproveType(1);
		hiTask.setTaskId(ruTask.getTaskId());
		return hiTask;
	}
	public void setHiTaskDao(HiTaskDao hiTaskDao) {
		this.hiTaskDao = hiTaskDao;
	}

}
