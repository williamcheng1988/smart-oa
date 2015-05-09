package com.chz.smartoa.dynamicForm.dao.ibatis;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.dynamicForm.dao.DynamicFormDao;
import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;

/**
 * DynamicFormImpl
 * @author William Cheng
 * @version 1.0
 * @time 14:30 2014/8/16
 */
public class DynamicFormDaoImpl extends SqlMapClientDaoSupport implements DynamicFormDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(DynamicFormDaoImpl.class);

	@Override
	public void insertFormTemplateType(FormTemplateType formTemplateType)
			throws DataAccessException {
		getSqlMapClientTemplate().insert("FormTemplateType_insert", formTemplateType);
	}

	@Override
	public void deleteFormTemplateType(String id) throws DataAccessException {
		FormTemplateType formTemplateType = new FormTemplateType();
		formTemplateType.setId(id);
		
		getSqlMapClientTemplate().update("FormTemplateType_delete", formTemplateType);
	}

	@Override
	public void updateFormTemplateType(FormTemplateType formTemplateType)
			throws DataAccessException {
		getSqlMapClientTemplate().update("FormTemplateType_update", formTemplateType);
	}

	@Override
	public FormTemplateType findFormTemplateType(FormTemplateType ft)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("findFormTemplateType(ft), ["+ ft +"]");
    	}
		
		FormTemplateType formTemplateType = 
				(FormTemplateType) getSqlMapClientTemplate().queryForObject("FormTemplateType_find", ft);
		if (logger.isDebugEnabled()) {
        	logger.debug("findFormTemplateType(FormTemplateType), [" + formTemplateType + "]");
		}
        return formTemplateType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormTemplateType> listFormTemplateType(
			FormTemplateType formTemplateType) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		//logger.debug("listFormTemplateType(ft), ["+ formTemplateType + "]");
    	}

		List<FormTemplateType> formTemplateTypes = 
				(List<FormTemplateType>) getSqlMapClientTemplate().queryForList("FormTemplateType_list", 
						formTemplateType,formTemplateType.getStart(), formTemplateType.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("listFormTemplateType(formTemplateType), [返回记录：" + formTemplateTypes + "]");
		}
        return formTemplateTypes;
	}
	
	@Override
    public Integer listFormTemplateTypeCount(FormTemplateType formTemplateType) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	//logger.debug("进入listFormTemplateTypeCount(formTemplateType), 输入参数[" + formTemplateType + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("FormTemplateType_listCount", formTemplateType);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listFormTemplateTypeCount(formTemplateType), 返回[" + count + "]");
		}
        return count;
    }

	@Override
	public void insertFormTemplate(FormTemplate formTemplate)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("insertFormTemplate(formTemplate)>>"+formTemplate!=null?formTemplate.toString():"");
    	}
    	getSqlMapClientTemplate().insert("FormTemplate_insertFormTemplate", formTemplate);
	}

	@Override
	public void deleteFormTemplate(String id)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("deleteFormTemplate(id:"+id+")");
    	}
		
		FormTemplate formTemplate = new FormTemplate();
		formTemplate.setId(id);
		
		getSqlMapClientTemplate().update("FormTemplate_deleteFormTemplate", formTemplate);
	}

	@Override
	public void updateFormTemplate(FormTemplate formTemplate)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("updateFormTemplate(updateFormTemplate)");
    	}
		getSqlMapClientTemplate().update("FormTemplate_updateFormTemplate", formTemplate);
	}
	
	@Override
	public FormTemplate findFormTemplate(FormTemplate ft) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("findFormTemplate(ft), ["+ ft +"]");
    	}

		FormTemplate formTemplate = (FormTemplate) getSqlMapClientTemplate().queryForObject("FormTemplate_findFormTemplate", ft);
		if (logger.isDebugEnabled()) {
        	logger.debug("findFormTemplate(FormTemplate), [" + formTemplate + "]");
		}
        return formTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormTemplate> listFormTemplate(FormTemplate formTemplate) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("findFormTemplate(ft), ["+ formTemplate + "]");
    	}

		List<FormTemplate> formTemplates = (List<FormTemplate>) getSqlMapClientTemplate().queryForList("FormTemplate_listFormTemplate", formTemplate,formTemplate.getStart(), formTemplate.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("listFormTemplate(FormTemplate), [返回记录：" + formTemplates + "]");
		}
        return formTemplates;
	}
	
	@Override
    public Integer listFormTemplateCount(FormTemplate formTemplate) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listFormTemplateCount(formTemplate), 输入参数[" + formTemplate + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("FormTemplate_listFormTemplateCount", formTemplate);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listFormTemplateCount(formTemplate), 返回[" + count + "]");
		}
        return count;
    }
	
	public List<FormTemplate> listFormTemplateByTypeSeq(FormTemplate formTemplate) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("findFormTemplate(ft), ["+ formTemplate + "]");
    	}

		List<FormTemplate> formTemplates = (List<FormTemplate>) getSqlMapClientTemplate().queryForList("FormTemplate_listByTemplateTypeSeq", formTemplate,formTemplate.getStart(), formTemplate.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("listFormTemplate(FormTemplate), [返回记录：" + formTemplates + "]");
		}
        return formTemplates;
	}

	@Override
	public void insertFormRecord(FormRecord formRecord) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("insertForm(formRecord)>>"+formRecord!=null?formRecord.toString():"");
    	}
    	getSqlMapClientTemplate().insert("Form_insertFormRecord", formRecord);
	}
	@Override
	public void insertFormProp(FormProp formProp) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("insertFormProp(formProp)>>"+formProp!=null?formProp.toString():"");
    	}
    	getSqlMapClientTemplate().insert("Form_insertFormProp", formProp);
	}

	@Override
	public void deleteFormRecord(String uuid) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("deleteFormRecord(uuid:"+uuid+")");
    	}
		
		FormRecord formRecord = new FormRecord();
		formRecord.setUuId(uuid);
		
		getSqlMapClientTemplate().update("Form_deleteFormRecord", formRecord);
	}

	@Override
	public void deleteFormPropByFormRecordId(FormProp formProp) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("deleteFormProp(id:"+formProp+")");
    	}
		
		/*FormProp formProp = new FormProp();
		formProp.setId(id);*/
		
		getSqlMapClientTemplate().update("Form_deleteFormProp", formProp);
	}

	@Override
	public void updateFormRecord(FormRecord formRecord)
			throws DataAccessException {
		getSqlMapClientTemplate().update("Form_updateFormRecord", formRecord);
	}

	@Override
	public void updateFormProp(FormProp formProp) throws DataAccessException {
		getSqlMapClientTemplate().update("Form_updateFormProp", formProp);
	}

	@Override
	public FormRecord findFormRecord(String recordId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("findFormRecord(id:"+recordId+")");
    	}
		
		FormRecord fRecord = new FormRecord();
		fRecord.setId(recordId);
		
		FormRecord formRecord = (FormRecord)getSqlMapClientTemplate().queryForObject("Form_findFormRecord", fRecord);
		
		return formRecord;
	}

	@Override
	public List<FormRecord> listFormRecord(FormRecord formRecord)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("listFormRecord(formRecord:"+formRecord+")");
    	}
		
		List<FormRecord> formRecords = (List<FormRecord>)getSqlMapClientTemplate().queryForList("Form_listFormRecord", formRecord,formRecord.getStart(), formRecord.getLimit());
		
		return formRecords;
	}

	@Override
	public List<FormProp> listFormProp(FormProp formProp)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("listFormProp(formProp:"+formProp+")");
    	}
		
		List<FormProp> formProps = (List<FormProp>)getSqlMapClientTemplate().queryForList("Form_listFormProp", formProp);
		
		return formProps;
	}
	
	@Override
	public Integer listFormRecordCount(FormRecord formRecord) throws DataAccessException{
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listFormRecordCount(formRecord), 输入参数[" + formRecord + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Form_listFormRecordCount", formRecord);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listFormRecordCount(formRecord), 返回[" + count + "]");
		}
        return count;
	}
	
	public Integer countByParam(String sql,Map<String,Object> map) throws DataAccessException{
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listFormRecordCountByParam(map), 输入参数[sql:" + sql + ",map:"+map+"]");
		}
        //Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Form_listFormRecordCountByDocNoRule", map);
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject(sql, map);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listFormRecordCountByParam(map), 返回[" + count + "]");
		}
        return count;
	}

	@Override
	public List<FormRecord> listProjects() throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listProjects");
    	}
		
		List<FormRecord> formRecords = (List<FormRecord>)getSqlMapClientTemplate().queryForList("Form_listProjects");
		
		return formRecords;
	}
}
