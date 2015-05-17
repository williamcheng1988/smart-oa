package com.chz.smartoa.system.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.service.DepartmentBiz;


@Controller
public class DepartmentAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DepartmentAction.class);
	
	private List<Department> departmentList;
	
	private DepartmentBiz departmentBiz;
	
	private Department dpment;
	
	private String parentId;
	
	private String departmentId;
	
	private String parentName;
	
	private String msg;
	
	private String jsonStr;
	
	
	@SuppressWarnings("rawtypes")
	public String queryDepartment(){
		try {
			if(StringUtils.isNotEmpty(parentId)){
				departmentList = departmentBiz.getDepartmentByParentId(parentId);
				dataGrid = new DataGrid(1,departmentList);
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	public String genDepartmentTree(){
		List<TreeData> treeList = generateTree("");
		treeData = treeList;
		return TREE_DATA;
	}
	
	
	private List<TreeData> generateTree(String parentId){
		List<TreeData> list = departmentBiz.getDepartmentTreeByParentId(parentId); 
		if(list != null && list.size() > 0){
			for (TreeData tree: list) {
				List<TreeData> child = generateTree(tree.getId()); 
				if(child != null && child.size() > 0){
					tree.setChildren(child);
				}
			}
		}
		return list;
	}
	
	
	
	public String insetDepartment(){
		return "toAdd";
	}
	
	
	public String saveDepartment(){
		try {
			Department department = departmentBiz.findDepartment(dpment.getDepartmentId());
			if(department != null){
				msg = "exist";
			}else{
				if(dpment != null){
					dpment.setStatus(1);  // 有效
					dpment.setCreateUser(getLoginStaff().getCreateUser());
					departmentBiz.insertDepartment(dpment);
					msg ="true";
				}else{
					msg ="false";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			msg ="false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	public String toEditPage(){
		if(StringUtils.isNotEmpty(departmentId)){
			dpment = departmentBiz.findDepartment(departmentId);
			if(dpment != null){
				if(departmentBiz.findDepartment(dpment.getParentId()) != null){
					parentName = departmentBiz.findDepartment(dpment.getParentId()).getDepartmentName();
				}
			}
		}
		return "toModify";
	}
	
	public String saveUpdate(){
		try {
			if(StringUtils.isNotEmpty(dpment.getDepartmentId())){
				Department uDepartment = departmentBiz.findDepartment(dpment.getDepartmentId());
				if(uDepartment != null){
					uDepartment.setDepartmentName(dpment.getDepartmentName());
					uDepartment.setLevel(dpment.getLevel());
					uDepartment.setStatus(dpment.getStatus());
					departmentBiz.updateDepartment(uDepartment);
					operateResult = new OperateResult(1, "部门信息修改成功！");
				}else{
					operateResult = new OperateResult(-1, "部门信息修改失败！");
				}
			}else{
				operateResult = new OperateResult(-1, "部门信息修改失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			operateResult = new OperateResult(-1, "部门信息修改失败！");
		}
		return OPER_RESULT;
	}
	
	

	public List<Department> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public DepartmentBiz getDepartmentBiz() {
		return departmentBiz;
	}
	public void setDepartmentBiz(DepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}
	
	public Department getDpment() {
		return dpment;
	}
	public void setDpment(Department dpment) {
		this.dpment = dpment;
	}

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

}
