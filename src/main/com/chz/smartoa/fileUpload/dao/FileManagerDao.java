package com.chz.smartoa.fileUpload.dao;

import java.util.List;
import java.util.Map;

import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.util.FileQuery;

public interface FileManagerDao {
	
	public String insertFileManager(FileManager fm);
	
	public void deleteFileManager(String id);
	
	public void updateFileStatusByIDs(String[] arrayId,Integer isValid);
	
	public void updateFileStatusByGroupId(Long groupId,Integer isValid);
	
	//public void filePublic(FileManager fm);
	
	public FileManager findFileManagerById(String id);
	
	public FileManager findFileManagerByFileNumber(String fileNumber);
	
	public List<FileManager> findFileManager(FileQuery fm,int start,int limit);
	
	public int getFileManagerCount(FileQuery fm);
	
	public List<FileManager> findByFileUpdateId(String updateId);
	
	public void updateFileByObject(FileManager fm);
	
	public List<FileManager> getFileListByGroupId(String groupId,String loginName,List<String> fileIds);
	
	public List<String> getPerssionFileList(FileQuery fq);
	
	public List<FileManager> getLastFiles(FileQuery fq);
	
	public int getFileNumberByName(Map map);
	
	public List<FileManager> findFileListByRole(FileQuery fq, int start,int limit);
	
	public int getFileCountByRole(FileQuery fq);
	 
}
