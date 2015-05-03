package com.chz.smartoa.task.dao.impl;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.GeExecutionDao;
import com.chz.smartoa.task.pojo.GeExecution;

/**
 * GeExecutionDao接口实现类.
 * @author wesson
 *
 */
public class GeExecutionDaoImpl extends BaseDaoiBatis implements GeExecutionDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(GeExecutionDaoImpl.class);

    public String insertGeExecution(GeExecution geExecution) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertGeExecution(GeExecution), 输入参数[" + geExecution + "]");
    	}
        String id = String.valueOf(UUID.randomUUID());
        geExecution.setExecutionId(id);
    	getSqlMapClientTemplate().insert("GeExecution_insertGeExecution", geExecution);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertGeExecution(GeExecution), 返回[" + id + "]");
		}
    	return id;
    }
    
    @Override
	public Integer getNextSortNum(String executionId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getNextSortNum(String executionId), 输入参数[" + executionId + "]");
		}
		Integer sortNum = null;
		Object obj = getSqlMapClientTemplate().queryForObject("GeExecution_getNextSortNum",executionId);
		if(obj != null)
			sortNum = (Integer)obj;
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getNextSortNum(String executionId),返回[" + sortNum + "]");
		} 
		return sortNum;
	}

    @Override
    public Integer updateGeExecution(GeExecution geExecution) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateGeExecution(GeExecution), 输入参数[" + geExecution + "]");
		}
		Integer cnt =  getSqlMapClientTemplate().update("GeExecution_updateGeExecution", geExecution);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateGeExecution(GeExecution)");
		}
		return cnt;
    }
    
    @Override
    public Integer updateGeExecution(String executionId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入updateGeExecution(String executionId), 输入参数[" + executionId + "]");
		}
    	Integer cnt =  getSqlMapClientTemplate().update("GeExecution_resetGeExecution", executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateGeExecution(String executionId)");
		}
		return cnt;
    }
    
    public GeExecution findGeExecution(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findGeExecution(pk), 输入参数[" + pk + "]");
		}
        GeExecution geExecution = (GeExecution) getSqlMapClientTemplate().queryForObject("GeExecution_findGeExecution", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findGeExecution(pk), 返回[" + geExecution + "]");
		}
        return geExecution;
    }
    
    @Override
    public Map<String, Object> findTaskGeExecution(String executionId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入findTaskGeExecution(executionId), 输入参数[" + executionId + "]");
		}
    	Map<String, Object> map = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("GeExecution_findTaskGeExecution", executionId);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findTaskGeExecution(executionId), 返回[" + map + "]");
		}
        return map;
    }
}
