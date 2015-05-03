package com.chz.smartoa.fileUpload.service;

import java.util.List;

import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.util.FileQuery;


/**
 * 
 * @author Zhao
 *
 */
public interface FileManagerBiz {
	
	public String insertFileManager(FileManager fm);
	
	public void deleteFileManager(String[]arrMainId,Integer isValid);
	
	public void deleteFileManager(String id);
	
	public void updateFileStatusByIDs(String[] arrayId,Integer isValid);
	
	public void updateFileStatusByGroupId(Long groupId, Integer isValid);
	
	public void toUnorPublic(String[]arrMainId,Integer ispublic);
	
	public FileManager findFileManagerById(String id);
	
	public FileManager findFileManagerByFileNumber(String fileNumber);
	
	public List<FileManager> findFileManager(FileQuery fm,int start,int limit);
	
	public int getFileManagerCount(FileQuery fm);
	
	public List<FileManager> findByFileUpdateId(String updateId);
	
	public void updateFileByObject(FileManager fm);
	
	public List<FileManager> getFileListByGroupId(String groupId,String loginName);
	
	public List<String> getPerssionFileList(FileQuery fq);
	
	public List<FileManager> disPlayLastFilesForPublic();
	
	public List<FileManager> disPlayLastFilesFortechnology();
	
	public List<FileManager> findFileListByRole(FileQuery fq, int start,int limit);
	
	public int getFileCountByRole(FileQuery fq);
}
