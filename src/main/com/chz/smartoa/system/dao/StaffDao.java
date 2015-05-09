package com.chz.smartoa.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.pojo.StaffRole;


/**
 * StaffDao接口
 * @author huangdhui
 */
public interface StaffDao {
    /**
     * insert.
     * @param staff staff
     * @return id
     * @throws DataAccessException DataAccessException
     */
    String insertStaff(Staff staff) throws DataAccessException ;
    
    /**
     * 新增项目角色对应关系
     * @param staffRole
     * @return
     * @throws DataAccessException
     */
    void insertStaffRole(StaffRole staffRole) throws DataAccessException ;
    
    /**
     * @param staffId
     * @param roleIds
     * @throws DataAccessException
     */
    String insertStaffRoles(String staffId,String[] roleIds) throws DataAccessException ;

    /**
     * delete.
     * @param staff staff
     * @throws DataAccessException DataAccessException
     */
    void deleteStaff(String loginName) throws DataAccessException ;
    
    /**
     * update.
     * @param staff staff
     * @throws DataAccessException DataAccessException
     */
    void updateStaff(Staff staff) throws DataAccessException ;

    /**
     * find.
     * @param id id
     * @return staff
     * @throws DataAccessException DataAccessException
     */
    Staff findStaffByLoginName(String loginName) throws DataAccessException ;    
    
    /**
     * list.
     * @param staff staff
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    List<Staff> listStaff(Staff staff) throws DataAccessException ;
    /**
     * list.
     * @param staff staff
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    List listAllStaffs(Staff staff) throws DataAccessException;
    
    /**
     * list.
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    List<Staff> listStaff(String loginName) throws DataAccessException ;
    
    /**
     * list.
     * @param resourceKey 
     * @param operationKey 
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    List<Staff> listStaffByRes(String resourceKey, String operationKey) throws DataAccessException ;  
    
    /**
     * list.
     * @param loginName 
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    List<String> listOperationUri(String loginName) throws DataAccessException ;  
    
    /**
     * 
     * @param loginName
     * @return
     * @throws DataAccessException
     */
    List<String> listResourceIds(String loginName) throws DataAccessException;
    
    /**
     * listCount.
     * @param staff staff
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    Integer listStaffCount(Staff staff) throws DataAccessException ;
    
    /**
     * assignRole.
     * @param id ����id
     * @param roleIds ��ɫIDS
     * @throws DataAccessException DataAccessException
     */
    void assignRole(String loginName, String[] roleIds) throws DataAccessException ;  
    
    /**
     * 查询角色对应用户
     * @param Rol
     * @throws DataAccessException
     */
    List<String> listStaffByRole(StaffRole staffRole) throws DataAccessException;
    
    /**
     * 删除项目对应用户角色关联关系
     * @param projectId
     * @return
     * @throws DataAccessException
     */
    Integer deleteStaffRoleByProjectId(String projectId) throws DataAccessException;
}
