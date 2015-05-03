package com.chz.smartoa.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.dao.BaseDao;
import com.chz.smartoa.system.pojo.Role;


/**
 * RoleDao 接口
 * @author huangdhui
 *
 */
public interface RoleDao extends BaseDao{
    /**
     * insert.
     * @param role role
     * @return id
     * @throws DataAccessException DataAccessException
     */
    String insertRole(Role role, String[] operationIds) throws DataAccessException ;

    /**
     * delete.
     * @param id
     * @throws DataAccessException DataAccessException
     */
    void deleteRole(String id) throws DataAccessException ;
    
    /**
     * delete.
     * @param id
     * @throws DataAccessException DataAccessException
     */
    void deleteStaffRole(String staffId) throws DataAccessException ;
    
    /**
     * delete.
     * @param id
     * @throws DataAccessException DataAccessException
     */
    void deleteDepartmentRole(String departmentId) throws DataAccessException ;    
    
    /**
     * update.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    void updateRole(Role role, String[] operationIds) throws DataAccessException ;
    
    /**
     * update.
     * @param staffId staffId
     * @throws DataAccessException DataAccessException
     */
    void updateStaffRole(String staffId, String[] roleIds) throws DataAccessException ;
    
    /**
     * update.
     * @param departmentId departmentId
     * @throws DataAccessException DataAccessException
     */
    void updateDepartmentRole(String departmentId, String[] roleIds) throws DataAccessException ;    

    /**
     * find.
     * @param id id
     * @return role
     * @throws DataAccessException DataAccessException
     */
    Role findRole(String id) throws DataAccessException ;
    
    /**
     * find.
     * @param id id
     * @return role
     * @throws DataAccessException DataAccessException
     */
    Role findRoleByName(String name) throws DataAccessException ;    
    
    /**
     * list.
     * @param role role
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    List<Role> listRole(Role role) throws DataAccessException ;
    
    /**
     * listStaffRole.
     * @param staffId
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    List<Role> listStaffRole(String staffId) throws DataAccessException ;
    
    /**
     * listDepartmentRole.
     * @param staffId
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    List<Role> listDepartmentRole(String departmentId) throws DataAccessException ;
    
    /**
     * listCount.
     * @param role role
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    Integer listRoleCount(Role role) throws DataAccessException ;
    
    
}
