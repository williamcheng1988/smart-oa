package com.chz.smartoa.dynamicForm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.dao.StaffBenefitsDao;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;
import com.chz.smartoa.dynamicForm.service.StaffBenefitsBiz;

/**
 * StaffBenefitsBizImpl
 * @author William Cheng
 * @version 1.0
 * @time 0:10 2015/4/29
 */
public class StaffBenefitsBizImpl implements StaffBenefitsBiz {
	/**
	 * logger.
	 */
	private static final Logger logger = Logger.getLogger(StaffBenefitsBizImpl.class);
	
	private StaffBenefitsDao staffBenefitsDao;
	
	public StaffBenefitsDao getStaffBenefitsDao() {
		return staffBenefitsDao;
	}
	public void setStaffBenefitsDao(StaffBenefitsDao staffBenefitsDao) {
		this.staffBenefitsDao = staffBenefitsDao;
	}

	@Override
	public void insertWage(Wage wage)
			throws DataAccessException {
		logger.info("StaffBenefitsBizImpl>>insertStaffBenefits:step in...");

		//工资总信息
		StaffWages staffWages = wage.getTotal();
		//工资详细信息
		List<StaffBenefits> staffBenefits = wage.getDetail();
		
		if(staffWages==null){
			logger.info("StaffBenefitsBizImpl>>insertStaffBenefits:wages is null...");
		}
		if(staffBenefits==null || staffBenefits.size()==0){
			logger.info("StaffBenefitsBizImpl>>insertStaffBenefits:staffBenefits is null...");
			return;
		}
		
		staffBenefitsDao.insertStaffWages(staffWages);
		
		for(StaffBenefits bf : staffBenefits){
			bf.setId(staffWages.getUuid());
			staffBenefitsDao.insertStaffBenefits(bf);
		}
		logger.info("StaffBenefitsBizImpl>>insertStaffBenefits:step out...");
	}

	@Override
	public void updateWage(Wage wage)
			throws DataAccessException {
		logger.info("StaffBenefitsBizImpl>>updateStaffBenefits : step in...");
		
		//工资总信息
		StaffWages staffWages = wage.getTotal();
		//工资详细信息
		List<StaffBenefits> staffBenefits = wage.getDetail();
		
		if(staffWages==null){
			logger.info("StaffBenefitsBizImpl>>insertStaffBenefits:wages is null...");
		}
		if(staffBenefits==null || staffBenefits.size()==0){
			logger.info("StaffBenefitsBizImpl>>updateStaffBenefits :staffBenefits is null...");
			return;
		}
		//修改主信息
		staffBenefitsDao.updateStaffWages(staffWages);
		
		/**
		 * 先删除，再增加
		 */
		//delete
		//insert 
		for(StaffBenefits bf : staffBenefits){
			bf.setId(staffWages.getUuid());
			staffBenefitsDao.deleteStaffBenefits(bf);
		}
		//insert
		for(StaffBenefits bf : staffBenefits){
			bf.setId(staffWages.getUuid());
			staffBenefitsDao.insertStaffBenefits(bf);
		}
		logger.info("StaffBenefitsBizImpl>>updateStaffBenefits :step out...");
	}

	@Override
	public Wage findWageById(StaffWages staffWages) throws DataAccessException {
		logger.info("StaffBenefitsBizImpl>>findStaffBenefitsById : step in...");
		
		Wage wage = new Wage();
		
		StaffWages w = staffBenefitsDao.findStaffWagesById(staffWages);
		wage.setTotal(w);
		wage.setDetail(staffBenefitsDao.findStaffBenefitsById(new StaffBenefits(w.getUuid())));
		
		logger.info("StaffBenefitsBizImpl>>findStaffBenefitsById : step out...");
		return wage;
	}
	
	@Override
	public List<Wage> findWagesByTmpId(StaffWages staffWages) throws DataAccessException {
		logger.info("StaffBenefitsBizImpl>>findWagesByTmpId : step in...");
		
		List<Wage> wages = new ArrayList<Wage>();
		StaffWages sw = new StaffWages();
		sw.setTemplateId(staffWages.getTemplateId());
		List<StaffWages> ws = staffBenefitsDao.findWagesByTmpId(sw);
		if(ws!=null && ws.size()>0){
			for(StaffWages w : ws){
				Wage wage = new Wage();
				wage.setTotal(w);
				wage.setDetail(staffBenefitsDao.findStaffBenefitsById(new StaffBenefits(w.getUuid())));
				wages.add(wage);
			}
		}
		
		logger.info("StaffBenefitsBizImpl>>findWagesByTmpId : step out...");
		
		return wages;
	}
}
