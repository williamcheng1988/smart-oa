package com.chz.smartoa.fileUpload.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.IRuleGenerator;
import com.chz.smartoa.dynamicForm.util.ruleGenerator.RuleGenerator;
import com.chz.smartoa.fileUpload.pojo.FileGroup;
import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.service.FileGroupBiz;
import com.chz.smartoa.fileUpload.service.FileManagerBiz;
import com.chz.smartoa.fileUpload.util.PerpertiesTool;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FileLoadAction extends BaseAction{
	
	private static final long serialVersionUID = 853893331682025149L;
	
	private static final Logger logger = Logger.getLogger(FileLoadAction.class);
	
	private FileManagerBiz fileManagerBiz;
	
	private DictionaryConfigBiz dictionaryConfigBiz;
	
	private FileGroupBiz fileGroupBiz;
	
	private FileManager fm;
	
	// 文件上传 ---------------------
	private File file;
	private String fileFileName;
    private String fileFileContentType;
    
    private File ufile;
    private String ufileFileName;
	

	// 文件下载 -----------
	private InputStream targetFile;
	
	private String templateFileName;
	
	private String filedownAddress;
	// ----------END-------------
	
	private String targerFileName;   // 保存的目标文件名称
	private String msg;
	
	private String fileId;
	private String fileTypeId;
	private String fileTypeSubId;
	private String jsonStr;
	private String updateId;
	private String custCode;
	private String groupId;
	
	/**
	 * 业务流程嵌套附件上传界面：新增后的保存操作
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveAddFile(){
		InputStream is = null;
		OutputStream os = null;
		Gson gson = new GsonBuilder().create();
		boolean allowUpload = this.validateFileType(fileFileName);
		boolean allowSize = this.validateFileSize(file);
		if(allowUpload && allowSize){
			String fid = String.valueOf(UUID.randomUUID());  // 生成唯一ID
			Integer versonNo = Integer.valueOf(1);   // 初始版本号
			try {
				// 文件上传暂存路径
				String fileSavePath = PerpertiesTool.getInstance().getProMap().get("FILE_SAVE_PATH");
				File f = new File(fileSavePath);
				if (!f.exists()) {            
					f.mkdirs();        
				}
				is = new FileInputStream(file);
				
				// 提交流程前，页面新增是显示的名称
				targerFileName = genFileName(versonNo, fileFileName); 
				
				// 使用uuid作为保存后的唯一附件名称
				String saveFileName = fid + this.getFileFix(fileFileName);  
				
				File toFile = new File(fileSavePath, saveFileName);  
				// 创建一个输出流  
				os = new FileOutputStream(toFile);  
				//设置缓存  
				byte[] buffer = new byte[1024];  
				int length = 0;
				//读取myFile文件输出到toFile文件中  
				while ((length = is.read(buffer)) > 0) {  
				   os.write(buffer, 0, length);  
				}  
				
				// 新增一条记录
				fm = new FileManager();
				fm.setId(fid);
				fm.setFileNumber("");
				fm.setFileTypeId(Integer.valueOf(fileTypeId));
				fm.setFileTypeSubId(Integer.valueOf(fileTypeSubId));
				fm.setFileName(saveFileName);   // 文件服务器端保存的问名称
				fm.setFileDisplayname(targerFileName); // 页面显示名称
				fm.setFileAddress(fileSavePath);  // 存储地址
				DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileTypeSubId));
				fm.setIsPublic(c.getIsPublic());  // 是否公开
				fm.setIsValid(1);                 // 初始化
				fm.setVersion(versonNo);   
				fm.setCreateUser(getLoginStaff().getLoginName());
				fm.setUpdateUser(getLoginStaff().getLoginName());
				fm.setUpdateId(fid);
				fileManagerBiz.insertFileManager(fm); //保存上传文件记录
				jsonStr = gson.toJson(fm);
			} catch (FileNotFoundException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch(Exception e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				try {
					if(is != null){
						is.close();   //关闭输入流
					}
					if(os != null){
						os.close();   //关闭输出流  
					}
				} catch (IOException e) {
					msg = "false";
					logger.error(e.getMessage());
				}  
			}
		}else{
			if(!allowUpload){
				msg = "type";
			}else if(!allowSize){
				msg = "size";
			}
			jsonStr = gson.toJson(msg);
		}
		return "json";
	}
	
	
	/**
	 * 业务流程嵌套附件上传界面：修改操作
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveFileForUpdate(){
		boolean isUpdate = true;
		boolean allowUpload = true;
		boolean allowSize = true;
		if(ufile != null && StringUtils.isNotBlank(ufileFileName)){
			allowUpload = this.validateFileType(ufileFileName);
			allowSize = this.validateFileSize(ufile);
			if(allowUpload && allowSize){
				isUpdate = true;
			}else{
				isUpdate = false;
			}
		}
		
		Gson gson = new GsonBuilder().create();
		if(isUpdate){
			FileManager fmger = fileManagerBiz.findFileManagerById(updateId);    // 获取待更新的记录
			List<FileManager> flist = fileManagerBiz.findByFileUpdateId(fmger.getUpdateId());
			Integer versonNo = flist.get(0).getVersion() + 1;
			String fid = String.valueOf(UUID.randomUUID());
			DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileTypeSubId));
			fm = new FileManager();
			String fileNumber = "";
			if(ufile != null && StringUtils.isNotBlank(ufileFileName)){
				InputStream is = null;
				OutputStream os = null;
				try {
					// 文件上传暂存路径
					String fileSavePath = PerpertiesTool.getInstance().getProMap().get("FILE_SAVE_PATH");
					File f = new File(fileSavePath);
					if (!f.exists()) {            
						f.mkdirs();        
					}
					is = new FileInputStream(ufile);
					
					
					// 使用uuid的方式将新上传的文件名称重新命名，确保唯一
					String saveFileName = fid + this.getFileFix(ufileFileName);  
					
					File toFile = new File(fileSavePath, saveFileName);  
					// 创建一个输出流  
					os = new FileOutputStream(toFile);  
					//设置缓存  
					byte[] buffer = new byte[1024];  
					int length = 0;
					//读取myFile文件输出到toFile文件中  
					while ((length = is.read(buffer)) > 0) {  
					   os.write(buffer, 0, length);  
					} 
					if(is != null){
						is.close();   //关闭输入流
					}
					if(os != null){
						os.close();   //关闭输出流 
					}
					
					// 文件存储名称
					fm.setFileName(saveFileName); 
					
					// 页面显示名称
					targerFileName = genFileName(versonNo, ufileFileName);
					// 根据原有生成的文件名称截取到客户号 + 新选择的文件序号  生成文件编号
					fileNumber = this.getFileNumber(fmger.getFileNumber(), c.getFileTypeNo());
					fm.setFileDisplayname(targerFileName);  
					
				} catch (FileNotFoundException e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IOException e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				}catch(Exception e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}else{
				// 修改操作未上传附件的，附件记录仍然用修改前的附件
				// 文件名称
				fm.setFileName(fmger.getFileName());    
				// 页面显示名称
				fileNumber = this.getFileNumber(fmger.getFileNumber(), c.getFileTypeNo());
				fm.setFileDisplayname(fmger.getFileDisplayname()); 
			}
			 
			fm.setFileTypeId(Integer.valueOf(fileTypeId));
			fm.setFileTypeSubId(Integer.valueOf(fileTypeSubId));
			fm.setFileNumber(fileNumber);             
			fm.setFileAddress(fmger.getFileAddress());  
			fm.setIsPublic(fmger.getIsPublic());       
			fm.setIsValid(fmger.getIsValid());   
			fm.setVersion(versonNo);            
			fm.setCreateUser(getLoginStaff().getLoginName());     
			fm.setUpdateUser(getLoginStaff().getLoginName());
			fm.setUpdateId(fmger.getUpdateId());  // 保留原始的数据ID
			fm.setId(fid);
			fileManagerBiz.insertFileManager(fm);  //保存上传文件记录
			FileManager filemanager = fileManagerBiz.findFileManagerById(fid);
			jsonStr = gson.toJson(filemanager);
		}else{
			if(!allowUpload){
				msg = "type";
			}else if(!allowSize){
				msg = "size";
			}
			jsonStr = gson.toJson(msg);
		}
		return "json";
	}
	
	
	
	/**
	 * 业务流程审批中的 修改操作
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveAfterFileForUpdate(){
		boolean isUpdate = true;
		boolean allowUpload = true;
		boolean allowSize = true;
		if(ufile != null && StringUtils.isNotBlank(ufileFileName)){
			allowUpload = this.validateFileType(ufileFileName);
			allowSize = this.validateFileSize(ufile);
			if(allowUpload && allowSize){
				isUpdate = true;
			}else{
				isUpdate = false;
			}
		}
		
		Gson gson = new GsonBuilder().create();
		if(isUpdate){
			FileManager fmger = fileManagerBiz.findFileManagerById(updateId);    // 获取待更新的记录
			List<FileManager> flist = fileManagerBiz.findByFileUpdateId(fmger.getUpdateId());
			Integer versonNo = flist.get(0).getVersion() + 1;
			String fid = String.valueOf(UUID.randomUUID());
			fm = new FileManager();
			String fileNumber = "";
			// 设置目标文件  
			DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileTypeSubId));
			if(ufile != null && StringUtils.isNotBlank(ufileFileName)){
				InputStream is = null;
				OutputStream os = null;
				try {
					// 文件上传暂存路径
					String fileSavePath = PerpertiesTool.getInstance().getProMap().get("FILE_SAVE_PATH");
					File f = new File(fileSavePath);
					if (!f.exists()) {            
						f.mkdirs();        
					}
					is = new FileInputStream(ufile);
					
					String saveFileName = fid + this.getFileFix(ufileFileName);  // 使用uuid作为保存后的唯一附件名称
					
					File toFile = new File(fileSavePath, saveFileName);  
					
					// 创建一个输出流  
					os = new FileOutputStream(toFile);  
					//设置缓存  
					byte[] buffer = new byte[1024];  
					int length = 0;
					//读取myFile文件输出到toFile文件中  
					while ((length = is.read(buffer)) > 0) {  
					   os.write(buffer, 0, length);  
					} 
					if(is != null){
						is.close();   //关闭输入流
					}
					if(os != null){
						os.close();   //关闭输出流 
					}
					
					// 文件存储名称
					fm.setFileName(saveFileName);  
					
					// 页面显示名称
					targerFileName = genFileName(versonNo, ufileFileName);
					fileNumber = this.getFileNumber(fmger.getFileNumber(), c.getFileTypeNo());
					fm.setFileDisplayname(targerFileName); 
				} catch (FileNotFoundException e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IOException e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				}catch(Exception e) {
					msg = "修改失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}else{
				
				// 文件名称
				fm.setFileName(fmger.getFileName());     
				
				// 页面显示名称
				fileNumber = this.getFileNumber(fmger.getFileNumber(), c.getFileTypeNo());
				fm.setFileDisplayname(fmger.getFileDisplayname()); 
			}
			   
			fm.setFileTypeId(Integer.valueOf(fileTypeId));
			fm.setFileTypeSubId(Integer.valueOf(fileTypeSubId));
			fm.setFileNumber(fileNumber);             // 文件编号
			fm.setFileAddress(fmger.getFileAddress());           // 存储地址
			fm.setIsPublic(fmger.getIsPublic());                 // 默认为不公开
			fm.setVersion(versonNo);             // 版本号
			fm.setCreateUser(getLoginStaff().getLoginName());   
			fm.setUpdateUser(getLoginStaff().getLoginName());
			fm.setUpdateId(fmger.getUpdateId());  // 保留原始的数据ID
			fm.setIsValid(2);
			fm.setId(fid);
			fileManagerBiz.insertFileManager(fm);  //保存上传文件记录
			// 同时插入一天记录至文件组表
			FileGroup fg = new FileGroup();
			String ugroupId = fileGroupBiz.getGroupIdByFileId(updateId);
			fg.setGroupId(ugroupId);
			fg.setFileId(fid);
			fileGroupBiz.addFileGroup(fg);  // 保存文件流程组
			FileManager filemanager = fileManagerBiz.findFileManagerById(fid);
			jsonStr = gson.toJson(filemanager);
		}else{
			if(!allowUpload){
				msg = "type";
			}else if(!allowSize){
				msg = "size";
			}
			jsonStr = gson.toJson(msg);
		}
		return "json";
	}
	
	
	
	/**
	 * 审核界面新增后的保存操作
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveFileForAudit(){
		InputStream is = null;
		OutputStream os = null;
		Gson gson = new GsonBuilder().create();
		boolean allowUpload = this.validateFileType(fileFileName);
		boolean allowSize = this.validateFileSize(file);
		if(allowUpload && allowSize){
			String fid = String.valueOf(UUID.randomUUID());  // 生成唯一ID
			Integer versonNo = Integer.valueOf(1);   // 初始版本号
			DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileTypeSubId));
			try {
				// 文件上传暂存路径
				String fileSavePath = PerpertiesTool.getInstance().getProMap().get("FILE_SAVE_PATH");
				File f = new File(fileSavePath);
				if (!f.exists()) {            
					f.mkdirs();        
				}
				is = new FileInputStream(file);
				
				String saveFileName = fid + this.getFileFix(fileFileName);  // 使用uuid作为保存后的唯一附件名称
				
				File toFile = new File(fileSavePath, saveFileName);  
				// 创建一个输出流  
				os = new FileOutputStream(toFile);  
				//设置缓存  
				byte[] buffer = new byte[1024];  
				int length = 0;
				//读取myFile文件输出到toFile文件中  
				while ((length = is.read(buffer)) > 0) {  
				   os.write(buffer, 0, length);  
				} 
				
				// 新增一条记录
				fm = new FileManager();
				fm.setId(fid);
				fm.setFileTypeId(Integer.valueOf(fileTypeId));
				fm.setFileTypeSubId(Integer.valueOf(fileTypeSubId));
				fm.setFileNumber(this.getFileNumber(custCode, c.getFileTypeNo()));  // 唯一文件编号
				fm.setFileName(saveFileName);   // 文件名称
				fm.setFileDisplayname(genFileName(versonNo, fileFileName)); // 页面显示名称
				fm.setFileAddress(fileSavePath);  // 存储地址
				fm.setIsPublic(c.getIsPublic());  // 是否公开
				fm.setIsValid(2);                
				fm.setVersion(versonNo);   
				fm.setCreateUser(getLoginStaff().getLoginName());
				fm.setUpdateUser(getLoginStaff().getLoginName());
				fm.setUpdateId(fid);
				fileManagerBiz.insertFileManager(fm); //保存上传文件记录
				
				// 添加流程所属附件记录
				FileGroup fg = new FileGroup();
				fg.setGroupId(groupId);
				fg.setFileId(fid);
				fileGroupBiz.addFileGroup(fg);  // 保存文件流程组
				
				jsonStr = gson.toJson(fm);
				
			} catch (FileNotFoundException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch(Exception e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				try {
					if(is != null){
						is.close();   //关闭输入流
					}
					if(os != null){
						os.close();   //关闭输出流  
					}
				} catch (IOException e) {
					msg = "false";
					logger.error(e.getMessage());
				}  
			}
		}else{
			if(!allowUpload){
				msg = "type";
			}else if(!allowSize){
				msg = "size";
			}
			jsonStr = gson.toJson(msg);
		}
		return "json";
	}
	
	
	
	
	/**
	 * 附件管理的修改界面：保存修改附件
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveUpdateFile(){
		boolean isUpdate = true;
		boolean allowUpload = true;
		boolean allowSize = true;
		if(file != null && StringUtils.isNotBlank(fileFileName)){
			allowUpload = this.validateFileType(fileFileName);
			allowSize = this.validateFileSize(file);
			if(allowUpload && allowSize){
				isUpdate = true;
			}else{
				isUpdate = false;
			}
		}
		
		Gson gson = new GsonBuilder().create();
		if(isUpdate){
			FileManager fmer = fileManagerBiz.findFileManagerById(updateId);
			List<FileManager> flist = fileManagerBiz.findByFileUpdateId(fmer.getUpdateId());
			Integer versonNo = flist.get(0).getVersion();
			InputStream is = null;
			OutputStream os = null;
			DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileTypeSubId));
			String fileNumber = "";
			try {
				if(file != null && StringUtils.isNotBlank(fileFileName)){
					// 文件上传暂存路径
					String fileSavePath = PerpertiesTool.getInstance().getProMap().get("FILE_SAVE_PATH");
					File f = new File(fileSavePath);
					if (!f.exists()) {            
						f.mkdirs();        
					}
					is = new FileInputStream(file);
					
					fileNumber = this.getFileNumber(fmer.getFileNumber(), c.getFileTypeNo());
					
					targerFileName = genFileName(versonNo, fileFileName);
					
					// 服务器保存的文件名称(如果文件名已存在则重新覆盖)
					String saveFileName = fmer.getId() + this.getFileFix(fileFileName);  
					File toFile = new File(fileSavePath, saveFileName);  
					
					// 创建一个输出流  
					os = new FileOutputStream(toFile);  
					//设置缓存  
					byte[] buffer = new byte[1024];  
					int length = 0;
					//读取myFile文件输出到toFile文件中  
					while ((length = is.read(buffer)) > 0) {  
					   os.write(buffer, 0, length);  
					} 
					fmer.setFileName(saveFileName);
					fmer.setFileDisplayname(targerFileName);
				}else{
					fileNumber = this.getFileNumber(fmer.getFileNumber(), c.getFileTypeNo());
					fmer.setFileName(fmer.getFileName());    // 文件名称
					fmer.setFileDisplayname(fmer.getFileDisplayname()); // 页面显示名称
				}
				fmer.setFileTypeId(Integer.valueOf(fileTypeId));
				fmer.setFileTypeSubId(Integer.valueOf(fileTypeSubId));
				fmer.setFileNumber(fileNumber);
				fmer.setUpdateUser(getLoginStaff().getLoginName());
				fileManagerBiz.updateFileByObject(fmer);
				msg = "true";
			} catch (FileNotFoundException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (Exception e){
				msg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			}finally {
				try {
					if(is != null){
						is.close();   //关闭输入流
					}
					if(os != null){
						os.close();   //关闭输出流  
					}
				} catch (IOException e) {
					msg = "false";
					logger.error(e.getMessage());
				}  
			}
			jsonStr = gson.toJson(msg);
		}else{
			if(!allowUpload){
				msg = "type";
			}else if(!allowSize){
				msg = "size";
			}
			jsonStr = gson.toJson(msg);
		}
		return "json";
	}
	
	
	/**
	 * 文件下载
	 * @return
	 * @throws Exception
	 */
	public String filedownLoad() throws Exception{
		FileManager f = fileManagerBiz.findFileManagerById(fm.getId());
		String fileAdress = f.getFileAddress();
		String filename = f.getFileName();
		filedownAddress = fileAdress + "/" + filename;  
		this.templateFileName= new String(f.getFileDisplayname().getBytes("gb2312"),"iso8859-1");
		return SUCCESS;
	}
	
	public InputStream getTargetFile() throws Exception {
		// 如果客户端传过来了文件名,则返回文件到客户端
		File downFile = new File(filedownAddress);
		if (downFile.exists()) {
			targetFile = new FileInputStream(downFile);
		}
		return targetFile;
	}

	
	/**
	 * 生成文件名称
	 * @return
	 */
	public String genFileName(Integer versonNo,String fileName){
		int index = fileName.lastIndexOf(".");
		String name = fileName.substring(0, index);
		String fix = fileName.substring(index);
		String targerName = name + "V_"+ versonNo + fix;
		return targerName;
	}
	
	/**
	 * 获取上传文件的后缀名
	 * @return
	 */
	public String getFileFix(String fileName){
		if(StringUtils.isNotBlank(fileName)){
			int index = fileName.lastIndexOf(".");
			String fix = fileName.substring(index);
			return fix;
		}else{
			return "";
		}
	}
	
	
	/**
	 *  附件管理界面修改后，更新附件显示名称(旧的文件名称，新的文件序号规则，新上传的文件名称)
	 * @return
	 */
	public String getFileNumber(String fileDisplayName,String fileTypeNo){
		if(StringUtils.isNotBlank(fileDisplayName)){
			Pattern patternTmp = Pattern.compile("[a-zA-Z]+");
			Matcher matcherTmp = patternTmp.matcher(fileDisplayName);   
			String matchStrTmp = "";
			int index = 0;
			while (matcherTmp.find()) {
				if(index==0){
					matchStrTmp = matcherTmp.group();
					break;
				}
			}
			
			Map<String, Object> pramMap = new HashMap<String, Object>();
			// 需要自增长序号
			pramMap.put(IRuleGenerator.RULE_SEQ_REQ_FLAG, true);
			//源规则字符串
			pramMap.put(IRuleGenerator.RULE_SRC_RULE, matchStrTmp + fileTypeNo);
			//执行sql脚本，获取最新记录条数
			pramMap.put(IRuleGenerator.RULE_EXEC_SQL, "fmger_getFileNumberByName");
			// 根据源规则生成规则代码
			String idByRule = RuleGenerator.generateStrByRule(pramMap);
			
			FileManager fmager = fileManagerBiz.findFileManagerByFileNumber(idByRule);
			if(fmager == null){
				// 文件规则名称定义：  <'公司代码' + '文件序号和配置年月日类型  + 自增长序号'
				return idByRule;
			}else{
				idByRule = RuleGenerator.generateStrByRule(pramMap);  // 再次调用
				return idByRule;
			}
		}else{
			return "";
		}
		
	}
	
	
	// 验证文件上传类型
	@SuppressWarnings("static-access")
	public boolean validateFileType(String fileName){
		boolean allowUpload = false;
		if(StringUtils.isNotBlank(fileName)){
			int index = fileName.lastIndexOf(".");
			String fileType = fileName.substring(index+1);
			String allowTypeConfig = PerpertiesTool.getInstance().getProMap().get("ALLOW_UPLOAD_TYPE");
			if(StringUtils.isNotBlank(allowTypeConfig)){
				String[]allowTypes = allowTypeConfig.split(",");   // 允许上传的附件类型
				for(int m=0;m<allowTypes.length;m++){
					if(fileType.equalsIgnoreCase(allowTypes[m])){
						allowUpload = true;
						break;
					}
				}
			}else{
				allowUpload = true;
			}
		}
		return allowUpload;
	}
	
	// 验证文件上传附件不能大于10M
	public boolean validateFileSize(File file){
		boolean allowSize = false;
		if(file.exists()){
			long length = file.length();
			if(length <= 10*1024*1024){
				allowSize = true;
			}
		}
		return allowSize;
	}
	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	
	
	public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileFileContentType() {
        return fileFileContentType;
    }

    public void setFileFileContentType(String fileFileContentType) {
        this.fileFileContentType = fileFileContentType;
    }
	
	
	public File getUfile() {
		return ufile;
	}
	public void setUfile(File ufile) {
		this.ufile = ufile;
	}
	
	public String getUfileFileName() {
		return ufileFileName;
	}
	public void setUfileFileName(String ufileFileName) {
		this.ufileFileName = ufileFileName;
	}


	public FileManagerBiz getFileManagerBiz() {
		return fileManagerBiz;
	}

	public void setFileManagerBiz(FileManagerBiz fileManagerBiz) {
		this.fileManagerBiz = fileManagerBiz;
	}
	
	public FileGroupBiz getFileGroupBiz() {
		return fileGroupBiz;
	}
	public void setFileGroupBiz(FileGroupBiz fileGroupBiz) {
		this.fileGroupBiz = fileGroupBiz;
	}

	public DictionaryConfigBiz getDictionaryConfigBiz() {
		return dictionaryConfigBiz;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}
	
	public FileManager getFm() {
		return fm;
	}

	public void setFm(FileManager fm) {
		this.fm = fm;
	}
	
	public void setTargetFile(InputStream targetFile) {
		this.targetFile = targetFile;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTargerFileName() {
		return targerFileName;
	}

	public void setTargerFileName(String targerFileName) {
		this.targerFileName = targerFileName;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}


	public String getFileTypeSubId() {
		return fileTypeSubId;
	}
	public void setFileTypeSubId(String fileTypeSubId) {
		this.fileTypeSubId = fileTypeSubId;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
}
