package com.chz.smartoa.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.pojo.Calendar;
import com.chz.smartoa.system.service.CalendarBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CalendarAction extends BaseAction{

	private static final long serialVersionUID = 4510595660177002081L;
	
	private static final Logger logger = Logger.getLogger(CalendarAction.class);
	
	private CalendarBiz calendarBiz;
	
	private List<Calendar> caList;
	
	private Calendar calendar;
	
	private String jsonStr;
	
	private String msg;
	
	private String selectDate;
	private String id;
	private String isUpdate;
	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 查询日历数据列表
	 * @return
	 */
	public String queryCalendar(){
		try {
			if(StringUtils.isNotBlank(selectDate)){
				caList = calendarBiz.getCalendarByDate(selectDate);
			}else{
				String currentDate = format.format(new Date());
				caList = calendarBiz.getCalendarByDate(currentDate);
			}
			dataGrid = new DataGrid(1,caList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	/**
	 * 用于区分日历界面显示的不同：假日管理显示不同的背景色(当前月份的前后一个月已设定的假日数据)
	 * @return
	 */
	public String queryCalendarForDate(){
		List<String> dateSetList = null;
		try {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.add(java.util.Calendar.MONTH, -1); 
			String startDate = new SimpleDateFormat("yyyy-MM").format(c.getTime()) + "-" + "01";
			c.add(java.util.Calendar.MONTH, 3); 
			String endDate = new SimpleDateFormat("yyyy-MM").format(c.getTime()) + "-" + "01";
			Calendar cal = new Calendar();
			cal.setStartDt(startDate);
			cal.setEndDt(endDate);
			List<Calendar> calendarList = calendarBiz.queryCalendarForDate(cal);
			if(calendarList != null && calendarList.size() >= 1){
				dateSetList = new ArrayList<String>();
				for(int i=0;i<calendarList.size();i++){
					dateSetList.add(format.format(calendarList.get(i).getDateSet()));
				}
			}
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if(msg.equals("true")){
			if(dateSetList != null && dateSetList.size() >= 1){
				Gson gson = new GsonBuilder().create();
				jsonStr = gson.toJson(dateSetList);
			}
		}
		return "json";
	}
	
	
	/**
	 * 保存新增或修改后的日历记录数据
	 * @return
	 */
	public String save(){
		if(StringUtils.isNotBlank(id)){
			return this.saveModify();
		}else{
			return this.saveAdd();
		}
	}
	
	
	/**
	 * 保存新增的日历记录数据
	 * @return
	 */
	public String saveAdd(){
		String sDate = null;
		try {
			if(calendar != null){
				if(calendar.getDateSet() != null){
					sDate = format.format(calendar.getDateSet());
					List<Calendar> cList = calendarBiz.getCalendarByDate(sDate);
					if(cList != null && cList.size() >= 2){
						msg = "manyData";
					}else{
						calendar.setCreateUser(getLoginStaff().getLoginName());
						calendar.setUpdateUser(getLoginStaff().getLoginName());
						calendarBiz.insertCalendar(calendar);
						msg = "true";
					}
				}
			}else{
				msg = "false";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		Calendar cdar = new Calendar();
		cdar.setMsg(msg);
		if(msg.equals("true")){
			List<Calendar> calList = calendarBiz.getCalendarByDate(sDate);
			cdar.setId(calList.get(0).getId());
		}
		entry = cdar;
		return ENTRY;
	}
	
	
	/**
	 * 保存修改后的日历记录数据
	 * @return
	 */
	public String saveModify(){
		try {
			Calendar ca = calendarBiz.findCalendarById(Long.valueOf(id));
			if(ca != null ){
				ca.setHolidayType(calendar.getHolidayType());
				ca.setHolidayDesc(calendar.getHolidayDesc());
				ca.setUpdateUser(getLoginStaff().getLoginName());
				calendarBiz.updateCalendar(ca);
				msg = "true";
			}else{
				msg = "false";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		Calendar cdar = new Calendar();
		cdar.setMsg(msg);
		cdar.setId(Long.valueOf(id));
		entry = cdar;
		return ENTRY;
	}
	
	
	/**
	 * 删除日历记录数据
	 * @return
	 */
	public String delete(){
		try {
			calendarBiz.deleteCalendar(Long.valueOf(id));
			msg = "true";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		if(msg.equals("true")){
			operateResult = new OperateResult(1, "日历记录删除成功！");
		}else{
			operateResult = new OperateResult(-1, "删除失败，请稍候重试!");
		}
		return OPER_RESULT;
	}
	
	
	public String findCalendar(){
		Calendar cdar = null;
		if(StringUtils.isNotBlank(selectDate)){
			List<Calendar> calList = calendarBiz.getCalendarByDate(selectDate);
			if(calList != null && calList.size() == 1){
				msg = "true";
				cdar = calList.get(0);
			}else{
				msg = "false";
			}
		}else{
			msg = "false";
		}
		if(cdar == null){
			cdar = new Calendar();
		}
		cdar.setMsg(msg);
		entry = cdar;
		return ENTRY;
	}
	
	
	
	
	
	
	public CalendarBiz getCalendarBiz() {
		return calendarBiz;
	}

	public void setCalendarBiz(CalendarBiz calendarBiz) {
		this.calendarBiz = calendarBiz;
	}

	public List<Calendar> getCaList() {
		return caList;
	}

	public void setCaList(List<Calendar> caList) {
		this.caList = caList;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

}
