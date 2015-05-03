package com.chz.smartoa.dynamicForm.pojo;

import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;
import com.google.gson.annotations.Expose;

/**
 * 该实体关联FormRecord记录的uuid
 * @author william
 *
 */
public class FormProp extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3823611094958428612L;
	private Long id;
	@Expose
	private String name;
	@Expose
    private String value;
	@Expose
    private String innerName;
    
    private String formRecordId;
    
    private FormRecord formRecord = new FormRecord();
    
    private List<String> propCodes;

    public FormProp(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormRecordId() {
		return formRecordId;
	}

	public void setFormRecordId(String formRecordId) {
		this.formRecordId = formRecordId;
	}

	public FormRecord getFormRecord() {
		return formRecord;
	}

	public void setFormRecord(FormRecord formRecord) {
		this.formRecord = formRecord;
	}

	public List<String> getPropCodes() {
		return propCodes;
	}

	public void setPropCodes(List<String> propCodes) {
		this.propCodes = propCodes;
	}

	public String getInnerName() {
		return innerName;
	}

	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}
}
