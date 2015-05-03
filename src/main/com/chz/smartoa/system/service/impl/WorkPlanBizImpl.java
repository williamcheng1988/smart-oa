package com.chz.smartoa.system.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chz.smartoa.system.dao.WorkPlanDao;
import com.chz.smartoa.system.pojo.Notice;
import com.chz.smartoa.system.pojo.WorkPlan;
import com.chz.smartoa.system.service.NoticeBiz;
import com.chz.smartoa.system.service.WorkPlanBiz;

public class WorkPlanBizImpl implements WorkPlanBiz{
	
	private WorkPlanDao workPlanDao;
	public WorkPlanDao getWorkPlanDao() {
		return workPlanDao;
	}
	public void setWorkPlanDao(WorkPlanDao workPlanDao) {
		this.workPlanDao = workPlanDao;
	}
	
	private NoticeBiz noticeBiz;
	public NoticeBiz getNoticeBiz() {
		return noticeBiz;
	}
	public void setNoticeBiz(NoticeBiz noticeBiz) {
		this.noticeBiz = noticeBiz;
	}
	
	
	
	@Override
	public void insertWorkPlan(WorkPlan workPlan) throws SQLException{
		Notice notice = new Notice();
		notice.setToPeople(workPlan.getCreateUser());
		notice.setTitle("日程提醒");
		String noticeType = workPlan.getNoticeType();
		if(StringUtils.isNotBlank(noticeType)){
			String[]noticeTypeArray = noticeType.split(",");
			for(int i=0;i<noticeTypeArray.length;i++){
				if("1".equals(noticeTypeArray[i])){
					notice.setStation(0);   // 站内通知
				}else if("2".equals(noticeTypeArray[i])){
					notice.setEmail(0);     // 邮件通知
				}
			}
		}
		notice.setContent(workPlan.getWorkDesc());
		String noticeId = noticeBiz.insert(notice);  // 添加通知
		workPlan.setNoticeId(noticeId);
		workPlanDao.insertWorkPlan(workPlan);        // 保存日程安排记录
		
	}

	@Override
	public void deleteWorkPlan(Long id) throws SQLException{
		noticeBiz.delete(this.getWorkPlanById(id).getNoticeId());
		workPlanDao.deleteWorkPlan(id);
	}

	@Override
	public void updateWorkPlan(WorkPlan workPlan) throws SQLException{
		// 新插入一条消息通知
		Notice notice = new Notice();
		notice.setToPeople(workPlan.getCreateUser());
		notice.setTitle("日程提醒");
		String noticeType = workPlan.getNoticeType();
		if(StringUtils.isNotBlank(noticeType)){
			String[]noticeTypeArray = noticeType.split(",");
			for(int i=0;i<noticeTypeArray.length;i++){
				if("1".equals(noticeTypeArray[i])){
					notice.setStation(0);   // 站内通知
				}else if("2".equals(noticeTypeArray[i])){
					notice.setEmail(0);     // 邮件通知
				}
			}
		}
		notice.setContent(workPlan.getWorkDesc());
		String noticeId = noticeBiz.insert(notice);  // 添加通知
		
		// 删除对应的消息通知
		noticeBiz.delete(workPlan.getNoticeId());
		
		workPlan.setNoticeId(noticeId);        // 重新更新关联通知ID
		workPlanDao.updateWorkPlan(workPlan);
		
	}

	@Override
	public WorkPlan getWorkPlanById(Long id) {
		return workPlanDao.getWorkPlanById(id);
	}

	@Override
	public List<WorkPlan> getWorkPlanByDate(String selectDate,String loginName) {
		WorkPlan wp = new WorkPlan();
		wp.setSelectDate(selectDate);
		wp.setCreateUser(loginName);
		return workPlanDao.getWorkPlanByDate(wp);
	}
	
	/**
	 * 获取当前月的前一个月和后一个月的数据
	 */
	@Override
	public List<WorkPlan> getWorkDateByBetweenDate(WorkPlan workPlan) {
		return workPlanDao.getWorkDateByBetweenDate(workPlan);
	}

}
