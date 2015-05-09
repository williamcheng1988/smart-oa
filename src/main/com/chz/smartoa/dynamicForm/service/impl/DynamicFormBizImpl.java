package com.chz.smartoa.dynamicForm.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.dao.DynamicFormDao;
import com.chz.smartoa.dynamicForm.pojo.FormProp;
import com.chz.smartoa.dynamicForm.pojo.FormRecord;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;
import com.chz.smartoa.dynamicForm.pojo.FormTemplateType;
import com.chz.smartoa.dynamicForm.service.DynamicFormBiz;
import com.chz.smartoa.dynamicForm.util.StringUtils;
import com.chz.smartoa.form.constants.FormStatus;
import com.chz.smartoa.global.ResourceMgr;
import com.chz.smartoa.system.dao.ResourceDao;
import com.chz.smartoa.system.pojo.Resource;

/**
 * DynamicFormBizImpl
 * @author William Cheng
 * @version 1.0
 * @time 15:29 2014/8/16
 */
public class DynamicFormBizImpl implements DynamicFormBiz {
	/**
	 * logger.
	 */
	private static final Logger logger = Logger.getLogger(DynamicFormBizImpl.class);
	
	private DynamicFormDao dynamicFormDao;
	
	private ResourceDao resourceDao;
	
	public DynamicFormDao getDynamicFormDao() {
		return dynamicFormDao;
	}
	public void setDynamicFormDao(DynamicFormDao dynamicFormDao) {
		this.dynamicFormDao = dynamicFormDao;
	}
	
	public ResourceDao getResourceDao() {
		return resourceDao;
	}
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	public void insertFormTemplateType(FormTemplateType formTemplateType) throws DataAccessException {
		
		dynamicFormDao.insertFormTemplateType(formTemplateType);
		
		FormTemplateType ftt = new FormTemplateType();
		ftt.setType(formTemplateType.getType());
		//获取插入的模板类型
		FormTemplateType formTT = dynamicFormDao.findFormTemplateType(ftt);
		/**
		 * 同时创建"模板类型"资源，显示到菜单
		 */
		String resourceId = "formTmpType-"+formTT.getId();
		int sequence = formTemplateType.getSequence();
		int sortNum = 200+sequence;
		Resource resource = new Resource(resourceId, "root",Resource.AUTH_TYPE_AUTH,
						formTT.getType(), "1", null, sortNum,"flowInput.png");
		resourceDao.insertResource(resource);
		//更新缓存,刷新资源
		ResourceMgr.getInstance().refresh();
	}
	
	public void deleteFormTemplateType(List<String> ids) throws DataAccessException {
		for (String id : ids) {
			//Long id = new Long(String.valueOf(o));
			if(StringUtils.isEmpty(id)) continue;
			
			FormTemplateType tmp = findFormTemplateType(new FormTemplateType (id));
			if(tmp==null){
				continue;
			}
			String uuid = tmp.getId();
			dynamicFormDao.deleteFormTemplateType(id);
			/**
			 * 删除资源
			 */
			resourceDao.deleteResource("formTmpType-"+uuid);
			//更新缓存,刷新资源
			ResourceMgr.getInstance().refresh();
		}
	}
    
    public void updateFormTemplateType(FormTemplateType formTemplateType) 
    		throws DataAccessException {
    	
    	dynamicFormDao.updateFormTemplateType(formTemplateType);
    	
    	Integer status = formTemplateType.getStatus();
    	if(status!=null && status.intValue()==0){
    		//定义资源对象
    		String resourceId = "formTmpType-"+formTemplateType.getId();
    		int sequence = formTemplateType.getSequence();
    		int sortNum = 200+sequence;
    		Resource resource = new Resource(resourceId, "root",Resource.AUTH_TYPE_AUTH,
    				formTemplateType.getType(), "1", null, sortNum,"flowInput.png");
    		
    		//获取数据库该资源对象
    		Resource rs = resourceDao.findResource(resourceId);
    		if(rs!=null && !StringUtils.isEmpty(rs.getResourceId())){
    			//如果存在该资源，更新
        		resourceDao.updateResource(resource);
    		}else{
    			//如果该资源不存在，新增
    			resourceDao.insertResource(resource);
    		}
    	}else{
    		/**
    		 * 删除资源
    		 */
    		resourceDao.deleteResource("formTmpType-"+formTemplateType.getId());
    	}
		
		//更新缓存,刷新资源
		ResourceMgr.getInstance().refresh();
	}
    
    public FormTemplateType findFormTemplateType(FormTemplateType ft) 
    		throws DataAccessException {
    	return dynamicFormDao.findFormTemplateType(ft);
	}
    
    public List<FormTemplateType> listFormTemplateType(FormTemplateType formTemplateType) 
    		throws DataAccessException {
		return dynamicFormDao.listFormTemplateType(formTemplateType);
	}
	
    public Integer listFormTemplateTypeCount(FormTemplateType formTemplateType) throws DataAccessException{
    	
    	return dynamicFormDao.listFormTemplateTypeCount(formTemplateType);
    }
    
	@Override
	public void insertFormTemplate(FormTemplate formTemplate)
			throws DataAccessException {
		dynamicFormDao.insertFormTemplate(formTemplate);
		
		/**
		 * 同时创建"模板类型"资源，显示到菜单
		 */
		String id = "formTmp-"+formTemplate.getId();
		String parentId = "formTmpType-"+formTemplate.getTypeId();
		String addressUrl ="flow!launch.do?formTemplateId="+formTemplate.getId();
		int sortNum = formTemplate.getSequence();
		String name = formTemplate.getName();
		Resource resource = new Resource(id, parentId, name, "1", addressUrl, sortNum);
		resourceDao.insertResource(resource);
		//更新缓存,刷新资源
		ResourceMgr.getInstance().refresh();
	}
	@Override
	public void deleteFormTemplate(String id,boolean delFlag)
			throws DataAccessException {
		if(delFlag){
			dynamicFormDao.deleteFormTemplate(id);
		}else{
			FormTemplate formTemplate = new FormTemplate();
			formTemplate.setId(id);
			formTemplate.setStatus(FormStatus.SUSPEND.value);		//设置数据为 “禁用” 状态
			dynamicFormDao.updateFormTemplate(formTemplate);
		}
	}
	@Override
	public void deleteFormTemplates(List<String> ids,boolean delFlag) 
			throws DataAccessException {
		for (String id : ids) {
			if(StringUtils.isEmpty(id)) return;
			
			this.deleteFormTemplate(id, delFlag);
			
			//删除资源
			resourceDao.deleteResource("formTmp-"+id);
		}
		//更新缓存,刷新资源
		ResourceMgr.getInstance().refresh();
	}
	@Override
	public void updateFormTemplate(FormTemplate formTemplate)
			throws DataAccessException {
		dynamicFormDao.updateFormTemplate(formTemplate);
		
		/**
		 * 同时更新"模板类型"资源，同步到菜单
		 */
		Integer status = formTemplate.getStatus();
    	if(status!=null && status.intValue()==0){
    		//定义资源对象
    		FormTemplateType tmp = findFormTemplateType(new FormTemplateType (formTemplate.getTypeId()));
    		if(tmp==null){return;}
    		
    		String resourceId = "formTmp-"+formTemplate.getId();
    		String parentId = "formTmpType-"+tmp.getId();
    		int sortNum = formTemplate.getSequence();
    		Resource resource = new Resource(resourceId, parentId, formTemplate.getName(), null, null, sortNum);
    		//resourceDao.updateResource(resource);
    		
    		//获取数据库该资源对象
    		Resource rs = resourceDao.findResource(resourceId);
    		if(rs!=null && !StringUtils.isEmpty(rs.getResourceId())){
    			//如果存在该资源，更新
        		resourceDao.updateResource(resource);
    		}else{
    			//如果该资源不存在，新增
    			resourceDao.insertResource(resource);
    		}
    	}else{
    		/**
    		 * 删除资源
    		 */
    		resourceDao.deleteResource("formTmp-"+formTemplate.getId());
    	}
		//更新缓存,刷新资源
		ResourceMgr.getInstance().refresh();
	}
	@Override
	public FormTemplate findFormTemplate(String id) throws DataAccessException {
		FormTemplate ft = new FormTemplate();
		ft.setId(id);
		return dynamicFormDao.findFormTemplate(ft);
	}
	@Override
	public List<FormTemplate> listFormTemplate(FormTemplate formTemplate) throws DataAccessException {
		
		return dynamicFormDao.listFormTemplate(formTemplate);
	}
	@Override
	public Integer listFormTemplateCount(FormTemplate formTemplate)
			throws DataAccessException {
		
		return dynamicFormDao.listFormTemplateCount(formTemplate);
	}
	@Override
	public Map<FormTemplateType,List<FormTemplate>> listFormTemplateByTypeSeq(FormTemplate formTemplate) throws DataAccessException{
		//使用 LinkedHashMap ，方可在页面按照添加时的顺序遍历
		Map<FormTemplateType,List<FormTemplate>> map = new LinkedHashMap<FormTemplateType,List<FormTemplate>>();
		
		List<FormTemplate> formTemplates = dynamicFormDao.listFormTemplateByTypeSeq(formTemplate);
		
		if(formTemplates!=null && formTemplates.size()>0){
			FormTemplateType ftt = null;
			
			for(FormTemplate temp : formTemplates){
				//获取对应模板类型
				ftt = temp.getFtType();
				//模板类型 获取 相应的 结合
				List<FormTemplate> listSequence = map.get(ftt);
				if(listSequence==null || listSequence.size()<=0){
					//如果没有对应的集合，则新建一个集合
					listSequence =  new ArrayList<FormTemplate>();
				}
				/**
				 * 首页显示，流程需要经过encoder之后的流程名字
				 */
				temp.setProcessName(URLEncoder.encode(temp.getProcessName()));
				//添加模板对象
				listSequence.add(temp);
				
				//将每组 序号 sequence 对应的集合 添加到 总的集合  list 中
				map.put(ftt,listSequence);
			}
		}
		
		return map;
	}
	@Override
	public void insertForm(FormRecord formRecord) throws DataAccessException {
		
		//插入记录信息
		dynamicFormDao.insertFormRecord(formRecord);
		
		String uuId = formRecord.getUuId();
		
		//FormProp formProp = null;
		if(formRecord.getProps()!=null && formRecord.getProps().size()>0){
			//循环插入数据
			for (Map.Entry<String, List<FormProp>> entry: formRecord.getProps().entrySet()) {
				List<FormProp> lsprops = entry.getValue();
	            for(FormProp prop:lsprops){
	            	if(StringUtils.isEmpty(prop.getValue())){continue;}
					prop.setFormRecordId(uuId);
					dynamicFormDao.insertFormProp(prop);
	            }
			}
		}
	}
	@Override
	public boolean deleteForm(String formRecordId, boolean delFlag) throws DataAccessException {
		
		FormRecord fr = dynamicFormDao.findFormRecord(formRecordId);
		if(fr==null){
			return false;
		}
		/**
		 * 1. 先删除键值对信息
		 */
		FormProp formProp = new FormProp();
		formProp.setFormRecordId(fr.getUuId());
		
		dynamicFormDao.deleteFormPropByFormRecordId(formProp);
		
		/**
		 * 2. 再删除记录信息
		 */
		dynamicFormDao.deleteFormRecord(fr.getUuId());
		
		return true;
	}
	@Override
	public void deleteForms(List<Long> ids, boolean delFlag)
			throws DataAccessException {
		
		
	}
	@Override
	public void updateForm(FormRecord formRecord) throws DataAccessException {
		/**
		 * 1. 先更新记录信息
		 */
		dynamicFormDao.updateFormRecord(formRecord);
		
		/**
		 * 2. 删除所有prop数据
		 */
		FormProp formProp = new FormProp();
		formProp.setFormRecordId(formRecord.getUuId());
		dynamicFormDao.deleteFormPropByFormRecordId(formProp);
		
		/**
		 * 3. 再新建键值对信息
		 */
		Map<String, List<FormProp>> props = formRecord.getProps();
		if(props!=null && props.size()>0){
			//大量数据时，采用此种方式遍历
			for (Map.Entry<String, List<FormProp>> entry: props.entrySet()) {
	            List<FormProp> lsprops = entry.getValue();
	            for(FormProp prop:lsprops){
					prop.setFormRecordId(formRecord.getUuId());
					dynamicFormDao.insertFormProp(prop);
	            }
			}
		}
	}
	@Override
	public FormRecord findForm(String formRecordId) throws DataAccessException {
		FormRecord formRecord = dynamicFormDao.findFormRecord(formRecordId);
		if(formRecord!=null){
			FormProp formProp = new FormProp();
			formProp.setFormRecordId(formRecord.getUuId());
			List<FormProp> formProps = dynamicFormDao.listFormProp(formProp);
			
			Map<String,List<FormProp>> map = new HashMap<String, List<FormProp>>();
			if(formProps!=null && formProps.size()>0){
				for(FormProp p : formProps){
					List<FormProp> lsprops = null;
					if(map.containsKey(p.getName())){
						lsprops = map.get(p.getName());
					} else {
						lsprops = new ArrayList<FormProp>();
					}
					lsprops.add(p);
					map.put(p.getName(), lsprops);
				}
			}
			
			formRecord.setProps(map);
		}
		
		return formRecord;
	}
	@Override
	public FormRecord findFormRecord(String recordId) throws DataAccessException {
		return dynamicFormDao.findFormRecord(recordId);
	}
	@Override
	public List<FormRecord> listForm(FormRecord formRecord)
			throws DataAccessException {
		List<FormRecord> formRecords = dynamicFormDao.listFormRecord(formRecord);
		
		if(formRecords!=null && formRecords.size()>0){
			for(FormRecord r: formRecords){
				//this.findForm(r.getId());
				FormProp formProp = new FormProp();
				formProp.setFormRecordId(r.getUuId());
				List<String> codes = formRecord.getPropCodes();
				if(codes!=null && codes.size()>0){formProp.setPropCodes(codes);}
				
				List<FormProp> formProps = dynamicFormDao.listFormProp(formProp);
				
				Map<String,List<FormProp>> map = new HashMap<String, List<FormProp>>();
				if(formProps!=null && formProps.size()>0){
					for(FormProp p : formProps){
						List<FormProp> lsprops = null;
						if(map.containsKey(p.getName())){
							lsprops = map.get(p.getName());
						} else {
							lsprops = new ArrayList<FormProp>();
						}
						lsprops.add(p);
						map.put(p.getName(), lsprops);
					}
				}
				r.setProps(map);
			}
		}
		
		return formRecords;
	}
	@Override
	public Integer listFormRecordCount(String templateId) throws DataAccessException{
		
		FormRecord formRecord = new FormRecord();
		formRecord.setFormTemplateId(templateId);
		
		return dynamicFormDao.listFormRecordCount(formRecord);
	}
	public Integer listFormRecordCount(FormRecord formRecord) throws DataAccessException{
		return dynamicFormDao.listFormRecordCount(formRecord);
	}
	@Override
	public Integer countByParam(String sql,@SuppressWarnings("rawtypes") Map map) throws DataAccessException{
		
		return dynamicFormDao.countByParam(sql,map);
	}
	@Override
	public List<FormRecord> listProjects() throws DataAccessException {
		
		return dynamicFormDao.listProjects();
	}
}
