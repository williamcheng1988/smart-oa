package com.chz.smartoa.system.dao;

import java.util.List;

import com.chz.smartoa.system.pojo.DepartmentPostStaffs;

public interface DepartmentPostDao {
	
	public void insertDepartmentPost(DepartmentPostStaffs dps);
	
	public List<DepartmentPostStaffs> findListByDepartmentId(String departmentId);
}
