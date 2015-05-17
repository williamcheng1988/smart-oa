package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.service.DepartmentBiz;


public class DepartmentBizImpl implements DepartmentBiz {

	private DepartmentDao departmentDao;
	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}


	@Override
	public Department findDepartment(String departmentId) {
		return departmentDao.findDepartment(departmentId);
	}
	
	@Override
	public List<Department> getDepartmentByParentId(String perentId) {
		return departmentDao.getDepartmentByParentId(perentId);
	}
	
	@Override
	public List<TreeData> getDepartmentTreeByParentId(String perentId) {
		return departmentDao.getDepartmentTreeByParentId(perentId);
	}
	
	@Override
	public void insertDepartment(Department department) {
		departmentDao.insertDepartment(department);
	}
	@Override
	public void updateDepartment(Department department) {
		departmentDao.updateDepartment(department);
	}
}
