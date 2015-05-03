package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.CalendarDao;
import com.chz.smartoa.system.pojo.Calendar;

public class CalendarDaoImpl extends SqlMapClientDaoSupport implements CalendarDao{
	

	@Override
	public void insertCalendar(Calendar calendar) {
		getSqlMapClientTemplate().insert("cdar_insertCdar", calendar);
	}

	@Override
	public void deleteCalendar(Long id) {
		getSqlMapClientTemplate().delete("cdar_deleteCdar",id);
	}

	
	@Override
	public void updateCalendar(Calendar calendar) {
		getSqlMapClientTemplate().update("cdar_updateCdar", calendar);
	}
	
	
	@Override
	public Calendar findCalendarById(Long id) {
		Calendar calendar = (Calendar) getSqlMapClientTemplate().queryForObject("cdar_findCdarById", id);
		return calendar;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Calendar> getCalendarByDate(String selectDate) {
		List<Calendar> cList = (List<Calendar>)getSqlMapClientTemplate().queryForList("cdar_findCdarByDate", selectDate);
		return cList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Calendar> queryCalendarForDate(Calendar calendar) {
		List<Calendar> cList = (List<Calendar>)getSqlMapClientTemplate().queryForList("cdar_findCdarByBetweenDate", calendar);
		return cList;
	}

	

}
