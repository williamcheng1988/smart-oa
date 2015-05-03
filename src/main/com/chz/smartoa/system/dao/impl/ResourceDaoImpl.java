package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.ResourceDao;
import com.chz.smartoa.system.pojo.Resource;

/**
 * ResourceDao接口实现类.
 * @author huangdhui
 *
 */
public class ResourceDaoImpl extends SqlMapClientDaoSupport implements ResourceDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(ResourceDaoImpl.class);

    /**
     * 得到sequence.
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String getNextId() throws DataAccessException {
    	return (String) getSqlMapClientTemplate().queryForObject("Resource_getNextId");
    }
    
    /**
     * insert.
     * @param resource resource
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public String insertResource(Resource resource) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertResource(Resource), 输入参数[" + resource + "]");
    	}
        
    	getSqlMapClientTemplate().insert("Resource_insertResource", resource);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertResource(Resource), 返回[" + resource.getResourceId() + "]");
		}
    	return resource.getResourceId();
    }

    /**
     * delete.
     * @param resource resource
     * @throws DataAccessException DataAccessException
     */
    public void deleteResource(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteResource(String), 输入参数[" + id + "]");
		}
        getSqlMapClientTemplate().update("Resource_deleteResource", id);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteResource(String)");
		}
    }
    
    /**
     * delete.
     * @param resource resource
     * @throws DataAccessException DataAccessException
     */
    public void deleteResourceBySubsystem(String subsystem)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteResourceBySubsystem(String), 输入参数[" + subsystem + "]");
		}
        getSqlMapClientTemplate().update("Resource_deleteResourceBySubsystem", subsystem);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteResourceBySubsystem(String)");
		}
    }    
    
    /**
     * update.
     * @param resource resource
     * @throws DataAccessException DataAccessException
     */
    public void updateResource(Resource resource) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateResource(Resource), 输入参数[" + resource + "]");
		}
    	getSqlMapClientTemplate().update("Resource_updateResource", resource);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateResource(Resource)");
		}
    }
    
    /**
     * find.
     * @param id id
     * @return resource
     * @throws DataAccessException DataAccessException
     */
    public Resource findResource(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findResource(Resource), 输入参数[" + pk + "]");
		}
        Resource resource = (Resource) getSqlMapClientTemplate().queryForObject("Resource_findResource", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findResource(Resource), 返回[" + resource + "]");
		}
        return resource;
    }

    /**
     * find.
     * @param id id
     * @return resource
     * @throws DataAccessException DataAccessException
     */
    public Resource findResourceByKey(String resourceKey) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findResourceByKey(String), 输入参数[" + resourceKey + "]");
		}
        Resource resource = (Resource) getSqlMapClientTemplate().queryForObject("Resource_findResourceByKey", resourceKey);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findResourceByKey(String), 返回[" + resource + "]");
		}
        return resource;
    }
    
    @Override
    public List<String> listUrl(String authType) {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listUrl(String), 输入参数[" + authType + "]");
		}
    	List<String> urls = (List<String>) getSqlMapClientTemplate().queryForList("Resource_listUrl", authType);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listUrl(String), 返回[" + authType + "]");
		}
        return urls;
    }

    
    /**
     * list.
     * @param resource resource
     * @return resource list
     * @throws DataAccessException DataAccessException
     */
    public List<Resource> listResource(Resource resource) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listResource(Resource), 输入参数[" + resource + "]");
		}
        List<Resource> list = getSqlMapClientTemplate().queryForList("Resource_listResource", resource);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listResource(Resource), 返回[" + list + "]");
		}
        return list;
    }  
    
    /**
     * listCount.
     * @param resource resource
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listResourceCount(Resource resource) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listResourceCount(Resource), 输入参数[" + resource + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("Resource_listResourceCount", resource);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listResourceCount(Resource), 返回[" + count + "]");
		}
        return count;
    }  
}
