package com.chz.smartoa.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.pojo.StaffRole;

/**
 * StaffDao接口实现类.
 * @author huangdnhui
 *
 */
public class StaffDaoImpl extends SqlMapClientDaoSupport implements StaffDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(StaffDaoImpl.class);

    /**
     * 得到sequence.
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String getNextId() throws DataAccessException {
    	return (String) getSqlMapClientTemplate().queryForObject("Staff_getNextId");
    }
    
    /**
     * insert.
     * @param staff staff
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String insertStaff(Staff staff) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertStaff(Staff), 输入参数[" + staff + "]");
    	}
    	getSqlMapClientTemplate().insert("Staff_insertStaff", staff);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertStaff(Staff)]");
		}
    	return staff.getLoginName();
    }
    
    @Override
    public void insertStaffRole(StaffRole staffRole)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入insertStaff(staffRole), 输入参数[" + staffRole + "]");
    	}
    	getSqlMapClientTemplate().insert("Staff_insertStaffRole", staffRole);    			
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertStaff(staffRole)");
		}
    }
    
    @Override
    public String insertStaffRoles(String loginName, String[] roleIds)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入insertStaff(loginName,roleIds), 输入参数[" + loginName +","+roleIds+ "]");
    	}
    	if (roleIds != null) {
    		for (int i = 0; i < roleIds.length; i++) {
    			StaffRole staffRole = new StaffRole();
    			staffRole.setStaffId(loginName);
    			staffRole.setRoleId(roleIds[i].trim());
    	    	getSqlMapClientTemplate().insert("Staff_insertStaffRole", staffRole);    			
    		}
    	}   
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertStaff(loginName,roleIds)");
		}
    	return null;
    }

    /**
     * delete.
     * @param staff staff
     * @throws DataAccessException DataAccessException
     */
    public void deleteStaff(String loginName)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteStaff(String), 输入参数[" + loginName + "]");
		}
        getSqlMapClientTemplate().update("Staff_deleteStaff", loginName);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteStaff(Staff)");
		}
    }
    
    /**
     * update.
     * @param staff staff
     * @throws DataAccessException DataAccessException
     */
    public void updateStaff(Staff staff) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateStaff(Staff), 输入参数[" + staff + "]");
		}
    	getSqlMapClientTemplate().update("Staff_updateStaff", staff);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateStaff(Staff)");
		}
    }
    
    public Integer deleteStaffRoleByProjectId(String projectId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteStaffRoleByProjectId(projectId), 输入参数[" + projectId + "]");
		}
    	Integer cnt = getSqlMapClientTemplate().update("Staff_deleteStaffRoleByProjectId", projectId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteStaffRoleByProjectId(projectId)");
		}
		return cnt;
    }
       
    /**
     * find.
     * @param loginName loginName
     * @return staff
     * @throws DataAccessException DataAccessException
     */
    public Staff findStaffByLoginName(String loginName) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findStaffByLoginName(String), 输入参数[" + loginName + "]");
		}
        Staff staff = (Staff) getSqlMapClientTemplate().queryForObject("Staff_findStaffByLoginName", loginName);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findStaffByLoginName(String), 返回[" + loginName + "]");
		}
        return staff;
    }    
    
    /**
     * list.
     * @param staff staff
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    public List listStaff(Staff staff) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaff(Staff), 输入参数[" + staff + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Staff_listStaff", 
        		staff, staff.getStart(), staff.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaff(Staff), 返回[" + list + "]");
		}
        return list;
    } 
    
    /**
     * list.
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    public List listStaff(String loginName) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaff(Staff)], 输入参数[" + loginName + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Staff_contacts",loginName);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaff(Staff), 返回[" + list + "]");
		}
        return list;
    } 
    
    @Override
    public List<String> listOperationUri(String loginName)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listOperationUri(loginName), 输入参数[" + loginName + "]");
		}
        List<String> list = getSqlMapClientTemplate().queryForList("Staff_listOperationUris",loginName);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listOperationUri(loginName), 返回[" + list + "]");
		}
        return list;
    }
    
    @Override
    public List<String> listResourceIds(String loginName)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listResourceIds(loginName), 输入参数[" + loginName + "]");
		}
        List<String> list = getSqlMapClientTemplate().queryForList("Staff_listResourceIds",loginName);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listResourceIds(loginName), 返回[" + list + "]");
		}
        return list;
    }
    
    /**
     * list.
     * @param resourceKey 
     * @param operationKey 
     * @return staff list
     * @throws DataAccessException DataAccessException
     */
    public List listStaffByRes(String resourceKey, String operationKey) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaffByRes(String,String), 输入参数[" + resourceKey + "," + operationKey + "]");
		}
		
		if (resourceKey== null || operationKey == null) {
			return null;
		}
		
		Map paramMap = new HashMap();
		paramMap.put("resourceKey", resourceKey);
		paramMap.put("operationKey", operationKey);		
        List list = getSqlMapClientTemplate().queryForList("Staff_listStaffByRes",  paramMap);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaff(Staff), 返回[" + list + "]");
		}
        return list;
    }      
    
    /**
     * listCount.
     * @param staff staff
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listStaffCount(Staff staff) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaffCount(Staff), 输入参数[" + staff + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Staff_listStaffCount", staff);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaffCount(Staff), 返回[" + count + "]");
		}
        return count;
    }  
    
    /**
     * assignRole.
     * @param id 员工id
     * @param roleIds 角色IDS
     * @throws DataAccessException DataAccessException
     */
    public void assignRole(String loginName, String[] roleIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入assignRole(String, String[]), 输入参数[" + loginName + "," + roleIds + "]");
		}
        // 先删除旧的角色
		getSqlMapClientTemplate().delete("Staff_deleteStaffRole", loginName);
		
		// 再增加新的角色
		for (int i = 0; i < roleIds.length ; i++) {
			StaffRole staffRole =  new StaffRole();
			staffRole.setStaffId(loginName);
			staffRole.setRoleId(roleIds[i].trim());
			getSqlMapClientTemplate().insert("Staff_insertStaffRole", staffRole);
		}
		
		if (logger.isDebugEnabled()) {
        	logger.debug("离开assignRole(String, String[])");
		}
    }   
    
    @Override
    public List<String> listStaffByRole(StaffRole staffRole)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listStaffByRole(Role), 输入参数[" + staffRole + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("Staff_listStaffByStaffRole", staffRole);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listStaffByRole(Role), 返回[" + list + "]");
		}
    	return list;
    }
}
