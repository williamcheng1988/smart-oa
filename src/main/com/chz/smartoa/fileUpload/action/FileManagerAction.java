package com.chz.smartoa.fileUpload.action;


import java.awt.Desktop;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.pojo.RoleFiletype;
import com.chz.smartoa.fileUpload.service.FileGroupBiz;
import com.chz.smartoa.fileUpload.service.FileManagerBiz;
import com.chz.smartoa.fileUpload.service.RoleFiletypeBiz;
import com.chz.smartoa.fileUpload.util.FileQuery;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Controller
public class FileManagerAction extends BaseAction{

	private static final long serialVersionUID = -7059659507390107526L;
	
	private static final Logger logger = Logger.getLogger(FileManagerAction.class);
	
	private FileManagerBiz fileManagerBiz;
	
	private DictionaryConfigBiz dictionaryConfigBiz;
	
	private RoleFiletypeBiz roleFiletypeBiz;
	
	private FileManager fm;
	
	private DictionaryConfig dc;
	
	private String dictionaryId;
	
	private List<FileManager> fileList;
	
	private List<DictionaryConfig> dictionaryList;
	
	private List<DictionaryConfig> dictionarySublist;
	
	private String jsonStr;
	
	private String mainId;
	
	private String mainIds;
	
	// 保存的目标文件名称
	private String targerFileName;
	
	// 上传的文件名称
	private String uploadFileName;
	
	// 上传文件的类型
	private String uploadContentType;
	
	private String fileId;
	
	private String filetypeName;
	
	private String dicType;   
	
	private String fileTypeId;
	
	private String fileTypeSubId;
	
	private String updateId;
	
	private String parentId;
	
	private String menuType;   
	
	private String groupId;
	
	private String selectType;
	
	
	// 打印信息
	private String msg;
	
	private FileQuery fquery;

	private FileGroupBiz fileGroupBiz;
	
	
	private String queryName;
	private String startDt;
	private String endDt;
	
	
	
	/**
	 * 公共信息
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public String listForPublic(){
		try {
			if(fquery == null){
				if(StringUtils.isNotBlank(queryName) || StringUtils.isNotBlank(startDt) || StringUtils.isNotBlank(endDt) ||
						StringUtils.isNotBlank(fileTypeId) || StringUtils.isNotBlank(menuType)){
					fquery = new FileQuery();
					fquery.setQueryName(queryName);
					fquery.setFileTypeId(Integer.valueOf(fileTypeId));
					fquery.setMenuType(menuType);
					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(StringUtils.isNotEmpty(startDt)){
							fquery.setStartDt(format.parse(startDt));
						}
						if(StringUtils.isNotEmpty(endDt)){
							fquery.setEndDt(format.parse(endDt));
						}
					} catch (ParseException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			}
			
			if(fquery != null){
				
				fquery.setDicType(1);   // 公共信息
				setPagination(fquery);
				fileList = fileManagerBiz.findFileManager(fquery, fquery.getStart(), fquery.getLimit());
				int count = fileManagerBiz.getFileManagerCount(fquery);
				if(count > 0){
					dataGrid = new DataGrid(count,fileList);
				}else{
					dataGrid = new DataGrid(0,new ArrayList());
				}
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return DATA_GRID;
	}
	
	
	/**
	 * 技术信息
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public String listFortechnology(){
		try {
			
			if(fquery == null){
				if(StringUtils.isNotBlank(queryName) || StringUtils.isNotBlank(startDt) || StringUtils.isNotBlank(endDt) ||
						StringUtils.isNotBlank(fileTypeId) || StringUtils.isNotBlank(menuType)){
					fquery = new FileQuery();
					fquery.setQueryName(queryName);
					fquery.setFileTypeId(Integer.valueOf(fileTypeId));
					fquery.setMenuType(menuType);
					try {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(StringUtils.isNotBlank(startDt)){
							fquery.setStartDt(format.parse(startDt));
						}
						if(StringUtils.isNotBlank(endDt)){
							fquery.setEndDt(format.parse(endDt));
						}
					} catch (ParseException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			}
			
			
			if(fquery != null){
				fquery.setDicType(2);   // 技术信息
				setPagination(fquery);
				fileList = fileManagerBiz.findFileManager(fquery, fquery.getStart(), fquery.getLimit());
				int count = fileManagerBiz.getFileManagerCount(fquery);
				if(count > 0){
					dataGrid = new DataGrid(count,fileList);
				}else{
					dataGrid = new DataGrid(0,new ArrayList());
				}
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return DATA_GRID;
	}
	
	/**
	 * 查询列表数据
	 * @return
	 */
	/**
	public String query(){
		FileQuery q = new FileQuery();
		try {
			if(StringUtils.isNotEmpty(menuType)){
				q.setMenuType(menuType);
			}
			if(StringUtils.isNotBlank(fileTypeId)){
				q.setFileTypeId(Integer.valueOf(fileTypeId));
			}
			fileList = fileManagerBiz.findFileManager(q, 0, 30,false);
			dataGrid = new DataGrid(1,fileList);
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		return DATA_GRID;
	}
	*/
	

	
	// 获取点击树形菜单的文件类型名称
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getQueryFileTypeName(){
		DictionaryConfig cg = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileId));
		if(cg != null){
			filetypeName = cg.getDictionaryName();
			fileTypeId = cg.getId().toString();
		}
		List myList = new ArrayList();
		myList.add(filetypeName);
		myList.add(fileTypeId);
		myList.add(menuType);
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(myList);
		return "json";
	}
	 
	
	
	public String upload(){
		return "fileList";
	}
	
	
	// 附件上传时文件修改界面
	public String addWindow(){
		if(StringUtils.isEmpty(dictionaryId)){
			dictionaryId = "93";   // 暂时文件类型
		}
		dictionaryList = dictionaryConfigBiz.findCfgByParentIdForIsvalid(Integer.valueOf(dictionaryId));
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(dictionaryList);
		return "json";
	}
	
	
	// 附件上传后可进行修改操作
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateWindow(){
		List allList = new ArrayList();
		fm = fileManagerBiz.findFileManagerById(fileId);
		DictionaryConfig cg = dictionaryConfigBiz.findDictionarytCfgById(fm.getFileTypeId());
		dictionaryList = dictionaryConfigBiz.findCfgByParentIdForIsvalid(cg.getParentId());
		dictionarySublist = dictionaryConfigBiz.findCfgByParentIdForIsvalid(fm.getFileTypeId());
		allList.add(dictionaryList);
		allList.add(dictionarySublist);
		allList.add(fm.getFileTypeId());
		allList.add(fm.getFileTypeSubId());
		allList.add(fm.getId());
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(allList);
		return "json";
	}
	
	/**
	 * 获取字段子项列表
	 * @return
	 */
	public String getSonList(){
		dictionaryList = dictionaryConfigBiz.findCfgByParentIdForIsvalid(Integer.valueOf(dictionaryId));
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(dictionaryList);
		return "json";
	}
	
	/**
	 * 附件管理模块的文件修改界面
	 * @return
	 */
	public String toEditpage(){
		fm = fileManagerBiz.findFileManagerById(fm.getId());  // 获取文件修改的数据对象
		DictionaryConfig cg = dictionaryConfigBiz.findDictionarytCfgById(fm.getFileTypeId());
		dictionaryList = dictionaryConfigBiz.findCfgByParentIdForIsvalid(cg.getParentId());
		dictionarySublist = dictionaryConfigBiz.findCfgByParentIdForIsvalid(fm.getFileTypeId());
		return "fileEdit";
	}
	
	
	/**
	 * 删除操作
	 * @return
	 */
	public String delete(){
		try {
			String[]arrMainId = mainIds.split(",");
			fileManagerBiz.deleteFileManager(arrMainId, Integer.valueOf(3));
			msg ="true";
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "false";
		}
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(msg);
		return "json";
	}
	
	
	public String deleteForPage(){
		fileManagerBiz.deleteFileManager(mainIds);
		return null;
	}
	
	
	// 公开
	public String toPublic(){
		try {
			String[]arrMainId = mainIds.split(",");
			fileManagerBiz.toUnorPublic(arrMainId,Integer.valueOf(1));
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.error(e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(msg);
		return "json";
	}
	
	
	// 不公开
	public String unPublic(){
		try {
			String[]arrMainId = mainIds.split(",");
			fileManagerBiz.toUnorPublic(arrMainId,Integer.valueOf(2));
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.error(e.getMessage());
		}
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(msg);
		return "json";
	}

	
	/**
	 * 根据groupId获取流程申请时添加的附件列表
	 * @return
	 */
	public String getGroupFilelist(){
		try {
			if(StringUtils.isNotBlank(groupId)){
				fileList = fileManagerBiz.getFileListByGroupId(groupId,getLoginStaff().getLoginName());
				if(fileList != null && fileList.size() >= 1){
					SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
					for(int i=0;i<fileList.size();i++){
						if(fileList.get(i).getCreateDt() != null){
							fileList.get(i).setCreateDtStr(format.format(fileList.get(i).getCreateDt()));
						}else{
							fileList.get(i).setCreateDtStr("");
						}
					}
				}
			}
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(fileList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	
	/**
	 * 打开附件内容
	 * @return
	 */
	public String examineFileContent(){
		fm = fileManagerBiz.findFileManagerById(fileId);
		String fileAdress = fm.getFileAddress();
		String filename = fm.getFileName();
		String openFile = fileAdress + "/" + filename; 
		File file = new File(openFile);
		try {
			Desktop.getDesktop().open(file);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
			logger.error(e.getMessage());
		}
		if("false".equals(msg)){
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson("false");
			return "json";
		}else{
			return null;
		}
	}
	
	
	/**
	 * 首页最新信息显示:公共信息
	 * @return
	 */
	public String displayInfoForPublic(){
		try {
			
			fquery = new FileQuery();
			fquery.setDicType(1);   // 公共信息
			fquery.setFileStatus("2"); // 只显示审核通过的
			fquery.setLoginName(getLoginStaff().getLoginName());
			setPagination(fquery);
			List<FileManager> pList = fileManagerBiz.findFileManager(fquery, fquery.getStart(), 8);
			if(pList != null && pList.size() >= 1){
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
				for(int i=0;i<pList.size();i++){
					if(pList.get(i).getCreateDt() != null){
						pList.get(i).setCreateDtStr(format.format(pList.get(i).getCreateDt()));
					}
				}
			}
			
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(pList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	
	/**
	 * 首页最新信息显示:技术信息
	 * @return
	 */
	public String displayInfoForTechnology(){
		try {
			fquery = new FileQuery();
			fquery.setDicType(2);   // 技术信息
			fquery.setFileStatus("2"); // 只显示审核通过的
			fquery.setLoginName(getLoginStaff().getLoginName());
			setPagination(fquery);
			List<FileManager> tList = fileManagerBiz.findFileManager(fquery, fquery.getStart(), 8);
			if(tList != null && tList.size() >= 1){
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
				for(int i=0;i<tList.size();i++){
					if(tList.get(i).getCreateDt() != null){
						tList.get(i).setCreateDtStr(format.format(tList.get(i).getCreateDt()));
					}
				}
			}
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(tList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	
	
	/**
	 * 查看文件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findFileList(){
		try {
			
			if(fquery == null){
				fquery = new FileQuery();
			}
			
			/**
			 * 获取不可看的文件类型List
			 */
			RoleFiletype unAllowRoleFile = new RoleFiletype();
			unAllowRoleFile.setIsSee(Integer.valueOf(2));   // 不可看
			// 获取不可看的文件类型列表
			List<String> unAllowFileList = roleFiletypeBiz.getListByRole(unAllowRoleFile,getLoginStaff().getLoginName());
			fquery.setUnAllowFileTypes(unAllowFileList);   // 排除不可看的
			fquery.setLoginName(getLoginStaff().getLoginName());
			fquery.setFileStatus("2"); // 只查审核通过的
			if(StringUtils.isNotBlank(fquery.getSelectType())){
				fquery.setDicType(Integer.valueOf(fquery.getSelectType()));
				selectType = fquery.getSelectType();
			}
			setPagination(fquery);
			fileList = fileManagerBiz.findFileListByRole(fquery, fquery.getStart(), fquery.getLimit());
			int count = fileManagerBiz.getFileCountByRole(fquery);
			if(fileList != null && fileList.size() >= 0){
				dataGrid = new DataGrid(count,fileList);
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return DATA_GRID;
	}
	
	
	
	
	/**
	 * 文件更多查询
	 * @return
	 */
	public String queryFilelistForMore(){
		return "morelist";
	}

	
	
	
	public FileManagerBiz getFileManagerBiz() {
		return fileManagerBiz;
	}

	public void setFileManagerBiz(FileManagerBiz fileManagerBiz) {
		this.fileManagerBiz = fileManagerBiz;
	}
	
	public DictionaryConfigBiz getDictionaryConfigBiz() {
		return dictionaryConfigBiz;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}
	
	public RoleFiletypeBiz getRoleFiletypeBiz() {
		return roleFiletypeBiz;
	}
	public void setRoleFiletypeBiz(RoleFiletypeBiz roleFiletypeBiz) {
		this.roleFiletypeBiz = roleFiletypeBiz;
	}

	public FileManager getFm() {
		return fm;
	}

	public void setFm(FileManager fm) {
		this.fm = fm;
	}
	
	public DictionaryConfig getDc() {
		return dc;
	}

	public void setDc(DictionaryConfig dc) {
		this.dc = dc;
	}
	
	public String getDictionaryId() {
		return dictionaryId;
	}


	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	
	public List<FileManager> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileManager> fileList) {
		this.fileList = fileList;
	}


	public List<DictionaryConfig> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<DictionaryConfig> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}

	public List<DictionaryConfig> getDictionarySublist() {
		return dictionarySublist;
	}

	public void setDictionarySublist(List<DictionaryConfig> dictionarySublist) {
		this.dictionarySublist = dictionarySublist;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFiletypeName() {
		return filetypeName;
	}

	public void setFiletypeName(String filetypeName) {
		this.filetypeName = filetypeName;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
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

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getMainIds() {
		return mainIds;
	}
	public void setMainIds(String mainIds) {
		this.mainIds = mainIds;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getTargerFileName() {
		return targerFileName;
	}

	public void setTargerFileName(String targerFileName) {
		this.targerFileName = targerFileName;
	}

	public FileQuery getFquery() {
		return fquery;
	}

	public void setFquery(FileQuery fquery) {
		this.fquery = fquery;
	}


	public FileGroupBiz getFileGroupBiz() {
		return fileGroupBiz;
	}


	public void setFileGroupBiz(FileGroupBiz fileGroupBiz) {
		this.fileGroupBiz = fileGroupBiz;
	}


	public String getQueryName() {
		return queryName;
	}


	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


	public String getStartDt() {
		return startDt;
	}


	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}


	public String getEndDt() {
		return endDt;
	}


	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}


	
	
}
