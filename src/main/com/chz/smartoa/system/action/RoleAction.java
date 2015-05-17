package com.chz.smartoa.system.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.fileUpload.pojo.RoleFiletype;
import com.chz.smartoa.fileUpload.service.RoleFiletypeBiz;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.ResourceBiz;
import com.chz.smartoa.system.service.RoleBiz;

public class RoleAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * logger.
	 */
	private static final Logger logger = Logger.getLogger(RoleAction.class);

	private String assignType;
	private String staffId;
	private String departmentId;
	private String resourceIds;
	private String unAllowFiles;
	private String[] roleId;
	private String roleIds;
	private String oldRoelName = "";
	private Role role = new Role();
	private List<Role> roles;
	private RoleBiz roleBiz;
	private ResourceBiz resourceBiz;
	private List<Resource> resources;
	private OperateLogBiz operateLogBiz;
	private DictionaryConfigBiz dictionaryConfigBiz;
	private RoleFiletypeBiz roleFiletypeBiz;
	private List<DictionaryConfig> dictionaryList;
	private String dataTypeHid;

	/**
	 * 列表用户
	 * @return
	 */
	public String list() {
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(role);
		// 得到分页结果
		roles = roleBiz.listRole(role);
		// 封装数据
		dataGrid = new DataGrid(roleBiz.listRoleCount(role), roles);
		return DATA_GRID;
	}

	/**
	 * 增加角色前
	 * @return
	 */
	public String resource() {
		// 查询系统所有操作资源
		resources = resourceBiz.getResources("root",null,Resource.AUTH_TYPE_AUTH);
		if(roleId != null){
			role = roleBiz.findRole(roleId[0]);
			checkedResource(resources, role.getResourceIds());
		}
		treeData = generateTree(resources);
		return TREE_DATA;
	}
	
	
	
	/**
	 * 不允许文件类型设置列表
	 * @param resources
	 * @return
	 */
	public String unAllowfileTypes(){
		dictionaryList = dictionaryConfigBiz.getDictionaryConfigAllForIsValid();
		if(roleId != null){
			role = roleBiz.findRole(roleId[0]);
			RoleFiletype allowRoleFile = new RoleFiletype();
			allowRoleFile.setRoleId(Integer.valueOf(role.getRoleId()));  // 根据登录获取用户角色ID
			allowRoleFile.setIsSee(Integer.valueOf(2));   // 不可看
			List<String> unAllowFileList = roleFiletypeBiz.getListByRole(allowRoleFile);
			checkedResourceForDictionary(dictionaryList,unAllowFileList);
		}
		treeData = generateTreeForDictionary(dictionaryList);
		return TREE_DATA;
	}
	
	
	
	private List<TreeData> generateTree(List<Resource> resources){
		List<TreeData> tree = new ArrayList<TreeData>();
		for (Resource entry : resources) {
			TreeData data = new TreeData(entry.getResourceId(),entry.getResourceName(),entry.getChecked());
			if( entry.getResources() != null && entry.getResources().size() > 0 ){
				data.setChildren(generateTree(entry.getResources()));
			}
			tree.add(data);
		}
		return tree;
	}
	
	
	
	private List<TreeData> generateTreeForDictionary(List<DictionaryConfig> list){
		List<TreeData> tree = new ArrayList<TreeData>();
		for (DictionaryConfig entry : list) {
			TreeData data = new TreeData(entry.getId().toString(),entry.getDictionaryName(),entry.getChecked());
			if( entry.getDictionaryList() != null && entry.getDictionaryList().size() > 0 ){
				data.setChildren(generateTreeForDictionary(entry.getDictionaryList()));
			}
			tree.add(data);
		}
		return tree;
	}
	
	

	/**
	 * 增加角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		// 检查名称是否重复
		Role tmpRole = roleBiz.findRoleByName(role.getRoleName()); 
		if (tmpRole != null) {
			operateResult = new OperateResult(-1, "角色名称己存在!");
			return OPER_RESULT;
		}

		Staff loginStaff = getLoginStaff();
		role.setCreateUser(loginStaff.getLoginName());
		role.setDataType(dataTypeHid);
		String id = "";
		try {
			// 增加角色
			String[] array = null;
			if(resourceIds != null)
				array = resourceIds.split(",");
			id = roleBiz.insertRole(role,array);
			
			
			// 不允许查看的文件类型
			String[] unAllowArr = null;
			if(StringUtils.isNotEmpty(unAllowFiles)){
				unAllowArr = unAllowFiles.split(",");
				RoleFiletype urf = new RoleFiletype();
				urf.setRoleId(Integer.valueOf(id));
				urf.setIsSee(2);  // 不可看
				roleFiletypeBiz.batchSaveUpdate(unAllowArr, urf);
			}
			
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.ROLE_MANAGE,roleId.toString(),role.getRoleName(), "新增失败");
			throw e;
		}
		operateLogBiz.info(OperateLogType.ROLE_MANAGE,id,role.getRoleName(), "新增成功");
		operateResult = new OperateResult(1, "新增角色成功！");
		return OPER_RESULT;
	}

	/**
	 * 修改角色.
	 * 
	 * @return
	 */
	public String beforeUpdate() {
		
		// 参数检查
		if (roleId == null || roleId.length == 0) {
			setErrorMsg("请选择需要修改的角色!");
			return "commonFailure";
		}
		// 查询角色详情
		role = roleBiz.findRole(roleId[0]);
		return "beforeUpdate";
	}
	
	private void checkedResource(List<Resource> resources,List<String> resourceIds){
		if(resources != null && resourceIds != null){
			for (Resource r : resources) {
				if(resourceIds.contains(r.getResourceId())&&!r.getParentId().equals("root")){
					r.setChecked(true);
				}
				checkedResource(r.getResources(), resourceIds);
			}
		}
	}
	
	
	private void checkedResourceForDictionary(List<DictionaryConfig> dictionaryList,List<String> fileList){
		if(dictionaryList != null && fileList != null){
			for (DictionaryConfig d : dictionaryList) {
				if(fileList.contains(d.getId().toString())){
					d.setChecked(true);
				}
				checkedResourceForDictionary(d.getDictionaryList(), fileList);
			}
		}
	}
	

	/**
	 * 修改角色.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if(isdo !=1 ){
			return beforeUpdate();
		}
		// 检查名称是否重复
		Role tmpRole = roleBiz.findRoleByName(role.getRoleName());
		if (tmpRole != null&& !tmpRole.getRoleId().equals(role.getRoleId())) {
			operateResult = new OperateResult(-1, "角色名称己存在!");
			return OPER_RESULT;
		}
		Staff loginStaff = getLoginStaff();
		role.setCreateUser(loginStaff.getLoginName());
		role.setDataType(dataTypeHid);
		try {
			// 增加角色
			String[] array = null;
			if(resourceIds != null)
				array = resourceIds.split(",");
			roleBiz.updateRole(role, array);
			
			
			// 修改不允许查看的文件类型
			String[] unAllowArr = null;
			if(StringUtils.isNotEmpty(unAllowFiles)){
				unAllowArr = unAllowFiles.split(",");
				RoleFiletype urf = new RoleFiletype();
				urf.setRoleId(Integer.valueOf(role.getRoleId()));
				urf.setIsSee(2);  // 不可看
				roleFiletypeBiz.batchSaveUpdate(unAllowArr, urf);
			}
			
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.ROLE_MANAGE, String.valueOf(role.getRoleId()),String.valueOf(role.getRoleName()), "修改失败");
			throw e;
		}
		operateLogBiz.info(OperateLogType.ROLE_MANAGE, String.valueOf(role.getRoleId()),String.valueOf(role.getRoleName()), "修改成功");
		// 调用列表方法
		role = new Role();
		operateResult = new OperateResult(1, "修改角色成功！");
		return OPER_RESULT;
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			// 修改组织或员工角色
			if(roleIds!=null&&!"".equals(roleIds)){
				roleId = roleIds.split(",");
			}
			// 删除角色
			roleBiz.deleteRole(roleId);
			//operateLogBiz.info(OperateLogType.ROLE_MANAGE, roleId.toString(), roleId.toString(), "删除成功");
			operateResult = new OperateResult(1, "删除角色成功！");
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.ROLE_MANAGE, roleId.toString(), roleId.toString(), "删除失败");
			operateResult = new OperateResult(-1, "删除角色失败！");
			logger.error("删除角色失败:"+e);
		}
		return OPER_RESULT;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleBiz(RoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	

	public void setUnAllowFiles(String unAllowFiles) {
		this.unAllowFiles = unAllowFiles;
	}

	public String[] getRoleId() {
		return roleId;
	}

	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResourceBiz(ResourceBiz resourceBiz) {
		this.resourceBiz = resourceBiz;
	}
	public String getOldRoelName() {
		return oldRoelName;
	}
	public void setOldRoelName(String oldRoelName) {
		this.oldRoelName = oldRoelName;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}
	
	public void setRoleFiletypeBiz(RoleFiletypeBiz roleFiletypeBiz) {
		this.roleFiletypeBiz = roleFiletypeBiz;
	}

	public List<DictionaryConfig> getDictionaryList() {
		return dictionaryList;
	}
	public void setDictionaryList(List<DictionaryConfig> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}

	public void setDataTypeHid(String dataTypeHid) {
		this.dataTypeHid = dataTypeHid;
	}
	
}
