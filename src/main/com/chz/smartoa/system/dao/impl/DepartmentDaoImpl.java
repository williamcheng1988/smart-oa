package com.chz.smartoa.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.pojo.Department;

/**
 * DepartmentDao接口实现类.
 * @authorLogHelper
 *
 */
public class DepartmentDaoImpl extends SqlMapClientDaoSupport implements DepartmentDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(DepartmentDaoImpl.class);

    /**
     * 得到sequence.
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String getNextId() throws DataAccessException {
    	return (String) getSqlMapClientTemplate().queryForObject("Department_getNextId");
    }
    
    /**
     * insert.
     * @param department department
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String insertDepartment(Department department) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertDepartment(Department), 输入参数[" + department + "]");
    	}
        
        // 得到sequence
    	String id = getNextId();
    	department.setDepartmentId(id);
        
    	getSqlMapClientTemplate().insert("Department_insertDepartment", department);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertDepartment(Department), 返回[" + id + "]");
		}
    	return id;
    }

    /**
     * delete.
     * @param department department
     * @throws DataAccessException DataAccessException
     */
    public void deleteDepartment(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteDepartment(String), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Department_deleteDepartment", id);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteDepartment(String)");
		}
    }
    
    /**
     * update.
     * @param department department
     * @throws DataAccessException DataAccessException
     */
    public void updateDepartment(Department department) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateDepartment(Department), 输入参数[" + department + "]");
		}
    	getSqlMapClientTemplate().update("Department_updateDepartment", department);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateDepartment(Department)");
		}
    }
    
    /**
     * find.
     * @param id id
     * @return department
     * @throws DataAccessException DataAccessException
     */
    public Department findDepartment(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findDepartment(Department), 输入参数[" + pk + "]");
		}
        Department department = (Department) getSqlMapClientTemplate().queryForObject("Department_findDepartment", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findDepartment(Department), 返回[" + department + "]");
		}
        return department;
    }
    
    /**
     * find.
     * @param id id
     * @return department
     * @throws DataAccessException DataAccessException
     */
    public Department findDepartmentByName(String name) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findDepartmentByName(String), 输入参数[" + name + "]");
		}
        Department department = (Department) getSqlMapClientTemplate().queryForObject("Department_findDepartmentByName", name);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findDepartmentByName(String), 返回[" + department + "]");
		}
        return department;
    }    
    
    /**
     * list.
     * @param department department
     * @return department list
     * @throws DataAccessException DataAccessException
     */
    public List listDepartment(Department department) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDepartment(Department), 输入参数[" + department + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Department_listDepartment", department, 
        		department.getStart(), department.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDepartment(Department), 返回[" + list + "]");
		}
        return list;
    }  
    
    
    /**
     * list.
     * @param department department
     * @return department list
     * @throws DataAccessException DataAccessException
     */
    public List listRecursiveDepartment(Department department) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listRecursiveDepartment(Department), 输入参数[" + department + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Department_listRecursiveDepartment", department);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRecursiveDepartment(Department), 返回[" + list + "]");
		}
        return list;    	
    }
    
    /**
     * listCount.
     * @param department department
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listDepartmentCount(Department department) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDepartmentCount(Department), 输入参数[" + department + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Department_listDepartmentCount", department);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDepartmentCount(Department), 返回[" + count + "]");
		}
        return count;
    }
    
    /**
     * assignRole.
     * @param id 部门id
     * @param roleIds 角色IDS
     * @throws DataAccessException DataAccessException
     */
    public void assignRole(String id, String[] roleIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入assignRole(String, String[]), 输入参数[" + id + "," + roleIds + "]");
		}
        // 先删除旧的角色
		getSqlMapClientTemplate().delete("Department_deleteDepartmentRole", id);
		
		// 再增加新的角色
		Map paramMap = new HashMap();
		for (int i = 0; i < roleIds.length ; i++) {
			paramMap.put("departmentId", id);
			paramMap.put("roleId", roleIds[i]);
			getSqlMapClientTemplate().insert("Department_insertDepartmentRole", paramMap);
		}
		
		if (logger.isDebugEnabled()) {
        	logger.debug("离开assignRole(String, String[])");
		}
    }    
}
