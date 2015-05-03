package com.chz.smartoa.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.pojo.Department;


/**
 * DepartmentDao接口.
 * @author huangdhui
 *
 */
public interface DepartmentDao {
    /**
     * insert.
     * @param department department
     * @return id
     * @throws DataAccessException DataAccessException
     */
    String insertDepartment(Department department) throws DataAccessException ;

    /**
     * delete.
     * @param department department
     * @throws DataAccessException DataAccessException
     */
    void deleteDepartment(String id) throws DataAccessException ;
    
    /**
     * update.
     * @param department department
     * @throws DataAccessException DataAccessException
     */
    void updateDepartment(Department department) throws DataAccessException ;

    /**
     * find.
     * @param id id
     * @return department
     * @throws DataAccessException DataAccessException
     */
    Department findDepartment(String id) throws DataAccessException ;
    
    /**
     * find.
     * @param id id
     * @return department
     * @throws DataAccessException DataAccessException
     */
    Department findDepartmentByName(String name) throws DataAccessException ;   
    
    /**
     * assignRole.
     * @param id 部门id
     * @param roleIds 角色IDS
     * @throws DataAccessException DataAccessException
     */
    void assignRole(String id, String[] roleIds) throws DataAccessException ;       
    
    /**
     * list.
     * @param department department
     * @return department list
     * @throws DataAccessException DataAccessException
     */
    List listDepartment(Department department) throws DataAccessException ;
    
    /**
     * list.
     * @param department department
     * @return department list
     * @throws DataAccessException DataAccessException
     */
    List listRecursiveDepartment(Department department) throws DataAccessException ;    
    
    /**
     * listCount.
     * @param department department
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    Integer listDepartmentCount(Department department) throws DataAccessException ;
}
