package com.chz.smartoa.fileUpload.pojo;


import com.chz.smartoa.common.base.BaseDomain;


/**
 * 对应 附件分组表(T_FILE_GROUP)
 * @author Zhao
 *
 */
public class FileGroup extends BaseDomain{

	private static final long serialVersionUID = 1L;
	
	private String groupId;
	
	private String fileId;
	
	
	public FileGroup() {
		
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
