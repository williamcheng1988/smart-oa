package com.chz.smartoa.dynamicForm.dao.ibatis;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.dynamicForm.dao.StaffBenefitsDao;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;

/**
 * DynamicFormImpl
 * @author William Cheng
 * @version 1.0
 * @time 14:30 2014/8/16
 */
public class StaffBenefitsDaoImpl extends SqlMapClientDaoSupport implements StaffBenefitsDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(StaffBenefitsDaoImpl.class);
    
    
    @Override
	public void insertStaffWages(StaffWages staffWages) throws DataAccessException {
		getSqlMapClientTemplate().insert("Form_insertStaffWages", staffWages);
	}
    
	@Override
	public void insertStaffBenefits(StaffBenefits staffBenefits)
			throws DataAccessException {
		getSqlMapClientTemplate().insert("Form_insertStaffBenefits", staffBenefits);
	}

	@Override
	public void updateStaffWages(StaffWages staffWages) throws DataAccessException {
		getSqlMapClientTemplate().update("Form_updateStaffWages", staffWages);
	}
	
	public void deleteStaffBenefits(StaffBenefits StaffBenefits) throws DataAccessException {
		getSqlMapClientTemplate().update("Form_deleteStaffBenefitsById", StaffBenefits);
	}
	@Override
	public void updateStaffBenefits(StaffBenefits staffBenefits)
			throws DataAccessException {
		getSqlMapClientTemplate().update("Form_updateStaffBenefits", staffBenefits);
	}
	
	public StaffWages findStaffWagesById(StaffWages staffWages) throws DataAccessException {
		return (StaffWages)getSqlMapClientTemplate().queryForObject("Form_findStaffWagesById", staffWages);
	}

	@Override
	public List<StaffBenefits> findStaffBenefitsById(StaffBenefits benefits)
			throws DataAccessException {
		List<StaffBenefits> bfs =
				(List<StaffBenefits>) getSqlMapClientTemplate().queryForList("Form_findStaffBenefitsById", benefits);
		return bfs;
	}

	@Override
	public List<StaffWages> findWagesByTmpId(StaffWages staffWages) throws DataAccessException{
		
		return (List<StaffWages>)getSqlMapClientTemplate().queryForList("Form_findStaffWages", staffWages);
	}
}
