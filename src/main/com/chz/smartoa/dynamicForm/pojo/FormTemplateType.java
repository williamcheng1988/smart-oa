package com.chz.smartoa.dynamicForm.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * FormTemplate .
 * 
 * @author William Cheng
 */
public class FormTemplateType extends BaseDomain {
    
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private int sequence;
    private String type;
    private Integer status;
    private String description;
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;

    public FormTemplateType(String id) {
    	this.id = id;
    }
    
    public FormTemplateType() {}
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString();
    }
	
	/**
	 * 重写  hashCode 方法 ，对模板按照类型分组起作用
	 */
	@Override
	public int hashCode() {
		if(this.id==null &&
				(this.type==null || "".equals(this.type)) &&
				this.sequence==0){
			return super.hashCode();
		}
		
		int code = (this.id!=null?this.id.hashCode():0)+(this.type!=null?this.type.hashCode():0)+this.sequence;
		
		return code;
	}
	/**
	 * 重写  hashCode 方法 ，对模板按照类型分组起作用
	 */
	@Override
	public boolean equals(Object obj) {
		if(this.id==null &&
				(this.type==null || "".equals(this.type)) &&
				this.sequence==0) {
			return super.equals(obj);
		}
		
		if(obj instanceof FormTemplateType){
			FormTemplateType ftt = (FormTemplateType)obj;
			if(this.id.equals(ftt.id) &&
					this.type.equals(ftt.type) &&
					this.sequence==ftt.sequence){
				return true;
			}
		}
		return false;
	}
}
