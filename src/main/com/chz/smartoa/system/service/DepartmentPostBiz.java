package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.pojo.DepartmentPostStaffs;

public interface DepartmentPostBiz {

	public void insertDepartmentPost(DepartmentPostStaffs dps);
	public List<DepartmentPostStaffs> findListByDepartmentId(String departmentId);
}
