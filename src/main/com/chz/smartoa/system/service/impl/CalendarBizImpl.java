package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.system.dao.CalendarDao;
import com.chz.smartoa.system.pojo.Calendar;
import com.chz.smartoa.system.service.CalendarBiz;


public class CalendarBizImpl implements CalendarBiz{
	
	private CalendarDao calendarDao;
	public CalendarDao getCalendarDao() {
		return calendarDao;
	}
	public void setCalendarDao(CalendarDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	
	@Override
	public void insertCalendar(Calendar calendar) {
		calendarDao.insertCalendar(calendar);
	}

	@Override
	public void deleteCalendar(Long id) {
		calendarDao.deleteCalendar(id);
	}

	@Override
	public void updateCalendar(Calendar calendar) {
		calendarDao.updateCalendar(calendar);
	}
	
	@Override
	public Calendar findCalendarById(Long id) {
		return calendarDao.findCalendarById(id);
	}

	@Override
	public List<Calendar> getCalendarByDate(String selectDate) {
		return calendarDao.getCalendarByDate(selectDate);
	}
	
	/**
	 * 获取当前月的前一个月和后一个月的数据
	 */
	@Override
	public List<Calendar> queryCalendarForDate(Calendar calendar) {
		return calendarDao.queryCalendarForDate(calendar);
	}
	

}
