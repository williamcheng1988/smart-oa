package com.chz.smartoa.dynamicForm.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;

/**
 * DynamicFormBiz
 * @author William Cheng
 * @version 1.0
 * @time 15:29 2014/8/16
 */
public interface StaffBenefitsBiz {
	
	/**
     * insert.
     * @param staffBenefits
     * @throws DataAccessException DataAccessException
     */
	void insertWage(Wage wage) throws DataAccessException ;
    
    /**
     * update.
     * @param staffBenefits
     * @return
     * @throws DataAccessException DataAccessException
     */
    void updateWage(Wage wage) throws DataAccessException ;

    /**
     * find wage
     * @param staffWages
     * @return
     * @throws DataAccessException
     */
    Wage findWageById(StaffWages staffWages) throws DataAccessException ;
    /**
     * find by tmpId
     * @param staffWages
     * @return
     * @throws DataAccessException
     */
    List<Wage> findWagesByTmpId(StaffWages staffWages) throws DataAccessException;
}
