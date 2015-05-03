package com.chz.smartoa.system.dao;

import java.util.List;

import com.chz.smartoa.system.pojo.WorkPlan;


public interface WorkPlanDao {

	public void insertWorkPlan(WorkPlan workPlan);
	public void deleteWorkPlan(Long id);
	public void updateWorkPlan(WorkPlan workPlan);
	public WorkPlan getWorkPlanById(Long id);
	public List<WorkPlan> getWorkPlanByDate(WorkPlan workPlan);
	public List<WorkPlan> getWorkDateByBetweenDate(WorkPlan workPlan);
	
}
