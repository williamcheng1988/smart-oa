package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.system.dao.ResourceDao;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.service.ResourceBiz;

public class ResourceBizImpl implements ResourceBiz {

	private ResourceDao resourceDao;
	
	@Override
	public String insertResource(Resource resource) {
		return resourceDao.insertResource(resource);
	}
	@Override
	public void deleteResource(String resourceId) {
		resourceDao.deleteResource(resourceId);
	}
	@Override
	public void updateResource(Resource resource) {
		resourceDao.updateResource(resource);
	}
	@Override
	public Resource findResource(String resourceId) {
		return resourceDao.findResource(resourceId);
	}
	@Override
	public List<String> listUrl(String authType) {
		return resourceDao.listUrl(authType);
	}
	@Override
	public List<Resource> listResource(Resource resource) {
		return resourceDao.listResource(resource);
	}
	
	@Override
	public List<Resource> getResources(String parentId,String resourceType,String authType){
    	Resource resource = new Resource();
    	resource.setParentId(parentId);
    	resource.setResourceType(resourceType);
    	resource.setAuthType(authType);
    	List<Resource> list = this.listResource(resource);
    	if(list == null){
    		return null;
    	}
    	for (Resource r:list) {
    		r.setResources(getResources(r.getResourceId(),resourceType,authType));
		}
    	return list;
    }
	
	@Override
	public int listResourceCount(Resource resource) {
		return resourceDao.listResourceCount(resource);
	}
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
