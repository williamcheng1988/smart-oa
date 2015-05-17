package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.system.dao.DepartmentPostDao;
import com.chz.smartoa.system.pojo.DepartmentPostStaffs;
import com.chz.smartoa.system.service.DepartmentPostBiz;


public class DepartmentPostBizImpl implements DepartmentPostBiz{
	
	private DepartmentPostDao departmentPostDao;
	public DepartmentPostDao getDepartmentPostDao() {
		return departmentPostDao;
	}
	public void setDepartmentPostDao(DepartmentPostDao departmentPostDao) {
		this.departmentPostDao = departmentPostDao;
	}

	
	@Override
	public void insertDepartmentPost(DepartmentPostStaffs dps) {
		departmentPostDao.insertDepartmentPost(dps);
	}

	@Override
	public List<DepartmentPostStaffs> findListByDepartmentId(String departmentId) {
		return departmentPostDao.findListByDepartmentId(departmentId);
	}
	
	@Override
	public DepartmentPostStaffs getDepartmentPostById(Long id) {
		return departmentPostDao.getDepartmentPostById(id);
	}
	
	@Override
	public DepartmentPostStaffs getDepartmentPostByDepIdAndPostId(String departmentId, String postId) {
		return departmentPostDao.getDepartmentPostByDepIdAndPostId(departmentId, postId);
	}
	
	@Override
	public void updateDepartmentPost(DepartmentPostStaffs dps) {
		departmentPostDao.updateDepartmentPost(dps);
	}

}
