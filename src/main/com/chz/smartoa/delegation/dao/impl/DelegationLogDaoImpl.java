package com.chz.smartoa.delegation.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.delegation.dao.DelegationLogDao;
import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.delegation.pojo.DelegationLogVo;

/**
 * DelegationLogDao
 * @author wesson
 */
public class DelegationLogDaoImpl extends BaseDaoiBatis implements DelegationLogDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(DelegationLogDaoImpl.class);

    public String insertDelegationLog(DelegationLog delegationLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertDelegationLog(DelegationLog), 输入参数[" + delegationLog + "]");
    	}
        
    	getSqlMapClientTemplate().insert("DelegationLog_insertDelegationLog", delegationLog);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertDelegationLog(DelegationLog)");
		}
    	return null;
    }

    public List<DelegationLogVo> listDelegationLog(DelegationLog delegationLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegationLog(DelegationLog), 输入参数[" + delegationLog + "]");
		}
        List<DelegationLogVo> list = getSqlMapClientTemplate().queryForList("DelegationLog_dealListDelegationLog", delegationLog.getDelegationId(),delegationLog.getStart(),delegationLog.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegationLog(DelegationLog), 返回[" + list + "]");
		}
        return list;
    }  
    
    public Integer listDelegationLogCount(DelegationLog delegationLog) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegationLogCount(DelegationLog), 输入参数[" + delegationLog + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("DelegationLog_dealListDelegationLogCount", delegationLog.getDelegationId());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegationLogCount(DelegationLog), 返回[" + count + "]");
		}
        return count;
    }  
    
    @Override
    public List<DelegationLogVo> listDelegationUndoLog(
    		DelegationLog delegationLog) throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegationUndoLog(DelegationLog), 输入参数[" + delegationLog + "]");
		}
        List<DelegationLogVo> list = getSqlMapClientTemplate().queryForList("DelegationLog_undoListDelegationLog", delegationLog.getDelegationId(),delegationLog.getStart(),delegationLog.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegationUndoLog(DelegationLog), 返回[" + list + "]");
		}
        return list;
    }
    
    @Override
    public Integer listDelegationUndoLogCount(DelegationLog delegationLog)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegationUndoLogCount(DelegationLog), 输入参数[" + delegationLog + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("DelegationLog_undoListDelegationLogCount", delegationLog.getDelegationId());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegationUndoLogCount(DelegationLog), 返回[" + count + "]");
		}
        return count;
    }
}
