package com.chz.smartoa.fileUpload.service;

import java.util.List;
import java.util.Map;

import com.chz.smartoa.fileUpload.pojo.FileGroup;


public interface FileGroupBiz {
	/**
	 * 保存流程附件
	 * @param executionId 流程实例ID
	 * @param attachmentIds 附件ID
	 * @param projectId 项目ID
	 * @return
	 */
	public void insertFileGroup(Map<String, Object> params);
	
	public void addFileGroup(FileGroup fg);
	
	public String getGroupIdByFileId(String fileId);
}
