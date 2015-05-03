package com.chz.smartoa.common.schedule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.common.schedule.dao.TaskDao;
import com.chz.smartoa.common.schedule.pojo.ScheduleTask;

public class TaskDaoImpl extends SqlMapClientDaoSupport implements TaskDao{
	

	@Override
	public List<ScheduleTask> getList() {
		return (List<ScheduleTask>)getSqlMapClientTemplate().queryForList("schedule_selectAll");
	}

	@Override
	public List<ScheduleTask> search(Map<String, Object> param) {
		return (List<ScheduleTask>)getSqlMapClientTemplate().queryForList("schedule_search");
	}

	@Override
	public ScheduleTask getTask(String id) {
		return (ScheduleTask)super.getSqlMapClientTemplate().queryForObject("schedule_selectByID",id);
	}

	@Override
	public ScheduleTask addTask(ScheduleTask task) {
		super.getSqlMapClientTemplate().insert("schedule_insert", task);
		return task;
	}

	@Override
	public void updateTask(ScheduleTask task) {
		super.getSqlMapClientTemplate().update("schedule_update", task);
	}

	@Override
	public void deleteTask(String id) {
		super.getSqlMapClientTemplate().delete("delete", id);
	}

	@Override
	public void updateTime(String id, String express) {
		
		Map param = new HashMap();
		param.put("id", id);
		param.put("express", express);
		super.getSqlMapClientTemplate().update("schedule_updateTime", param);
	}

	@Override
	public void updateStatus(String id, int status) {
		Map param = new HashMap();
		param.put("id", id);
		param.put("status", status);
		super.getSqlMapClientTemplate().update("schedule_updateStatus", param);

		
	}

}
