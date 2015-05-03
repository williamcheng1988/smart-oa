package com.chz.smartoa.fileUpload.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.fileUpload.dao.FileManagerDao;
import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.util.FileQuery;


/**
 * 
 * @author Zhao
 *
 */
public class FileManagerDaoImpl extends SqlMapClientDaoSupport implements FileManagerDao{

	/**
	 * 保存附件上传记录
	 */
	@Override
	public String insertFileManager(FileManager fm) {
		getSqlMapClientTemplate().insert("fmger_insertFmger", fm); 
//		FileQuery fq = new FileQuery();
//		fq.setIsValid(Integer.valueOf(1));
//		String id = this.findFileManager(fq, 0, 1).get(0).getId();
//		return id;
		return null;
	}


	
	/**
	 * 根据主键ID批量更新附件上传记录为有效
	 */
	@Override
	public void updateFileStatusByIDs(String[] arrayId,Integer isValid) {
		if(arrayId.length >= 1){
			String id = null;
			FileManager fm = null;
			for(int i=0;i<arrayId.length;i++){
				id = arrayId[i];
				fm = this.findFileManagerById(id);
				fm.setIsValid(isValid);
				this.updateFileByObject(fm);
			}
		}
	}
	
	
	/**
	 * 根据分组ID批量更新附件对应状态
	 */
	@Override
	public void updateFileStatusByGroupId(Long groupId, Integer isValid) {
		FileManager fm = new FileManager();
		fm.setGroupId(groupId);
		fm.setIsValid(isValid);
		getSqlMapClientTemplate().update("fmger_updateStatusByGroupId", fm);
	}


	/**
	 * 根据主键ID查找附件上传记录
	 */
	@Override
	public FileManager findFileManagerById(String id) {
		FileManager fm = (FileManager) getSqlMapClientTemplate().queryForObject("fmger_findFmgerById", id);
		return fm;
	}

	/**
	 * 根据文件编号查找附件上传记录
	 */
	@Override
	public FileManager findFileManagerByFileNumber(String fileNumber) {
		FileManager fm = (FileManager) getSqlMapClientTemplate().queryForObject("fmger_findFmgerByFileNumber", fileNumber);
		return fm;
	}

	
	/**
	 * 附件下载列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> findFileManager(FileQuery fm, int start,int limit) {
		List<FileManager> fmList = (List<FileManager>)getSqlMapClientTemplate().queryForList("fmger_findFmgerByQueryCondition", fm,start, limit);
		return fmList;
	}

	/**
	 * 附件下载列表对应总数
	 */
	@Override
	public int getFileManagerCount(FileQuery fm) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("fmger_listfmgerCount", fm);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> findByFileUpdateId(String updateId) {
		List<FileManager> fmList = (List<FileManager>)getSqlMapClientTemplate().queryForList("fmger_findFmgerByFileUpdateId", updateId);
		return fmList;
	}

	@Override
	public void updateFileByObject(FileManager fm) {
		getSqlMapClientTemplate().update("fmger_updateFileByObject", fm);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> getFileListByGroupId(String groupId,String loginName,List<String> fileIds) {
		FileQuery fq = new FileQuery();
		fq.setGroupId(groupId);
		fq.setUnAllowFileTypes(fileIds);
		fq.setLoginName(loginName);
		List<FileManager> fmList = (List<FileManager>)getSqlMapClientTemplate().queryForList("fmger_getFileListByGroupId", fq);
		return fmList;
	}



	@Override
	public void deleteFileManager(String id) {
		getSqlMapClientTemplate().delete("fmger_deleteForPage",id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPerssionFileList(FileQuery fq) {
		List<String> fileList = (List<String>)getSqlMapClientTemplate().queryForList("fmger_getFileListByPerssion", fq);
		return fileList;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> getLastFiles(FileQuery fq) {
		List<FileManager> fmList = (List<FileManager>)getSqlMapClientTemplate().queryForList("fmger_getLastFiles", fq);
		return fmList;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public int getFileNumberByName(Map map) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("fmger_getFileNumberByName", map);
		return count;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> findFileListByRole(FileQuery fq, int start,int limit) {
		List<FileManager> fmList = (List<FileManager>)getSqlMapClientTemplate().queryForList("fmger_findFileListByRole", fq,start, limit);
		return fmList;
	}



	@Override
	public int getFileCountByRole(FileQuery fq) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("fmger_getFileCountByRole", fq);
		return count;
	}

}
