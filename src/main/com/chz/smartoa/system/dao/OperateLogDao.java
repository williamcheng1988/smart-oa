package com.chz.smartoa.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.pojo.OperateLog;

/**
 * OperateLogDao接口.
 * 
 * @author huangdhui
 * 
 */
public interface OperateLogDao {

	String insertOperateLog(OperateLog operateLog) throws DataAccessException;

	void deleteOperateLog(OperateLog operateLog) throws DataAccessException;

	void updateOperateLog(OperateLog operateLog) throws DataAccessException;

	OperateLog findOperateLog(String id) throws DataAccessException;

	List<OperateLog> listOperateLog(OperateLog operateLog) throws DataAccessException;

	Integer listOperateLogCount(OperateLog operateLog) throws DataAccessException;

	/**
	 * 登录日志
	 * 
	 * @param operateLog
	 * @return
	 * @throws DataAccessException
	 */
	List<OperateLog> listLoginLog(OperateLog operateLog) throws DataAccessException;

	/**
	 * 登录日志条数
	 * 
	 * @param operateLog
	 * @return
	 * @throws DataAccessException
	 */
	int listLoginLogCount(OperateLog operateLog) throws DataAccessException;
}
