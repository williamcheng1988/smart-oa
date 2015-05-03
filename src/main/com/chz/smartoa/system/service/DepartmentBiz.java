package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.exception.DepartmentHasChildException;
import com.chz.smartoa.system.exception.DepartmentHasStaffException;
import com.chz.smartoa.system.exception.DepartmentNotFoundException;
import com.chz.smartoa.system.pojo.Department;

public interface DepartmentBiz {
	Department findDepartment(String id);
	
	List listDepartment(Department department);
	
	List listRecursiveDepartment(Department department);	
	
	int listDepartmentCount(Department department);	
	
	Department findDepartmentByName(String name);
	
	String insertDepartment(Department department);

	void updateDepartment(Department department);

	void deleteDepartment(String id) 
		throws DepartmentHasChildException, 
			   DepartmentHasStaffException, DepartmentNotFoundException;
	
	void assignRole(String id, String[] roleIds);
}
