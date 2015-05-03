package com.chz.smartoa.delegation.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.delegation.dao.DelegationDao;
import com.chz.smartoa.delegation.pojo.Delegation;

/**
 * DelegationDao
 * @author wesson
 */
public class DelegationDaoImpl extends BaseDaoiBatis implements DelegationDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(DelegationDaoImpl.class);

    public String insertDelegation(Delegation delegation) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertDelegation(Delegation), 输入参数[" + delegation + "]");
    	}
        
		//生成ID
		String id  = String.valueOf(UUID.randomUUID());
		delegation.setDelegationId(id);
        
    	getSqlMapClientTemplate().insert("Delegation_insertDelegation", delegation);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertDelegation(Delegation), 返回[" + id + "]");
		}
    	return id;
    }

    public void deleteDelegation(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteDelegation(String id), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Delegation_deleteDelegation", id);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteDelegation(String id)");
		}
    }

    public void updateDelegation(Delegation delegation) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateDelegation(Delegation),输入参数[" + delegation + "]");
		}
    	getSqlMapClientTemplate().update("Delegation_updateDelegation", delegation);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateDelegation(Delegation)");
		}
    }
    
    public Delegation findDelegation(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findDelegation(Delegation), 输入参数[" + pk + "]");
		}
        Delegation delegation = (Delegation) getSqlMapClientTemplate().queryForObject("Delegation_findDelegation", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findDelegation(Delegation), 返回[" + delegation + "]");
		}
        return delegation;
    }
    
    public List<Delegation> listDelegation(Delegation delegation) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegation(Delegation), 输入参数[" + delegation + "]");
		}
        List<Delegation> list = getSqlMapClientTemplate().queryForList("Delegation_listDelegation", delegation,delegation.getStart(),delegation.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegation(Delegation), 返回[" + list + "]");
		}
        return list;
    }  

    
    public Integer listDelegationCount(Delegation delegation) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDelegationCount(Delegation), 输入参数[" + delegation + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Delegation_listDelegationCount", delegation);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDelegationCount(Delegation), 返回[" + count + "]");
		}
        return count;
    }  
    
    @Override
    public String findDelegationToUser(String fromUser)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入findDelegationToUser(String fromUser), 输入参数[" + fromUser + "]");
		}
    	String toUser = (String)getSqlMapClientTemplate().queryForObject("Delegation_findDelegationTouser", fromUser);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findDelegationToUser(String fromUser), 返回[" + toUser + "]");
		}
    	return toUser;
    }
    
    @Override
    public Delegation findDelegationByFromuser(String fromUser)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入findDelegationToUser(String fromUser), 输入参数[" + fromUser + "]");
		}
    	Delegation delegation = (Delegation)getSqlMapClientTemplate().queryForObject("Delegation_findDelegationByFromUser", fromUser);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findDelegationToUser(String fromUser), 返回[" + delegation + "]");
		}
    	return delegation;
    }
}
