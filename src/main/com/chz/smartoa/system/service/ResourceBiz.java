package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.pojo.Resource;

public interface ResourceBiz {
	String insertResource(Resource resource);
	void updateResource(Resource resource);
	void deleteResource(String resourceId);
	Resource findResource(String resourceId);
	List<Resource> listResource(Resource resource);
	List<String> listUrl(String authType);
	List<Resource> getResources(String parentId,String resourceType,String authType);
	int listResourceCount(Resource resource);
}
