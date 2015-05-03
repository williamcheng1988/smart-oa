package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.system.dao.OperateLogDao;
import com.chz.smartoa.system.pojo.OperateLog;

/**
 * OperateLogDao接口实现类.
 * @author huangdhui
 *
 */
public class OperateLogDaoImpl extends BaseDaoiBatis implements OperateLogDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(OperateLogDaoImpl.class);

      
    /**
     * insert.
     * @param operateLog operateLog
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String insertOperateLog(OperateLog operateLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertOperateLog(OperateLog), 输入参数[" + operateLog + "]");
    	}
        
    	getSqlMapClientTemplate().insert("OperateLog_insertOperateLog", operateLog);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertOperateLog(OperateLog), 返回[" + null + "]");
		}
    	return null;
    }

    /**
     * delete.
     * @param operateLog operateLog
     * @throws DataAccessException DataAccessException
     */
    public void deleteOperateLog(OperateLog operateLog)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteOperateLog(OperateLog), 输入参数[" + operateLog + "]");
		}
        getSqlMapClientTemplate().update("OperateLog_deleteOperateLog", operateLog);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteOperateLog(OperateLog)");
		}
    }
    
    /**
     * update.
     * @param operateLog operateLog
     * @throws DataAccessException DataAccessException
     */
    public void updateOperateLog(OperateLog operateLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateOperateLog(OperateLog), 输入参数[" + operateLog + "]");
		}
    	getSqlMapClientTemplate().update("OperateLog_updateOperateLog", operateLog);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateOperateLog(OperateLog)");
		}
    }
    
    /**
     * find.
     * @param id id
     * @return operateLog
     * @throws DataAccessException DataAccessException
     */
    public OperateLog findOperateLog(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findOperateLog(OperateLog), 输入参数[" + pk + "]");
		}
        OperateLog operateLog = (OperateLog) getSqlMapClientTemplate().queryForObject("OperateLog_findOperateLog", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findOperateLog(OperateLog), 返回[" + operateLog + "]");
		}
        return operateLog;
    }
    
    /**
     * list.
     * @param operateLog operateLog
     * @return operateLog list
     * @throws DataAccessException DataAccessException
     */
    public List<OperateLog> listOperateLog(OperateLog operateLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listOperateLog(OperateLog), 输入参数[" + operateLog + "]");
		}
      List<OperateLog> list = getSqlMapClientTemplate().queryForList("OperateLog_listOperateLog", operateLog, 
    		  operateLog.getStart(), operateLog.getLimit());		
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listOperateLog(OperateLog), 返回[" + list + "]");
		}
        return list;
    }  
    
    /**
     * listCount.
     * @param operateLog operateLog
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listOperateLogCount(OperateLog operateLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listOperateLogCount(OperateLog), 输入参数[" + operateLog + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("OperateLog_listOperateLogCount", operateLog);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listOperateLogCount(OperateLog), 返回[" + count + "]");
		}
        return count;
    }  
    
    @Override
    public List<OperateLog> listLoginLog(OperateLog operateLog)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listLoginLog(OperateLog), 输入参数[" + operateLog + "]");
		}
      List<OperateLog> list = getSqlMapClientTemplate().queryForList("OperateLog_listLoginLog", operateLog,operateLog.getStart(), operateLog.getLimit());		
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listLoginLog(OperateLog), 返回[" + list + "]");
		}
        return list;
    }
    
    @Override
    public int listLoginLogCount(OperateLog operateLog)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listLoginLogCount(OperateLog), 输入参数[" + operateLog + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("OperateLog_listLoginLogCount", operateLog);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listLoginLogCount(OperateLog), 返回[" + count + "]");
		}
        return count;
    }
    
}
