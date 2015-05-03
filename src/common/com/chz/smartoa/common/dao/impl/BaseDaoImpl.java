package com.chz.smartoa.common.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.common.base.BaseDomain;
import com.chz.smartoa.common.dao.BaseDao;

/**
 * BaseDao接口实现 IMPL
 */
public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao{
	
	@Override
	public Object insert(String sqlId, Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insert, 输入参数[" + sqlId+","+obj + "]");
		}
    	Object object = getSqlMapClientTemplate().insert(sqlId, obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insert,返回:"+object);
		}
		return object;
	}
	@Override
	public int update(String sqlId, Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入update, 输入参数[" + sqlId+","+obj + "]");
		}
		int cnt = getSqlMapClientTemplate().update(sqlId, obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开update,返回:"+cnt);
		}
		return cnt;
	}
	@Override
	public int delete(String sqlId, Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入delete, 输入参数[" + sqlId+","+obj + "]");
		}
		int cnt = getSqlMapClientTemplate().delete(sqlId, obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开delete,返回:"+cnt);
		}
		return cnt;
	}
	@Override
	public Object queryForObject(String sqlId, Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入queryForObject, 输入参数[" + sqlId+","+obj + "]");
		}
    	Object object = getSqlMapClientTemplate().queryForObject(sqlId, obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开queryForObject,返回:"+object);
		}
		return object;
	}
	@Override
	public List<?> queryForList(String sqlId, Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入queryForList, 输入参数[" + sqlId+","+obj + "]");
		}
    	List<?> list = getSqlMapClientTemplate().queryForList(sqlId,obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开queryForList,返回:"+list);
		}
		return list;
	}
	
	@Override
	public List<?> queryForPageList(String sqlId, BaseDomain obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入queryForPageList, 输入参数[" + sqlId+","+obj + "]");
		}
    	List<?> list = getSqlMapClientTemplate().queryForList(sqlId,obj,obj.getStart(),obj.getLimit());
		if (logger.isDebugEnabled()) {
    		logger.debug("离开queryForPageList,返回:"+list);
		}
		return list;
	}
	@Override
	public Integer queryForPageCount(String sqlId,Object obj) throws SQLException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入queryForPageCount, 输入参数[" + sqlId+","+obj + "]");
		}
		int cnt = (Integer)getSqlMapClientTemplate().queryForObject(sqlId,obj);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开queryForPageCount,返回:"+cnt);
		}
		return cnt;
	}
	
	@Override
	public HashMap<?, ?> queryForObject(String sqlId, Map<?, ?> map,String str) {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入queryForObject, 输入参数[" + sqlId+","+str + "]");
		}
		HashMap<?, ?> result = (HashMap<?, ?>)getSqlMapClientTemplate().queryForObject(sqlId, map);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开queryForObject,返回:"+result);
		}
		return result;
	}
	
	@Override
	public HashMap<?, ?> queryForList(String sqlId, Object obj, String str) {
		List<?> list = getSqlMapClientTemplate().queryForList(sqlId, obj);
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnList",list);
		returnMap.put("returnMap",obj);
		return returnMap;
	}
	
}
