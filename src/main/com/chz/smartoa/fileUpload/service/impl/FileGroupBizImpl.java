package com.chz.smartoa.fileUpload.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.RuleGenerator;
import com.chz.smartoa.fileUpload.dao.FileGroupDao;
import com.chz.smartoa.fileUpload.dao.FileManagerDao;
import com.chz.smartoa.fileUpload.pojo.FileGroup;
import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.service.FileGroupBiz;
import com.chz.smartoa.form.constants.FormConstants;
import com.chz.smartoa.system.dao.DictionaryConfigDao;
import com.chz.smartoa.system.pojo.DictionaryConfig;

public class FileGroupBizImpl implements FileGroupBiz{
	
	private FileGroupDao fileGroupDao;

	public FileGroupDao getFileGroupDao() {
		return fileGroupDao;
	}
	public void setFileGroupDao(FileGroupDao fileGroupDao) {
		this.fileGroupDao = fileGroupDao;
	}
	
	private FileManagerDao fileManagerDao;
	public FileManagerDao getFileManagerDao() {
		return fileManagerDao;
	}
	public void setFileManagerDao(FileManagerDao fileManagerDao) {
		this.fileManagerDao = fileManagerDao;
	}
	
	private DictionaryConfigDao dictionaryConfigDao;
	public DictionaryConfigDao getDictionaryConfigDao() {
		return dictionaryConfigDao;
	}
	public void setDictionaryConfigDao(DictionaryConfigDao dictionaryConfigDao) {
		this.dictionaryConfigDao = dictionaryConfigDao;
	}
	
	
	
	/**
	 * 新增
	 */
	@Override
	public void insertFileGroup(Map<String, Object> paraMap) {
		
		String[] fileIds = (String[]) paraMap.get("fileIds");   // 获取文件唯一ID列表
		String status = String.valueOf(paraMap.get(FormConstants.FORM_STATUS));  // 暂存 还是 提交 操作
		String executionId = (String) paraMap.get("executionId");  // 获取文件列表所属的流程Id
		if(StringUtils.isNotEmpty(status)){
			if("0".equals(status)){
				// 暂存
				if(fileIds == null || fileIds.length < 1){
					return;
				}else{
					for(int i=0;i<fileIds.length;i++){
						FileGroup fg = new FileGroup();
						fg.setGroupId(executionId);
						fg.setFileId(fileIds[i].trim());
						fileGroupDao.insertFileGroup(fg);  // 保存文件流程组
					}
				}
				
			}else if("1".equals(status)){
				
				// 获取历史暂存的记录
				List<String> historyList = fileGroupDao.getFileIdsByGroupId(executionId);
				// 更新历史记录文件编号
				if(historyList != null && historyList.size() >= 1){
					for(int j=0;j<historyList.size();j++){
						FileManager fm = fileManagerDao.findFileManagerById(historyList.get(j));
						if(fm != null){
							String fileNumber = this.genNewFileNumber(paraMap,fm);
							fm.setIsValid(2);   // 生效
							// 验证该文件序号是否存在
							FileManager fmager = fileManagerDao.findFileManagerByFileNumber(fileNumber);
							if(fmager == null){
								fm.setFileNumber(fileNumber);
								fileManagerDao.updateFileByObject(fm);  // 更新文件名称
							}else{
								fileNumber = this.genNewFileNumber(paraMap,fm);
								fileManagerDao.updateFileByObject(fm);  // 重新更新文件名称
							}
						}
					}
				}
				
				// 提交
				if(fileIds != null && fileIds.length >= 1){
					for(int i=0;i<fileIds.length;i++){
						FileGroup fg = new FileGroup();
						fg.setGroupId(executionId);
						fg.setFileId(fileIds[i].trim());
						fileGroupDao.insertFileGroup(fg);  // 保存文件流程组
						
						FileManager fm = fileManagerDao.findFileManagerById(fileIds[i].trim());
						String fileNumber = this.genNewFileNumber(paraMap,fm);
						fm.setIsValid(2);   // 生效
						// 验证该文件序号是否存在
						FileManager fmager = fileManagerDao.findFileManagerByFileNumber(fileNumber);
						if(fmager == null){
							fm.setFileNumber(fileNumber);
							fileManagerDao.updateFileByObject(fm);  // 更新文件名称
						}else{
							fileNumber = this.genNewFileNumber(paraMap,fm);
							fileManagerDao.updateFileByObject(fm);  // 重新更新文件名称
						}
					}
				}
			}
		}
		
	}
	
	public String[] getFileIds(String fileIds){
		int index = fileIds.indexOf(']');
		if(index > 0){
			fileIds = fileIds.replace("[", "");
			fileIds = fileIds.replace("]", "");
			String [] fileArr = fileIds.split(","); 
			return fileArr;
		}else{
			String [] fileArr = new String [1];
			fileArr[0] = fileIds;
			return fileArr;
		}
		
	}
	
	
	/**
	 * 更新上传的文件名称
	 */
	public String genNewFileNumber(Map<String, Object> pramMap,FileManager fm){
		
		DictionaryConfig cg = dictionaryConfigDao.findDictionarytCfgById(fm.getFileTypeSubId());
		String fileTypeNo = cg.getFileTypeNo();  // 获取字典配置对应的文件类别序号
		
		/**
		 *  获取客户代码（截取第一段字母）
		 */
		String recordId =  String.valueOf(pramMap.get(FormConstants.FORMRECORD_ID));
		Pattern patternTmp = Pattern.compile("[a-zA-Z]+");
		Matcher matcherTmp = patternTmp.matcher(recordId);   // 客户
		String matchStrTmp = "";
		int index = 0;
		while (matcherTmp.find()) {
			if(index==0){
				matchStrTmp = matcherTmp.group();
				break;
			}
		}

		// 需要自增长序号
		pramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);
		//源规则字符串
		pramMap.put(IRuleGenerator.RULE_SRC_RULE, matchStrTmp + fileTypeNo);
		//执行sql脚本，获取最新记录条数
		pramMap.put(IRuleGenerator.RULE_EXEC_SQL, "fmger_getFileNumberByName");
		// 根据源规则生成规则代码
		String idByRule = RuleGenerator.generateStrByRule(pramMap);
		// 返回最终文件名称：文件规则名称定义：  <'公司代码' + '文件序号和配置年月日类型  + 自增长序号'  + '上传文件名称(版本号  + 文件后缀)'>
		return idByRule;  
		
	}
	
	
	@Override
	public void addFileGroup(FileGroup fg) {
		fileGroupDao.insertFileGroup(fg);  
	}
	
	
	/**
	 * 根据fileId获取所属流程ID
	 */
	@Override
	public String getGroupIdByFileId(String fileId) {
		return fileGroupDao.getGroupIdByFileId(fileId);
	}
	
}
