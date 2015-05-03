package com.chz.smartoa.common.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chz.smartoa.common.base.BaseDomain;
import com.chz.smartoa.common.dao.BaseDao;
import com.chz.smartoa.common.service.BaseService;

public class BaseServiceImpl implements BaseService {
	
	private BaseDao baseDao;

	@Override
	public Object insert(String sqlId, Object obj) throws SQLException {
		return baseDao.insert(sqlId, obj);
	}

	@Override
	public Object betchInsert(String sqlId, List<?> list) throws SQLException {
		int cnt = 0;
		if(list != null){
			for (Object obj : list) {
				baseDao.insert(sqlId, obj);
				cnt ++;
			}
		}
		return cnt;
	}

	@Override
	public int update(String sqlId, Object obj) throws SQLException {
		return baseDao.update(sqlId, obj);
	}

	@Override
	public int delete(String sqlId, Object obj) throws SQLException {
		return baseDao.delete(sqlId, obj);
	}

	@Override
	public Object queryForObject(String sqlId, Object obj) throws SQLException {
		return baseDao.queryForObject(sqlId, obj);
	}

	@Override
	public List<?> queryForList(String sqlId, Object obj) throws SQLException {
		return baseDao.queryForList(sqlId, obj);
	}

	@Override
	public List<?> queryForPageList(String sqlId, BaseDomain obj)
			throws SQLException {
		return baseDao.queryForPageList(sqlId, obj);
	}

	@Override
	public Integer queryForPageCount(String sqlId, Object obj)
			throws SQLException {
		return baseDao.queryForPageCount(sqlId, obj);
	}

	@Override
	public HashMap<?, ?> queryForObject(String sqlId, Map<?, ?> map, String str)
			throws SQLException {
		return baseDao.queryForObject(sqlId, map, str);
	}
	@Override
	public HashMap<?, ?> queryForList(String sqlId, Object obj, String str)
			throws SQLException {
		return baseDao.queryForList(sqlId, obj, str);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
