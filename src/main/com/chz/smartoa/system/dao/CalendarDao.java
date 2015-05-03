package com.chz.smartoa.system.dao;

import java.util.List;

import com.chz.smartoa.system.pojo.Calendar;


public interface CalendarDao {

	public void insertCalendar(Calendar calendar);
	public void deleteCalendar(Long id);
	public void updateCalendar(Calendar calendar);
	public Calendar findCalendarById(Long id);
	public List<Calendar> getCalendarByDate(String selectDate);
	public List<Calendar> queryCalendarForDate(Calendar calendar);
}
