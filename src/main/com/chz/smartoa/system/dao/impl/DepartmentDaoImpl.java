package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.pojo.Department;



/**
 * DepartmentDao接口实现类.
 * @authorLogHelper
 *
 */
public class DepartmentDaoImpl extends SqlMapClientDaoSupport implements DepartmentDao{

	@Override
	public Department findDepartment(String departmentId) {
		Department department = (Department)getSqlMapClientTemplate().queryForObject("dpt_departmentById", departmentId);
		return department;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDepartmentByParentId(String perentId) {
		List<Department> dList = (List<Department>)getSqlMapClientTemplate().queryForList("dpt_departmentByParentId",perentId);
		return dList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TreeData> getDepartmentTreeByParentId(String perentId) {
		List<TreeData> treeList = (List<TreeData>)getSqlMapClientTemplate().queryForList("dpt_departmentTreeByParentId",perentId);
		return treeList;
	}


	@Override
	public void insertDepartment(Department department) {
		getSqlMapClientTemplate().insert("dpt_insertDpt", department);
	}


	@Override
	public void updateDepartment(Department department) {
		getSqlMapClientTemplate().update("dpt_updateDpt", department);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getAllDepartment() {
		List<Department> dList = (List<Department>)getSqlMapClientTemplate().queryForList("dpt_allDepartment");
		return dList;
	}

}
