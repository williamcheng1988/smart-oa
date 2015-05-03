package com.chz.smartoa.fileUpload.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.fileUpload.dao.FileGroupDao;
import com.chz.smartoa.fileUpload.pojo.FileGroup;


public class FileGroupDaoImpl extends SqlMapClientDaoSupport implements FileGroupDao{

	/**
	 * 新增
	 */
	@Override
	public void insertFileGroup(FileGroup fg) {
		getSqlMapClientTemplate().insert("fg_insertFg", fg); 
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String getGroupIdByFileId(String fileId) {
		List<String> groupIds = (List<String>)getSqlMapClientTemplate().queryForList("fg_getGroupIdByFileId", fileId);
		if(groupIds != null && groupIds.size() == 1){
			return groupIds.get(0);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFileIdsByGroupId(String groupId) {
		List<String> fileIds = (List<String>)getSqlMapClientTemplate().queryForList("fg_fileIdsByGroupId", groupId);
		return fileIds;
	}

}
