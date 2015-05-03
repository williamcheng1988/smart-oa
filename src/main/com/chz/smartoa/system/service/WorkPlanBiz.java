package com.chz.smartoa.system.service;

import java.sql.SQLException;
import java.util.List;

import com.chz.smartoa.system.pojo.WorkPlan;

public interface WorkPlanBiz {

	public void insertWorkPlan(WorkPlan workPlan) throws SQLException;
	
	public void deleteWorkPlan(Long id) throws SQLException;
	
	public void updateWorkPlan(WorkPlan workPlan) throws SQLException;
	
	public WorkPlan getWorkPlanById(Long id);
	
	public List<WorkPlan> getWorkPlanByDate(String selectDate,String loginName);
	
	public List<WorkPlan> getWorkDateByBetweenDate(WorkPlan workPlan);
}
