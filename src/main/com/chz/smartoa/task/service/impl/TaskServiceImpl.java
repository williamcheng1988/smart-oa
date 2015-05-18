package com.chz.smartoa.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.chz.smartoa.task.dao.GeExecutionDao;
import com.chz.smartoa.task.dao.RuTaskDao;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.pojo.RuTaskVo;
import com.chz.smartoa.task.service.TaskService;

public class TaskServiceImpl implements TaskService {
	
	private RuTaskDao ruTaskDao;
	private GeExecutionDao geExecutionDao;
	
	@Override
	public List<GeExecution> listDrafts(String userName) {
		return ruTaskDao.listDrafts(userName);
	}
	
	@Override
	public int listTodoTaskCount(String userName) {
		RuTaskVo task = new RuTaskVo();
		task.setOwner(userName);
		return ruTaskDao.listTodoTaskCount(task);
	}

	@Override
	public List<RuTaskVo> listTodoTask(String userName) {
		RuTaskVo task = new RuTaskVo();
		task.setOwner(userName);
		return ruTaskDao.listTodoTask(task);
	}
	
	@Override
	public List<RuTaskVo> listTodoTask(String userName, String sort,String order) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("owner", userName);
		if("businessKey".equals(sort)){
			sort = "ge_e.BUSINESS_KEY_";
		}else if("realName".equals(sort)){
			sort = "S.REAL_NAME";
		}else if("startTime".equals(sort)){
			sort = "ge_e.start_time_ ";
		}else{
			sort = "ARRIVE_TIME_";
		}
		params.put("sort", sort);
		if(StringUtils.isEmpty(order)){
			order = "desc";
		}
		params.put("order", order);
		return ruTaskDao.listTodoTask(params);
	}
	@Override
	public Map<String, Object> getTaskExecution(String executionId) {
		return geExecutionDao.findTaskGeExecution(executionId);
	}
	
	@Override
	public Map<String, Object> getTodoTask(String taskId,String user) {
		return ruTaskDao.getTodoTask(taskId,user);
	}
	@Override
	public Integer getCurrentSortNum(String executionId) {
		return geExecutionDao.getCurrentSortNum(executionId);
	}
	@Override
	public Integer getNextSortNum(String executionId) {
		return geExecutionDao.getNextSortNum(executionId);
	}
	@Override
	public RuTask getRutask(String taskId) {
		return ruTaskDao.findRuTask(taskId);
	}
	@Override
	public boolean getIsNext(String executionId) {
		Integer cnt  = ruTaskDao.getTodoTaskCountForExecution(executionId);
		if(cnt != null && cnt >0){
			return false;
		}
		return true;
	}
	@Override
	public GeExecution getGeExecution(String executionId) {
		return geExecutionDao.findGeExecution(executionId);
	}

	public void setRuTaskDao(RuTaskDao ruTaskDao) {
		this.ruTaskDao = ruTaskDao;
	}
	public void setGeExecutionDao(GeExecutionDao geExecutionDao) {
		this.geExecutionDao = geExecutionDao;
	}
	
}
