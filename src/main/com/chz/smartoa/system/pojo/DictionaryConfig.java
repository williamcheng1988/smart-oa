package com.chz.smartoa.system.pojo;

import java.util.List;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * 
 * 对应  字典配置表(T_DICTIONARY_CONFIG)
 * @author Zhao
 *
 */
public class DictionaryConfig extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;            // 唯一编号
	private String dictionaryName; // 字典名称
	private String dictionaryKey;  // 字典key
	private Integer orderNo;       // 显示位置排序编号
	private Integer parentId;      // 父节点ID
	private Integer dicType;       // 显示信息类型 (1:公共信息  2:技术支持  3:普通类型)
	private Integer isValid;       // 1:有效  2:无效
	private String companyName;    // 公司简称
	private String fileTypeNo;     // 文件类别序号
	private Integer configType;    // 配置类型(1:手动配置  2：自定义sql)
	private String sqlValue;       // 自定义配置的sql
	private Integer level;         // 节点等级
	private Integer isPublic;      // 是否公开（1：公开   2：不公开）
	
	
	private List<DictionaryConfig> dictionaryList;
	private boolean checked;  // 是否选中
	
	
	public DictionaryConfig() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getDictionaryKey() {
		return dictionaryKey;
	}

	public void setDictionaryKey(String dictionaryKey) {
		this.dictionaryKey = dictionaryKey;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDicType() {
		return dicType;
	}

	public void setDicType(Integer dicType) {
		this.dicType = dicType;
	}
	
	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFileTypeNo() {
		return fileTypeNo;
	}

	public void setFileTypeNo(String fileTypeNo) {
		this.fileTypeNo = fileTypeNo;
	}

	public Integer getConfigType() {
		return configType;
	}

	public void setConfigType(Integer configType) {
		this.configType = configType;
	}
	
	public String getSqlValue() {
		return sqlValue;
	}

	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}
	
	public List<DictionaryConfig> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<DictionaryConfig> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}

