package com.chz.smartoa.dynamicForm.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 该实体真正主键为uuid，而id本身只是给用户作为记录查看，以及关联流程等用
 * @author william
 * 
 */
public class FormRecord extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2327719657605296482L;
	private String id;
	private String uuId;
    private String title;
    private String alias;
    private Integer status;
    private int count;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    
    private Map<String, String> formProps = new LinkedHashMap<String, String>();
    //private List<FormProp> props = new ArrayList<FormProp>();
    private Map<String, List<FormProp>> props = new LinkedHashMap<String, List<FormProp>>();
    
    private List<String> propCodes = new ArrayList<String>();
    
    private String formTemplateId;
    private String createDateFrom;
    private String createDateTo;
    
    public FormRecord(){}
    public FormRecord(String id){
    	this.id = id;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Map<String, List<FormProp>> getProps() {
		return props;
	}

	public void setProps(Map<String, List<FormProp>> props) {
		this.props = props;
	}

	public String getFormTemplateId() {
		return formTemplateId;
	}

	public void setFormTemplateId(String formTemplateId) {
		this.formTemplateId = formTemplateId;
	}

	public List<String> getPropCodes() {
		return propCodes;
	}

	public void setPropCodes(List<String> propCodes) {
		this.propCodes = propCodes;
	}

	public Map<String, String> getFormProps() {
		return formProps;
	}

	public void setFormProps(Map<String, String> formProps) {
		this.formProps = formProps;
	}

	public String getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(String createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public String getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(String createDateTo) {
		this.createDateTo = createDateTo;
	}
}
