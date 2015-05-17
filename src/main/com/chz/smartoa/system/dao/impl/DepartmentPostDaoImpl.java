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

}
