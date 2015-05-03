package com.chz.smartoa.dynamicForm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;


/**
 * DynamicFormDao
 * @author William Cheng
 * @version 1.0
 * @time 14:29 2014/8/16
 */
public interface DynamicFormDao {
	
	/**
     * insert.
     * @param FormTemplateType
     * @throws DataAccessException DataAccessException
     */
	void insertFormTemplateType(FormTemplateType formTemplateType) throws DataAccessException ;

    /**
     * delete.
     * @param FormTemplateType
     * @throws DataAccessException DataAccessException
     */
    void deleteFormTemplateType(String id) throws DataAccessException ;
    
    /**
     * update.
     * @param FormTemplateType
     * @return
     * @throws DataAccessException DataAccessException
     */
    void updateFormTemplateType(FormTemplateType formTemplateType) throws DataAccessException ;

    /**
     * find.
     * @param id id
     * @return FormTemplateType
     * @throws DataAccessException DataAccessException
     */
    FormTemplateType findFormTemplateType(FormTemplateType ft) throws DataAccessException ;
    
    /**
     * list.
     * @param FormTemplateType
     * @return FormTemplateType list
     * @throws DataAccessException DataAccessException
     */
    List<FormTemplateType> listFormTemplateType(FormTemplateType formTemplateType) throws DataAccessException ;
    
    /**
     * listFormTemplateTypeCount.
     * @param formTemplateType formTemplateType
     * @return formTemplateType count
     * @throws DataAccessException DataAccessException
     */
    public Integer listFormTemplateTypeCount(FormTemplateType formTemplateType) throws DataAccessException;	
	
    /**
     * insert.
     * @param FormTemplate
     * @throws DataAccessException DataAccessException
     */
	void insertFormTemplate(FormTemplate formTemplate) throws DataAccessException ;

    /**
     * delete.
     * @param FormTemplate
     * @throws DataAccessException DataAccessException
     */
    void deleteFormTemplate(String id) throws DataAccessException ;
    
    /**
     * update.
     * @param FormTemplate
     * @return FormTemplate
     * @throws DataAccessException DataAccessException
     */
    void updateFormTemplate(FormTemplate formTemplate) throws DataAccessException ;

    /**
     * find.
     * @param id id
     * @return FormTemplate
     * @throws DataAccessException DataAccessException
     */
    FormTemplate findFormTemplate(FormTemplate ft) throws DataAccessException ;
    
    /**
     * list.
     * @param FormTemplate
     * @return FormTemplate list
     * @throws DataAccessException DataAccessException
     */
    List<FormTemplate> listFormTemplate(FormTemplate formTemplate) throws DataAccessException ;
    
    /**
     * listFormTemplateCount.
     * @param formTemplate formTemplate
     * @return formTemplate count
     * @throws DataAccessException DataAccessException
     */
    public Integer listFormTemplateCount(FormTemplate formTemplate) throws DataAccessException ;
    
    /**
     * fetch formTemplates by form template type
     * @param formTemplate
     * @return List<FormTemplate>
     * @throws DataAccessException
     */
    public List<FormTemplate> listFormTemplateByTypeSeq(FormTemplate formTemplate) throws DataAccessException ;
    
    /**
     * insert formRecord.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
	void insertFormRecord(FormRecord formRecord) throws DataAccessException ;
	
	/**
     * insert formProp.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
	void insertFormProp(FormProp formProp) throws DataAccessException ;
	
	/**
     * delete.
     * @param FormTemplate
     * @throws DataAccessException DataAccessException
     */
    void deleteFormRecord(String id) throws DataAccessException ;
    
    /**
     * delete FormRecord
     *  @param delFlag : 
     * 			true : delete the data
     * 			false : suspend the data 
     * @throws DataAccessException DataAccessException
     */
    void deleteFormPropByFormRecordId(FormProp formProp) throws DataAccessException ;
    
    /**
     * update form.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
    void updateFormRecord(FormRecord formRecord) throws DataAccessException ;
    
    /**
     * update form.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
    void updateFormProp(FormProp formProp) throws DataAccessException ;

    /**
     * find FormRecord by record id.
     * @param id
     * @return FormRecord
     * @throws DataAccessException
     */
    FormRecord findFormRecord(String recordId) throws DataAccessException ;

    /**
     * find list formRecord
     * @param formRecord
     * @return List<FormRecord>
     * @throws DataAccessException
     */
    public List<FormRecord> listFormRecord(FormRecord formRecord) throws DataAccessException;
    
    /**
     * find list formProp
     * @param formRecord
     * @return List<FormProp>
     * @throws DataAccessException
     */
    public List<FormProp> listFormProp(FormProp formProp) throws DataAccessException;
    
    /**
     * find count of FormRecord
     * @param formRecord
     * @return
     * @throws DataAccessException
     */
    public Integer listFormRecordCount(FormRecord formRecord) throws DataAccessException;
    
    /**
     * find count of FormRecord by docNoRule
     * @param formRecord
     * @return
     * @throws DataAccessException
     */
    public Integer countByParam(String sql,Map<String,Object> map) throws DataAccessException;
    
    /**
     * find projects
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<FormRecord> listProjects() throws DataAccessException;
}
