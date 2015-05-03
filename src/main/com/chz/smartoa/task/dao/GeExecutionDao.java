package com.chz.smartoa.task.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.GeExecution;


/**
 * GeExecutionDao
 * @author wesson
 *
 */
public interface GeExecutionDao {
	/**
	 * 添加一个流程实例
	 * @param geExecution
	 * @return
	 * @throws DataAccessException
	 */
	String insertGeExecution(GeExecution geExecution) throws DataAccessException ;
	
	/**
	 * 查询下一个处理节点
	 * @param executionId
	 * @return
	 * @throws DataAccessException
	 */
	Integer getNextSortNum(String executionId) throws DataAccessException;
	
	/**
	 * 修改流程实例状态
	 * @param executionId
	 * @throws DataAccessException
	 */
	Integer updateGeExecution(String executionId) throws DataAccessException;
	
	/**
	 * 修改流程实例状态
	 * @param execution
	 * @throws DataAccessException
	 */
	Integer updateGeExecution(GeExecution execution) throws DataAccessException;
	
	/**
	 * 查询流程实例
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	GeExecution findGeExecution(String id) throws DataAccessException ;
	
	/**
	 * 查询流程实例
	 * @param executionId
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> findTaskGeExecution(String executionId) throws DataAccessException ;

}
