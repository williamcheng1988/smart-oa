/**
 * 表单/模板 状态
 * @author william
 * 20:46 2014/10/6
 */
package com.chz.smartoa.form.constants;


public interface FormConstants {
	/**表单记录条数**/
	public final static String FORMRECORD_COUNT="xf_f_formRecord_count";
	
	/**返回给流程记录===>表单记录ID前缀**/
	public final static String PREFIX_FORMRECORD_ID="RID:";
	/**返回给流程记录===>表单记录UUID前缀**/
	public final static String PREFIX_FORMRECORD_UUID="RUUID:";
	public final static String SUFFIX_FORMPROP_NAME_CLONE="dy_clone";
	
	
	/**流程实例名**/
	public final static String PROCESS_NAME="processName";
	/**模板对象**/
	public final static String FORMTEMPLATE_OBJ="formTemplate";
	/**模板名称**/
	public final static String FORMTEMPLATE_NAME="formTemplateName";
	/**模板类型名称**/
	public final static String FORMTEMPLATE_TYPE_NAME="formTemplateTypeName";
	/**模板类型描述**/
	public final static String FORMTEMPLATE_TYPE_DESCRIPTION="formTemplateTypeDesc";
	/**模板对象ID**/
	public final static String FORMTEMPLATE_ID="formTemplateId";
	/**表单对象ID**/
	public final static String FORMRECORD_ID="xf_f_formRecordId";
	/**登陆用户名**/
	public final static String LOGIN_NAME="loginName";
	/**保存状态**/
	public final static String FORM_STATUS="formStatus";
	/** Request 页面参数 Map**/
	public final static String FLOW_REQUEST_MAP="flowRequestMap";
	/** 项目 ID**/
	public final static String PROJECT_ID="projectId";
	/** 表单数据JSON**/
	public final static String FORMRECORD_JSON="json";
	
	
	/**视图路径**/
	public final static String VIEW_URL="viewUrl";
	/**页面传入控件参数(key-value)**/
	public final static String VIEW_PARAM_MAP="viewParamMap";
	
	
	/**客户代码**/
	public final static String CUSTOMER_CODE="customerCode";
	
}
