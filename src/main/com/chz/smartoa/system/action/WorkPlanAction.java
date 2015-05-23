package com.chz.smartoa.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.pojo.WorkPlan;
import com.chz.smartoa.system.service.WorkPlanBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WorkPlanAction extends BaseAction{

	private static final long serialVersionUID = -1908818054755113299L;
	
	private static final Logger logger = Logger.getLogger(WorkPlanAction.class);
	
	private WorkPlanBiz workPlanBiz;
	
	private List<WorkPlan> wpList;
	
	private WorkPlan workPlan;
	
	private String jsonStr;
	
	private String msg;
	
	private String workDate;
	private String isUpdate;
	private String selectHour;
	private String selectMin;
	private String noticeType;
	private String id;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 查询工作日程数据列表
	 * @return
	 */
	public String queryWorkPlan(){
		try {
			if(StringUtils.isNotBlank(workDate)){
				wpList = workPlanBiz.getWorkPlanByDate(workDate,getLoginStaff().getLoginName());
			}else{
				String currentDate = format.format(new Date());
				wpList = workPlanBiz.getWorkPlanByDate(currentDate,getLoginStaff().getLoginName());
			}
			dataGrid = new DataGrid(1,wpList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	/**
	 * 保存新增或修改后的工作日程记录数据
	 * @return
	 */
	public String save(){
		if(StringUtils.isNotBlank(isUpdate)){
			return this.saveModify();
		}else{
			return this.saveAdd();
		}
	}
	
	
	/**
	 * 保存新增的工作日程记录
	 * @return
	 */
	public String saveAdd(){
		try {
			if(workPlan != null){
				workPlan.setCreateUser(getLoginStaff().getLoginName());
				workPlan.setUpdateUser(getLoginStaff().getLoginName());
				workPlan.setWorkTime(selectHour + ":" + selectMin);
				workPlan.setNoticeType(noticeType);
				workPlanBiz.insertWorkPlan(workPlan);
				msg = "true";
			}else{
				msg = "false";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		
		if(msg.equals("true")){
			operateResult = new OperateResult(1, "日程记录保存成功！");
		}else{
			operateResult = new OperateResult(-1, "日程记录保存失败，请稍候重试!");
		}
		return OPER_RESULT;
	}
	
	
	/**
	 * 保存修改后的工作日程记录
	 * @return
	 */
	public String saveModify(){
		try {
			WorkPlan wPlan = workPlanBiz.getWorkPlanById(Long.valueOf(id));
			if(wPlan != null){
				wPlan.setWorkTime(selectHour + ":" + selectMin);
				wPlan.setNoticeType(noticeType);
				wPlan.setWorkDesc(workPlan.getWorkDesc());
				workPlanBiz.updateWorkPlan(wPlan);
				msg = "true";
			}else{
				msg = "false";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		if(msg.equals("true")){
			operateResult = new OperateResult(1, "日程记录保存修改成功！");
		}else{
			operateResult = new OperateResult(-1, "日程记录保存修改失败，请稍候重试!");
		}
		return OPER_RESULT;
	}
	
	
	public String toEidt(){
		WorkPlan pageWorkPlan = new WorkPlan();
		try {
			workPlan = workPlanBiz.getWorkPlanById(workPlan.getId());
			String workTime = workPlan.getWorkTime();
			if(StringUtils.isNotBlank(workTime)){
				selectHour = workTime.substring(0, 2);
				selectMin = workTime.substring(3, 5);
			}else{
				selectHour = null;
				selectMin = null;
			}
			
			pageWorkPlan.setSelectHour(selectHour);
			pageWorkPlan.setSelectMin(selectMin);
			pageWorkPlan.setWorkDesc(workPlan.getWorkDesc());
			pageWorkPlan.setNoticeType(workPlan.getNoticeType());
			msg = "true";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		pageWorkPlan.setMsg(msg);
		entry = pageWorkPlan;
		return ENTRY;
	}
	
	
	/**
	 * 删除工作日程记录
	 * @return
	 */
	public String delete(){
		try {
			workPlanBiz.deleteWorkPlan(workPlan.getId());
			msg = "true";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		if(msg.equals("true")){
			operateResult = new OperateResult(1, "日程记录删除成功！");
		}else{
			operateResult = new OperateResult(-1, "日程记录删除失败，请稍候重试!");
		}
		return OPER_RESULT;
	}
	
	
	/**
	 * 用于区分日历界面显示的不同：日程安排显示不同的样式(当前月份的前后一个月已设定的假日数据)
	 * @return
	 */
	public String queryWorkDateForDate(){
		List<String> dateSetList = null;
		try {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.add(java.util.Calendar.MONTH, -1); 
			String startDate = new SimpleDateFormat("yyyy-MM").format(c.getTime()) + "-" + "01";
			c.add(java.util.Calendar.MONTH, 3); 
			String endDate = new SimpleDateFormat("yyyy-MM").format(c.getTime()) + "-" + "01";
			WorkPlan wkplan = new WorkPlan();
			wkplan.setStartDt(startDate);
			wkplan.setEndDt(endDate);
			wkplan.setCreateUser(getLoginStaff().getLoginName());
			List<WorkPlan> workPlanList = workPlanBiz.getWorkDateByBetweenDate(wkplan);
			if(workPlanList != null && workPlanList.size() >= 1){
				dateSetList = new ArrayList<String>();
				for(int i=0;i<workPlanList.size();i++){
					dateSetList.add(format.format(workPlanList.get(i).getWorkDate()));
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
	 * 首页显示当天最新工作安排
	 * @return
	 */
	public String firstPageForNewWorkPlan(){
		try {
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			List<WorkPlan> workPlanList = workPlanBiz.getWorkPlanByDate(currentDate, getLoginStaff().getLoginName());
			if(workPlanList != null && workPlanList.size() >= 1){
				for(int i=0;i<workPlanList.size();i++){
					if(StringUtils.isNotBlank(workPlanList.get(i).getWorkTime())){
						if(workPlanList.get(i).getWorkDesc().length() > 8){
							workPlanList.get(i).setWorkDesc(workPlanList.get(i).getWorkDesc().substring(0, 6) + "...");
						}
						workPlanList.get(i).setWorkTime(currentDate + " " + workPlanList.get(i).getWorkTime());
					}
				}
			}
			dataList = workPlanList;
			//Gson gson = new GsonBuilder().create();
			//jsonStr = gson.toJson(workPlanList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		//return "json";
		
		return DATA_LIST;
	}
	
	
	/**
	 * 首页查看
	 * @return
	 */
	public String firstPgeeForSeeContent(){
		try {
			WorkPlan wp = workPlanBiz.getWorkPlanById(workPlan.getId());
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(wp.getWorkDesc());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	

	public WorkPlanBiz getWorkPlanBiz() {
		return workPlanBiz;
	}

	public void setWorkPlanBiz(WorkPlanBiz workPlanBiz) {
		this.workPlanBiz = workPlanBiz;
	}

	public List<WorkPlan> getWpList() {
		return wpList;
	}

	public void setWpList(List<WorkPlan> wpList) {
		this.wpList = wpList;
	}
	public WorkPlan getWorkPlan() {
		return workPlan;
	}
	public void setWorkPlan(WorkPlan workPlan) {
		this.workPlan = workPlan;
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
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getSelectHour() {
		return selectHour;
	}
	public void setSelectHour(String selectHour) {
		this.selectHour = selectHour;
	}
	public String getSelectMin() {
		return selectMin;
	}
	public void setSelectMin(String selectMin) {
		this.selectMin = selectMin;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
