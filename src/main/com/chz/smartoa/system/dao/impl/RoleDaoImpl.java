package com.chz.smartoa.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.common.dao.impl.BaseDaoImpl;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;

/**
 * RoleDao接口实现类.
 * @author huangdhui
 *
 */
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);

    /**
     * 得到sequence.
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String getId(String roleName) throws DataAccessException {
    	return (String) getSqlMapClientTemplate().queryForObject("Role_getIdByName",roleName);
    }
    
    /**
     * insert.
     * @param role role
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String insertRole(Role role, String[] operationIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertRole(Role), 输入参数[" + role + "]");
    	}
        
        getSqlMapClientTemplate().insert("Role_insertRole", role);
        String id = getId(role.getRoleName());
    	
    	// 增加角色关联的资源操作
    	insertRoleOperation(id, operationIds);

    	
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertRole(Role), 返回[" + id + "]");
		}
    	return id;
    }
    
    /**
     * 增加角色关联的资源操作
     * @param id
     * @param operationIds
     */
    private void insertRoleOperation(String id, String[] operationIds) {
    	if (operationIds != null) {
    		for (int i = 0; i < operationIds.length; i++) {
    			Map<String, String> paramMap = new HashMap<String, String>();
    			paramMap.put("roleId", id);
    			paramMap.put("resourceKey", operationIds[i].trim());
    	    	getSqlMapClientTemplate().insert("RoleResource_insertRoleResource", paramMap);    			
    		}
    	}    	
    }

    /**
     * delete.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    public void deleteRole(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRole(String), 输入参数[" + id + "]");
		}
		
		// 删除角色
        getSqlMapClientTemplate().update("Role_deleteRole", id);
        
    	// 删除角色关联的资源操作
    	deleteRoleOperation(id);
    	
    	// 删除分配组组织和用户的角色关联
        getSqlMapClientTemplate().update("Role_deleteStaffRoleByRoleId", id);    	
        getSqlMapClientTemplate().update("Role_deleteDepartmentRoleByRoleId", id);        
    	
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRole(String)");
		}
    }
    
    /**
     * delete.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    public void deleteStaffRole(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteStaffRole(String), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Role_deleteStaffRole", id);
    	
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteStaffRole(String)");
		}
    }
    
    /**
     * delete.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    public void deleteDepartmentRole(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteDepartmentRole(String), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Role_deleteDepartmentRole", id);
    	
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteDepartmentRole(String)");
		}
    }    
    
    /**
     * 删除角色关联的资源操作.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    private void deleteRoleOperation(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRoleOperation(String), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Role_deleteRoleOperation", id);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRoleOperation(String)");
		}
    }    
    
    /**
     * update.
     * @param role role
     * @throws DataAccessException DataAccessException
     */
    public void updateRole(Role role, String[] operationIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateRole(Role), 输入参数[" + role + "]");
		}
    	getSqlMapClientTemplate().update("Role_updateRole", role);
    	
    	// 删除角色关联的资源操作
    	deleteRoleOperation(role.getRoleId());    	
    	
    	// 增加角色关联的资源操作
    	insertRoleOperation(role.getRoleId(), operationIds);
    	
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateRole(Role)");
		}
    }
    
    /**
     * update.
     * @param staffId staffId
     * @throws DataAccessException DataAccessException
     */
    public void updateStaffRole(String staffId, String[] roleIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateStaffRole(String, String[]), 输入参数[" + staffId + "," + roleIds + "]");
		}    	
    	// 删除员工关联角色
        getSqlMapClientTemplate().update("Role_deleteStaffRole", staffId);
        
    	// 增加员工关联角色
    	if (roleIds != null) {
    		for (int i = 0; i < roleIds.length; i++) {
    			Map paramMap = new HashMap();
    			paramMap.put("staffId", staffId);
    			paramMap.put("roleId", roleIds[i]);
    	    	getSqlMapClientTemplate().insert("Role_insertStaffRole", paramMap);    			
    		}
    	}            
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateStaffRole(String,String[])");
		}		
    }
    
    /**
     * update.
     * @param departmentId departmentId
     * @throws DataAccessException DataAccessException
     */
    public void updateDepartmentRole(String departmentId, String[] roleIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateDepartmentRole(String, String[]), 输入参数[" + departmentId + "," + roleIds + "]");
		}    	
    	// 删除组织关联角色
        getSqlMapClientTemplate().update("Role_deleteDepartmentRole", departmentId);
        
    	// 增加员工关联角色
    	if (roleIds != null) {
    		for (int i = 0; i < roleIds.length; i++) {
    			Map paramMap = new HashMap();
    			paramMap.put("departmentId", departmentId);
    			paramMap.put("roleId", roleIds[i]);
    	    	getSqlMapClientTemplate().insert("Role_insertDepartmentRole", paramMap);    			
    		}
    	}            
		
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateDepartmentRole(String,String[])");
		}		
    }
    
    /**
     * find.
     * @param id id
     * @return role
     * @throws DataAccessException DataAccessException
     */
    public Role findRole(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findRole(Role), 输入参数[" + pk + "]");
		}
        Role role = (Role) getSqlMapClientTemplate().queryForObject("Role_findRole", pk);
        
        if (role != null) {
        	// 得到角色关联的资源.
        	List<String> resourceIds = getSqlMapClientTemplate().queryForList("Role_listRoleResource", pk);
        	role.setResourceIds(resourceIds);
        }
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findRole(Role), 返回[" + role + "]");
		}
        return role;
    }
    
    /**
     * find.
     * @param name name
     * @return role
     * @throws DataAccessException DataAccessException
     */
    public Role findRoleByName(String name) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findRole(String), 输入参数[" + name + "]");
		}
        Role role = (Role) getSqlMapClientTemplate().queryForObject("Role_findRoleByName", name);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findRoleByName(String), 返回[" + name + "]");
		}
        return role;
    }    
    
    /**
     * list.
     * @param role role
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    public List listRole(Role role) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listRole(Role), 输入参数[" + role + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Role_listRole", role,role.getStart(), role.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRole(Role), 返回[" + list + "]");
		}
        return list;
    }  
    
    /**
     * list.
     * @param role role
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    public List listStaffRole(String id) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaffRole(String), 输入参数[" + id + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Role_listStaffRole", id);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaffRole(String), 返回[" + list + "]");
		}
        return list;
    }   
    
    /**
     * list.
     * @param role role
     * @return role list
     * @throws DataAccessException DataAccessException
     */
    public List listDepartmentRole(String id) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listDepartmentRole(String), 输入参数[" + id + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Role_listDepartmentRole", id);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listDepartmentRole(String), 返回[" + list + "]");
		}
        return list;
    }       
    
    /**
     * listCount.
     * @param role role
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listRoleCount(Role role) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listRoleCount(Role), 输入参数[" + role + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Role_listRoleCount", role);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRoleCount(Role), 返回[" + count + "]");
		}
        return count;
    }  
}
