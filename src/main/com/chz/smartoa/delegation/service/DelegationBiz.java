package com.chz.smartoa.delegation.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.delegation.pojo.Delegation;
import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.delegation.pojo.DelegationLogVo;


/**
 * 授权管理
 * @author wesson
 */
public interface DelegationBiz{
	/**
	 * 新增授权
	 * @param delegation
	 * @throws DataAccessException
	 */
	void insertDelegation(Delegation delegation) throws DataAccessException;
	/**
	 * 删除授权
	 * @param delegationId
	 * @throws DataAccessException
	 */
	void deleteDelegation(String delegationId) throws DataAccessException,ParseException;
	/**
	 * 修改授权
	 * @param delegation
	 * @throws DataAccessException
	 */
	void updateDelegation(Delegation delegation) throws DataAccessException;
	/**
	 * 查找授权
	 * @param delegationId
	 * @throws DataAccessException
	 */
	Delegation findDelegation(String delegationId) throws DataAccessException;
	/**
	 * 是否已授权
	 * @param delegationId
	 * @throws DataAccessException
	 */
	Delegation isExists(Delegation delegation) throws DataAccessException;
	/**
	 * 授权列表
	 * @param delegation
	 * @return
	 * @throws DataAccessException
	 */
	List<Delegation> listDelegation(Delegation delegation) throws DataAccessException;
	/**
	 * 授权条数
	 * @param delegation
	 * @return
	 * @throws DataAccessException
	 */
	Integer listDelegationCount(Delegation delegation) throws DataAccessException;
	/**
	 * 记录授权日志
	 * @param delegationLog
	 * @throws DataAccessException
	 */
	void insertDelegationLog(DelegationLog delegationLog) throws DataAccessException;
	/**
	 * 授权日志列表
	 * @param delegationLog
	 * @return
	 * @throws DataAccessException
	 */
	List<DelegationLogVo> listDelegationLog(DelegationLog delegationLog) throws DataAccessException;
	/**
	 * 授权日志条数
	 * @param delegation
	 * @return
	 * @throws DataAccessException
	 */
	Integer listDelegationLogCount(DelegationLog delegationLog) throws DataAccessException;
	
	/**
	 * 授权待办任务列表
	 * @param delegationLog
	 * @return
	 * @throws DataAccessException
	 */
	List<DelegationLogVo> listDelegationUndoLog(DelegationLog delegationLog) throws DataAccessException;
	/**
	 * 授权待办任务条数
	 * @param delegation
	 * @return
	 * @throws DataAccessException
	 */
	Integer listDelegationUndoLogCount(DelegationLog delegationLog) throws DataAccessException;
}
