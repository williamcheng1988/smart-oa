package com.chz.smartoa.dynamicForm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;

/**
 * DynamicFormBiz
 * @author William Cheng
 * @version 1.0
 * @time 15:29 2014/8/16
 */
public interface DynamicFormBiz {
	
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
    void deleteFormTemplateType(List<String> ids) throws DataAccessException ;
    
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
     * delete Template
     *  @param delFlag : 
     * 			true : delete the data
     * 			false : suspend the data 
     * @throws DataAccessException DataAccessException
     */
    void deleteFormTemplate(String id,boolean delFlag) throws DataAccessException ;
    
    /**
     * delete Templates
     * @param ids : keys of templates
     * @param delFlag : 
     * 			true : delete the data
     * 			false : suspend the data  
     * @throws DataAccessException
     */
    void deleteFormTemplates(List<String> ids,boolean delFlag) throws DataAccessException ;
    
    /**
     * update.
     * @param FormTemplate
     * @throws DataAccessException DataAccessException
     */
    void updateFormTemplate(FormTemplate formTemplate) throws DataAccessException ;

    /**
     * find.
     * @param id id
     * @return FormTemplate
     * @throws DataAccessException DataAccessException
     */
    FormTemplate findFormTemplate(String id) throws DataAccessException ;

    /**
     * find list FormTemplate
     * @param formTemplate
     * @return List<FormTemplate>
     * @throws DataAccessException
     */
    public List<FormTemplate> listFormTemplate(FormTemplate formTemplate) throws DataAccessException;
    
    /**
     * listFormTemplateCount.
     * @param formTemplate formTemplate
     * @return formTemplate count
     * @throws DataAccessException DataAccessException
     */
    public Integer listFormTemplateCount(FormTemplate formTemplate) throws DataAccessException;
    
    /**
     * 返回所有 模板  按照序号排序之后的  分组集合
     * @param formTemplate
     * @return
     * @throws DataAccessException
     */
    public Map<FormTemplateType,List<FormTemplate>> listFormTemplateByTypeSeq(FormTemplate formTemplate) throws DataAccessException;
    
    
    /**
     * insert.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
	void insertForm(FormRecord formRecord) throws DataAccessException ;
	
    /**
     * delete Form
     *  @param delFlag : 
     * 			true : delete the data
     * 			false : suspend the data 
     * @throws DataAccessException DataAccessException
     */
    boolean deleteForm(String formRecordId,boolean delFlag) throws DataAccessException ;
    
    /**
     * delete Forms
     * @param ids : keys of formRecords
     * @param delFlag : 
     * 			true : delete the data
     * 			false : suspend the data  
     * @throws DataAccessException
     */
    void deleteForms(List<Long> ids,boolean delFlag) throws DataAccessException ;
    
    /**
     * update form.
     * @param Form
     * @throws DataAccessException DataAccessException
     */
    void updateForm(FormRecord formRecord) throws DataAccessException ;

    /**
     * find Forms:只有查询方法才是用 recordId 对记录进行搜索，其余的 删、改方法，都会根据 uuid去真正指定某条记录
     * @param id
     * @return FormRecord
     * @throws DataAccessException
     */
    FormRecord findForm(String formRecordId) throws DataAccessException ;
    
    /**
     * only get FormRecord except for FormProp
     * @param recordId
     * @return FormRecord
     * @throws DataAccessException
     */
    FormRecord findFormRecord(String recordId) throws DataAccessException;

    /**
     * find list Form
     * @param formRecord
     * @return List<FormRecord>
     * @throws DataAccessException
     */
    public List<FormRecord> listForm(FormRecord formRecord) throws DataAccessException;
    
    /**
     * 根据 模板 ID 查找相关表单记录数
     * @param templateId
     * @return Integer
     * @throws DataAccessException
     */
    public Integer listFormRecordCount(String templateId) throws DataAccessException;
    /**
     * 根据 formRecord 查找相关表单记录数
     * @param templateId
     * @return Integer
     * @throws DataAccessException
     */
    public Integer listFormRecordCount(FormRecord formRecord) throws DataAccessException;
    
    /**
     * find count of FormRecord by docNoRule or other parameters
     * @param formRecord
     * @return
     * @throws DataAccessException
     */
    public Integer countByParam(String sql,Map map) throws DataAccessException;
    
    /**
     * find FormRecord by tmpIds
     * @param tmpIds
     * @return
     * @throws DataAccessException
     */
    public List<FormRecord> listProjects() throws DataAccessException;
}
