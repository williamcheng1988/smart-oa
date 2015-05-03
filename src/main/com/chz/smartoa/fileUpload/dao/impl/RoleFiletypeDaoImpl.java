package com.chz.smartoa.fileUpload.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.fileUpload.dao.RoleFiletypeDao;
import com.chz.smartoa.fileUpload.pojo.RoleFiletype;

public class RoleFiletypeDaoImpl extends SqlMapClientDaoSupport implements RoleFiletypeDao{

	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListByRole(RoleFiletype roleFile) {
		List<String> list = (List<String>)getSqlMapClientTemplate().queryForList("rf_getListByRole", roleFile);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListByRoleId(RoleFiletype roleFile) {
		List<String> list = (List<String>)getSqlMapClientTemplate().queryForList("rf_getListByRoleId", roleFile);
		return list;
	}
	

	@Override
	public void insertRoleFile(RoleFiletype roleFile) {
		getSqlMapClientTemplate().insert("rf_insertRoleFile", roleFile); 
	}

	@Override
	public void batchDeleteByIsSee(RoleFiletype roleFile) {
		getSqlMapClientTemplate().delete("rf_batchDeleteByIsSee",roleFile);
	}

}
