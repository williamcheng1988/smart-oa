package com.chz.smartoa.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * BaseDao基类
 */
public interface BaseDao {
	/**
	 * 新增记录
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 */
	public Object insert(String sqlId, Object obj) throws SQLException ;

	/**
	 * 修改记录
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 */
	public int update(String sqlId, Object obj)throws SQLException ;
	

	/**
	 * 删除记录
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 */
	public int delete(String sqlId, Object obj)throws SQLException ;
	
	/**
	 * 查询对象
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 * @return
	 */
	public Object queryForObject(String sqlId, Object obj)throws SQLException ;
	

	/**
	 * 查询集合List
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 * @return
	 */
	public List<?> queryForList(String sqlId, Object obj)throws SQLException ;
	
	/**
	 * 分布查询：查询集合List
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 * @return
	 */
    public List<?> queryForPageList(String sqlId, BaseDomain obj) throws SQLException;
   
    /**
	 * 分布查询：查询 总数
	 * @param sqlId ibatis中对应的sqlId
	 * @param obj 参数对象
	 * @return
	 */
    public Integer queryForPageCount(String sqlId,Object obj) throws SQLException;
    
    /**
	 * 专注与存储过程的调用
	 * @param sqlId
	 * @param map
	 * @param str
	 * @return
	 */
	public HashMap<?, ?> queryForObject(String sqlId, Map<?, ?> map,String str)throws SQLException ;
	public HashMap<?, ?> queryForList(String sqlId, Object obj,String str)throws SQLException ;
}
