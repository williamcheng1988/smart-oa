package com.chz.smartoa.delegation.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.chz.smartoa.delegation.pojo.Delegation;


/**
 * DelegationDao
 * @author wesson
 *
 */
public interface DelegationDao {
    String insertDelegation(Delegation delegation) throws DataAccessException ;

    void deleteDelegation(String id) throws DataAccessException ;
    
    void updateDelegation(Delegation delegation) throws DataAccessException ;

    Delegation findDelegation(String id) throws DataAccessException ;
    
    List<Delegation> listDelegation(Delegation delegation) throws DataAccessException ;

    Integer listDelegationCount(Delegation delegation) throws DataAccessException ;
    /**
     * 查询被委托人
     * @param fromUser 委托人
     * @return 被委托人
     * @throws DataAccessException
     */
    String findDelegationToUser(String fromUser) throws DataAccessException;
    
    /**
     * 查询委托
     * @param fromUser 委托人
     * @return 委托
     * @throws DataAccessException
     */
    Delegation findDelegationByFromuser(String fromUser) throws DataAccessException ;
}
