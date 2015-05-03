package com.chz.smartoa.form.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.dynamicForm.pojo.FormTemplate;


/**
 * FormBiz 流程调用接口
 * @author william
 * @time 11:15 2014/11/2
 */
public interface FormBiz {
	/**
	 * 流程请求(新增):根据 "模板ID"获取相应参数
	 * @param templateId
	 * @return Map:
	 * 		processName(String) : 流程名称
	 * 		formTemplate(Entity): 模板对象信息
	 * 		pageUrl(String) : 模板信息加载页面
	 */
	public Map reachFormTemplate(String templateId) throws Exception;
	
	/**
	 * 流程请求(修改):根据 "模板ID/表单记录ID" 获取相应参数
	 * @param templateId
	 * @return Map:
	 * 		processName(String) : 流程名称
	 * 		formTemplate(Entity): 模板对象信息
	 * 		pageUrl(String) : 模板信息加载页面
	 */
	public Map reachFormTemplate(String templateId,String formRecordId) throws Exception;
	
	/**
	 * 流程请求：保存表单数据
	 * @param paramMap
	 * @return Map
	 * @throws Exception
	 */
	public Map saveForm(Map paramMap) throws Exception;
	
	/**
     * find.
     * @param id id
     * @return FormTemplate
     * @throws DataAccessException DataAccessException
     */
    String getProcessNamebyFtId(String id) throws DataAccessException ;
}
