package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.system.pojo.Department;


public interface DepartmentBiz {
	
	public Department findDepartment(String departmentId);
	
	public List<Department> getDepartmentByParentId(String perentId);
	
	public List<TreeData> getDepartmentTreeByParentId(String perentId);
	
	public void insertDepartment(Department department);
	
	public void updateDepartment(Department department);
	
	public List<Department> getAllDepartment();
}
