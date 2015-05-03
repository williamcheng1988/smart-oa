package com.chz.smartoa.fileUpload.dao;



import java.util.List;

import com.chz.smartoa.fileUpload.pojo.FileGroup;

public interface FileGroupDao {
	
	public void insertFileGroup(FileGroup fg);
	
	public String getGroupIdByFileId(String fileId);
	
	public List<String> getFileIdsByGroupId(String groupId);
}
