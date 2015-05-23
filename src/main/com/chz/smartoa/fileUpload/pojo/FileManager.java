package com.chz.smartoa.fileUpload.pojo;


import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;


/**
 * 对应 附件管理表(T_FILE_MANAGER)
 * @author Zhao
 *
 */
public class FileManager extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private String id;              // 唯一编号
	private String fileNumber;    // 文件编号
	private String fileName;      // 文件显示名称（指向文件存储的文件名称）
	private String fileDisplayname;  // 页面文件显示名称
	private Integer fileTypeId;     // 文件对应字典主类型
	private Integer fileTypeSubId;  // 文件对已字典级联子类型
	private String fileAddress;   // 文件保存路径
	private Integer isPublic;     // 是否公开（1：公开   2：不公开）
	private Integer isValid;      // 是否有效（1： 初始化   2：有效 3：无效）
	private Integer version;       // 版本号
	private String fileFix;       // 文件后缀
	private String createUser;    // 创建用户
	private Date createDt;        // 创建时间
	private String updateUser;    // 更新用户
	private Date updateDt;        // 更新时间
	private String updateId;        // 修改的数据的唯一ID
	
	
	// 帮助字段
	private Long groupId;
	private String createDtStr;
	private String fileStatus;
	private String realName;
	
	private String msg;
	
	
	
	public FileManager() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileDisplayname() {
		return fileDisplayname;
	}

	public void setFileDisplayname(String fileDisplayname) {
		this.fileDisplayname = fileDisplayname;
	}

	public Integer getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public Integer getFileTypeSubId() {
		return fileTypeSubId;
	}
	public void setFileTypeSubId(Integer fileTypeSubId) {
		this.fileTypeSubId = fileTypeSubId;
	}
	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getFileFix() {
		return fileFix;
	}
	public void setFileFix(String fileFix) {
		this.fileFix = fileFix;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getCreateDtStr() {
		return createDtStr;
	}
	
	public void setCreateDtStr(String createDtStr) {
		this.createDtStr = createDtStr;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
