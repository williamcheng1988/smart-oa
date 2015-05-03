package com.chz.smartoa.system.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.OperateLog;
import com.chz.smartoa.system.service.impl.OperateLogContext;

public interface OperateLogBiz {
	/**
	 * 记录操作日志.
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 */
	void info(OperateLogType logType, String entityId, String entityName, String operateResult);
	
	/**
	 * 记录操作日志.
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 * @param otherInfoMap 其它信息 
	 */
	void info(OperateLogType logType, String entityId, String entityName, String operateResult,String description);
	
	/**
	 * 记录操作日志.
	 * @param operateLogContext 日志环境 
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 * @param otherInfoMap 其它信息 
	 */
	void info(OperateLogContext context, OperateLogType logType, String entityId, String entityName, 
			String operateResult,String description);	
	/**
	 * 操作日志
	 * @param operateLog
	 * @return
	 * @throws DataAccessException
	 */
    List<OperateLog> listOperateLog(OperateLog operateLog) throws DataAccessException ;	
    /**
     * 操作日志条数
     * @param operateLog
     * @return
     * @throws DataAccessException
     */
    int listOperateLogCount(OperateLog operateLog) throws DataAccessException ;    
    
    /**
	 * 登录日志
	 * @param operateLog
	 * @return
	 * @throws DataAccessException
	 */
    List<OperateLog> listLoginLog(OperateLog operateLog) throws DataAccessException ;	
    /**
     * 登录日志条数
     * @param operateLog
     * @return
     * @throws DataAccessException
     */
    int listLoginLogCount(OperateLog operateLog) throws DataAccessException ;    
}
