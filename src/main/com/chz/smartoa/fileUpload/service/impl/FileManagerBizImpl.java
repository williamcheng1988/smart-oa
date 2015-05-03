package com.chz.smartoa.fileUpload.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.fileUpload.dao.FileManagerDao;
import com.chz.smartoa.fileUpload.dao.RoleFiletypeDao;
import com.chz.smartoa.fileUpload.pojo.FileManager;
import com.chz.smartoa.fileUpload.pojo.RoleFiletype;
import com.chz.smartoa.fileUpload.service.FileManagerBiz;
import com.chz.smartoa.fileUpload.util.FileQuery;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.pojo.Permission;
import com.chz.smartoa.system.pojo.Role;


/**
 * 
 * @author Zhao
 *
 */
public class FileManagerBizImpl implements FileManagerBiz{
	
	private FileManagerDao fileManagerDao;
	public FileManagerDao getFileManagerDao() {
		return fileManagerDao;
	}
	public void setFileManagerDao(FileManagerDao fileManagerDao) {
		this.fileManagerDao = fileManagerDao;
	}
	
	private RoleDao roleDao;
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	private RoleFiletypeDao roleFiletypeDao;
	public RoleFiletypeDao getRoleFiletypeDao() {
		return roleFiletypeDao;
	}
	public void setRoleFiletypeDao(RoleFiletypeDao roleFiletypeDao) {
		this.roleFiletypeDao = roleFiletypeDao;
	}
	
	
	
	/**
	 * 新增
	 */
	@Override
	public String insertFileManager(FileManager fm) {
		return fileManagerDao.insertFileManager(fm);
	}

	
	/**
	 * 批量删除(即设为无效状态)
	 */
	@Override
	public void deleteFileManager(String[]arrMainId,Integer isValid) {
		if(arrMainId != null && arrMainId.length >= 1){
			for(int i=0;i<arrMainId.length;i++){
				FileManager fger = this.findFileManagerById(arrMainId[i]);
				fger.setIsValid(isValid);
				this.updateFileByObject(fger);
			}
		}
	}
	
	
	/**
	 * 批量更新附件对应状态
	 */
	@Override
	public void updateFileStatusByIDs(String[] arrayId,Integer isValid) {
		fileManagerDao.updateFileStatusByIDs(arrayId,isValid);
	}

	
	/**
	 * 批量更新附件对应状态
	 */
	public void updateFileStatusByGroupId(Long groupId, Integer isValid){
		fileManagerDao.updateFileStatusByGroupId(groupId,isValid);
	}

	/**
	 * 根据主键ID获取附件对象
	 */
	@Override
	public FileManager findFileManagerById(String id) {
		return fileManagerDao.findFileManagerById(id);
	}

	@Override
	public FileManager findFileManagerByFileNumber(String fileNumber) {
		return fileManagerDao.findFileManagerByFileNumber(fileNumber);
	}

	/**
	 * 根据查询条件获取附件列表
	 */
	@Override
	public List<FileManager> findFileManager(FileQuery fm, int start,int limit) {
		if(StringUtils.isNotEmpty(fm.getLoginName())){
			List<Role> roles = roleDao.listStaffRole(fm.getLoginName());
			List<String> roleIds = null;  // 不可看list
			List<String> unAllList = null;
			if(roles != null && roles.size() >= 1){
				RoleFiletype unAllowRf = new RoleFiletype();
				unAllowRf.setIsSee(2);   // 不可看
				roleIds = new ArrayList<String>();
				for(int i=0;i<roles.size();i++){
					roleIds.add(roles.get(i).getRoleId());
				}
				unAllowRf.setRoleIds(roleIds);  // 设置该用户对应的所有角色
				// 获取该角色下不允许查看 的文件类型
				unAllList = roleFiletypeDao.getListByRole(unAllowRf);  
			}
			fm.setUnAllowFileTypes(unAllList);
		}
		return fileManagerDao.findFileManager(fm, start, limit);
	}

	/**
	 * 根据查询条件获取附件列表总数
	 */
	@Override
	public int getFileManagerCount(FileQuery fm) {
		return fileManagerDao.getFileManagerCount(fm);
	}
	
	/**
	 * 根据修改ID获取附件列表
	 */
	@Override
	public List<FileManager> findByFileUpdateId(String updateId) {
		return fileManagerDao.findByFileUpdateId(updateId);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void updateFileByObject(FileManager fm) {
		fileManagerDao.updateFileByObject(fm);
	}
	
	/**
	 * 批量工卡或不公开
	 */
	@Override
	public void toUnorPublic(String[] arrMainId,Integer ispublic) {
		if(arrMainId != null && arrMainId.length >= 1){
			for(int i=0;i<arrMainId.length;i++){
				FileManager fger = this.findFileManagerById(arrMainId[i]);
				fger.setIsPublic(ispublic);
				this.updateFileByObject(fger);
			}
		}
	}
	
	/**
	 * 根据groupID获取附件列表
	 */
	@Override
	public List<FileManager> getFileListByGroupId(String groupId,String loginName) {
		List<Role> roles = roleDao.listStaffRole(loginName);
		List<String> roleIds = null;  // 不可看list
		List<String> unAllList = null;
		if(roles != null && roles.size() >= 1){
			RoleFiletype unAllowRf = new RoleFiletype();
			unAllowRf.setIsSee(2);   // 不可看
			roleIds = new ArrayList<String>();
			for(int i=0;i<roles.size();i++){
				roleIds.add(roles.get(i).getRoleId());
			}
			unAllowRf.setRoleIds(roleIds);  // 设置该用户对应的所有角色
			// 获取该角色下不允许查看 的文件类型
			unAllList = roleFiletypeDao.getListByRole(unAllowRf);  
		}
		return fileManagerDao.getFileListByGroupId(groupId,loginName,unAllList);
	}
	
	
	@Override
	public void deleteFileManager(String id) {
		fileManagerDao.deleteFileManager(id);
	}
	
	
	/**
	 *  根据登录用户获取所有权限下的可访问的附件列表ID
	 */
	@Override
	public List<String> getPerssionFileList(FileQuery fq) {
		return fileManagerDao.getPerssionFileList(fq);
	}
	
	// 获取最新公共信息用于首页显示的最新记录
	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> disPlayLastFilesForPublic() {
		List<FileManager> lastList = null;
		FileQuery fquery = new FileQuery();
		fquery.setDicType(Integer.valueOf(1));  // 公共信息
		int allPerssion = 0;
		if(LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.ALL_DATA) != null){
			allPerssion = (Integer)LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.ALL_DATA);
		}
		if(allPerssion != 1 ){
			List<String> projectList = (List<String>)LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.PROJECT_IDS);
			fquery.setProjectIds(projectList);  // 项目权限ID
			fquery.setLoginName(LoginUtils.getLoginStaff().getLoginName());
			List<String> fileIds = this.getPerssionFileList(fquery);  // 根据允许的项目ID获取所有可访问的附件ID列表
			fquery.setFileIds(fileIds);
		}
		lastList = fileManagerDao.getLastFiles(fquery);
		return lastList;
	}
	
	// 获取最新技术信息用于首页显示的最新记录
	@SuppressWarnings("unchecked")
	@Override
	public List<FileManager> disPlayLastFilesFortechnology() {
		List<FileManager> lastList = null;
		FileQuery fquery = new FileQuery();
		fquery.setDicType(Integer.valueOf(2));  // 公共信息
		int allPerssion = 0;
		if(LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.ALL_DATA) != null){
			allPerssion = (Integer)LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.ALL_DATA);
		}
		if(allPerssion != 1 ){
			List<String> projectList = (List<String>)LoginUtils.getLoginStaff().getPermission().getDataPermissions().get(Permission.PROJECT_IDS);
			fquery.setProjectIds(projectList);  // 项目权限ID
			fquery.setLoginName(LoginUtils.getLoginStaff().getLoginName());
			List<String> fileIds = this.getPerssionFileList(fquery);  // 根据允许的项目ID获取所有可访问的附件ID列表
			fquery.setFileIds(fileIds);
		}
		lastList = fileManagerDao.getLastFiles(fquery);
		return lastList;
	}
	
	
	
	@Override
	public List<FileManager> findFileListByRole(FileQuery fq, int start,int limit) {
		return fileManagerDao.findFileListByRole(fq, start, limit);
	}
	
	
	@Override
	public int getFileCountByRole(FileQuery fq) {
		return fileManagerDao.getFileCountByRole(fq);
	}

}
