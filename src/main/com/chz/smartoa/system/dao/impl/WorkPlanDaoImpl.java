package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.WorkPlanDao;
import com.chz.smartoa.system.pojo.WorkPlan;


public class WorkPlanDaoImpl extends SqlMapClientDaoSupport implements WorkPlanDao{
	

	@Override
	public void insertWorkPlan(WorkPlan workPlan) {
		getSqlMapClientTemplate().insert("wplan_insertWplan", workPlan);
	}

	@Override
	public void deleteWorkPlan(Long id) {
		getSqlMapClientTemplate().delete("wplan_deleteWplan",id);
	}

	@Override
	public void updateWorkPlan(WorkPlan workPlan) {
		getSqlMapClientTemplate().update("wplan_updateWplan", workPlan);
	}

	@Override
	public WorkPlan getWorkPlanById(Long id) {
		WorkPlan wp = (WorkPlan) getSqlMapClientTemplate().queryForObject("wplan_findWplanById", id);
		return wp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkPlan> getWorkPlanByDate(WorkPlan workPlan) {
		List<WorkPlan> wpList = (List<WorkPlan>)getSqlMapClientTemplate().queryForList("wplan_findWplanByDate", workPlan);
		return wpList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkPlan> getWorkDateByBetweenDate(WorkPlan workPlan) {
		List<WorkPlan> wpList = (List<WorkPlan>)getSqlMapClientTemplate().queryForList("wplan_workDateByBetweenDate", workPlan);
		return wpList;
	}

}
