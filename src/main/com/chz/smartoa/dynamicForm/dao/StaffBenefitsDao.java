package com.chz.smartoa.dynamicForm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.pojo.StaffBenefits;
import com.chz.smartoa.dynamicForm.pojo.StaffWages;
import com.chz.smartoa.dynamicForm.pojo.Wage;


/**
 * DynamicFormDao
 * @author William Cheng
 * @version 1.0
 * @time 14:29 2014/8/16
 */
public interface StaffBenefitsDao {
	
	/**
	 * insert 
	 * @param staffWages
	 * @throws DataAccessException
	 */
	void insertStaffWages(StaffWages staffWages) throws DataAccessException ;
	
	/**
     * insert.
     * @param staffBenefits
     * @throws DataAccessException DataAccessException
     */
	void insertStaffBenefits(StaffBenefits staffBenefits) throws DataAccessException ;
	
	/**
     * update.
     * @param staffWages
     * @return
     * @throws DataAccessException DataAccessException
     */
    void updateStaffWages(StaffWages staffWages) throws DataAccessException ;

    /**
     * delete StaffBenefits by id
     * @param StaffBenefits
     * @throws DataAccessException
     */
	void deleteStaffBenefits(StaffBenefits StaffBenefits) throws DataAccessException ;
    /**
     * update.
     * @param staffBenefits
     * @return
     * @throws DataAccessException DataAccessException
     */
    void updateStaffBenefits(StaffBenefits StaffBenefits) throws DataAccessException ;
    /**
     * find staff wage by id
     * @param staffWages
     * @return staffWages
     * @throws DataAccessException
     */
    StaffWages findStaffWagesById(StaffWages staffWages) throws DataAccessException ;
    
    /**
     * find.
     * @param benefits
     * @return FormTemplateType
     * @throws DataAccessException DataAccessException
     */
    List<StaffBenefits> findStaffBenefitsById(StaffBenefits benefits) throws DataAccessException ;
    
    
    List<StaffWages> findWagesByTmpId(StaffWages staffWages) throws DataAccessException;
    
}
