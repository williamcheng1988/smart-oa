package com.chz.smartoa.dynamicForm.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * FormTemplate .
 * 
 * @author William Cheng
 */
public class FormTemplate extends BaseDomain {
    private static final long serialVersionUID = 0L;
    
    private String id;
	private int sequence;
    private String name;
    private String description;
    private String content;
    private Integer status;
    private String docNoRule;
    private String repNoRule;
    private String proNameRule;
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;
    private String viewUrl;
    private String attachment;
    private String handleClass;
    private String dynamicForm;
    private String projectFlag;
    private String exportFlag;
    
    private String processName;

    public FormTemplate() {}
    
    /**
     * 显示首页模块
     */
    FormTemplateType ftType = new FormTemplateType();
    private String typeId;
    private String type;
    private Integer typeStatus;
    private String typeDescription;
    
    /**
     * 模板统计
     */
    FormRecord record = new FormRecord();
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDocNoRule() {
		return docNoRule;
	}

	public void setDocNoRule(String docNoRule) {
		this.docNoRule = docNoRule;
	}

	public String getRepNoRule() {
		return repNoRule;
	}

	public void setRepNoRule(String repNoRule) {
		this.repNoRule = repNoRule;
	}

	public String getProNameRule() {
		return proNameRule;
	}

	public void setProNameRule(String proNameRule) {
		this.proNameRule = proNameRule;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Integer getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public FormTemplateType getFtType() {
		return ftType;
	}

	public void setFtType(FormTemplateType ftType) {
		this.ftType = ftType;
	}

	public FormRecord getRecord() {
		return record;
	}

	public void setRecord(FormRecord record) {
		this.record = record;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getHandleClass() {
		return handleClass;
	}

	public void setHandleClass(String handleClass) {
		this.handleClass = handleClass;
	}

	public String getDynamicForm() {
		return dynamicForm;
	}

	public void setDynamicForm(String dynamicForm) {
		this.dynamicForm = dynamicForm;
	}
	
	public String getProjectFlag() {
		return projectFlag;
	}

	public void setProjectFlag(String projectFlag) {
		this.projectFlag = projectFlag;
	}

	public boolean isDynamic(){
		return "T".equals(this.dynamicForm) ? true: false;
	}
	
	public boolean isProject(){
		return "T".equals(this.projectFlag) ? true: false;
	}

	public String getExportFlag() {
		return exportFlag;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString();
    }
}
