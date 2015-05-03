package com.chz.smartoa.common.schedule.dao;

import java.util.List;
import java.util.Map;

import com.chz.smartoa.common.schedule.pojo.ScheduleTask;

public interface TaskDao{

	public List<ScheduleTask> getList();

	public List<ScheduleTask> search(Map<String,Object> param);

	public ScheduleTask getTask(String id);
	
	public ScheduleTask addTask(ScheduleTask task);
	
	public void updateTask(ScheduleTask task);
	
	public void updateTime(String id,String express);
	
	public void updateStatus(String id,int status);
	
	public void deleteTask(String id);
	
}
