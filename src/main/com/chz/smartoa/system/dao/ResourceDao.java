package com.chz.smartoa.system.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.pojo.Resource;


/**
 * ResourceDao接口
 * @author huangdhui
 *
 */
public interface ResourceDao {
    /**
     * insert.
     * @param resource resource
     * @return id
     * @throws DataAccessException DataAccessException
     */
    String insertResource(Resource resource) throws DataAccessException ;

    /**
     * delete.
     * @param resource resource
     * @throws DataAccessException DataAccessException
     */
    void deleteResource(String id) throws DataAccessException ;
    
    void deleteResourceBySubsystem(String subsystem) throws DataAccessException ;    
    
    void updateResource(Resource resource) throws DataAccessException ;

    Resource findResource(String id) throws DataAccessException ;
    
    Resource findResourceByKey(String resourceKey) throws DataAccessException ;    
    
    List listResource(Resource resource) throws DataAccessException ;
    
    Integer listResourceCount(Resource resource) throws DataAccessException ;

	List<String> listUrl(String authType);
}
