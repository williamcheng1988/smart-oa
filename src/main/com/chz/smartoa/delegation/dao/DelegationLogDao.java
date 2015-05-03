package com.chz.smartoa.delegation.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.delegation.pojo.DelegationLogVo;


/**
 * DelegationLogDao
 * @author wesson
 */
public interface DelegationLogDao {
    String insertDelegationLog(DelegationLog delegationLog) throws DataAccessException ;
    List<DelegationLogVo> listDelegationLog(DelegationLog delegationLog) throws DataAccessException ;
    Integer listDelegationLogCount(DelegationLog delegationLog) throws DataAccessException ;
    
    List<DelegationLogVo> listDelegationUndoLog(DelegationLog delegationLog) throws DataAccessException ;
    Integer listDelegationUndoLogCount(DelegationLog delegationLog) throws DataAccessException ;
}
