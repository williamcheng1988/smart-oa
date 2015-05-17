package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.DepartmentPostDao;
import com.chz.smartoa.system.pojo.DepartmentPostStaffs;


public class DepartmentPostDaoImpl extends SqlMapClientDaoSupport implements DepartmentPostDao{

	@Override
	public void insertDepartmentPost(DepartmentPostStaffs dps) {
		getSqlMapClientTemplate().insert("dps_insertDepartmentPost", dps);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentPostStaffs> findListByDepartmentId(String departmentId) {
		List<DepartmentPostStaffs> dpsList = (List<DepartmentPostStaffs>)getSqlMapClientTemplate().queryForList("dps_findListByDepartmentId",departmentId);
		return dpsList;
	}


	@Override
	public DepartmentPostStaffs getDepartmentPostById(Long id) {
		DepartmentPostStaffs dps = (DepartmentPostStaffs)getSqlMapClientTemplate().queryForObject("dps_departmentPostById", id);
		return dps;
	}


	@Override
	public DepartmentPostStaffs getDepartmentPostByDepIdAndPostId(String departmentId, String postId) {
		DepartmentPostStaffs param = new DepartmentPostStaffs();
		param.setDepartmentId(departmentId);
		param.setPostId(postId);
		DepartmentPostStaffs dps = (DepartmentPostStaffs)getSqlMapClientTemplate().queryForObject("dps_departmentPostByDepIdAndPostId", param);
		return dps;
	}


	@Override
	public void updateDepartmentPost(DepartmentPostStaffs dps) {
		getSqlMapClientTemplate().update("dps_updateDepartmentPost", dps);
	}

}
