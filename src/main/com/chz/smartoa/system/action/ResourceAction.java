package com.chz.smartoa.system.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.ResourceBiz;

public class ResourceAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	/**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(ResourceAction.class);
    
    private static final String PREVIEW_RESOURCES = "PREVIEW_RESOURCES";    
    
	private ResourceBiz resourceBiz;
	private OperateLogBiz operateLogBiz;
	
	private Resource resource;
	private List<Resource> resources;

		
	/**
	 * 资源列表
	 */
	public String list() {
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(resource);

		// 得到分页结果
		resources = resourceBiz.listResource(resource);

		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）
		setTotalCount(resourceBiz.listResourceCount(resource));
		
		return "list";
	}
	
	/**
	 * 资源树.
	 * @return
	 */
	public String listTree() {
//		// 得到全部子系统
//		subsystems = subsystemBiz.listSubsystem(new Subsystem());
//		
//		// 如果用户选择了子系统，查询子系统全部权限元数据，否则查询全部
//		ResourceCategory resourceCategory = new ResourceCategory();
//		resourceCategory.setParentId(ResourceCategory.ROOT_PARENT_ID);
//		resourceCategory.setSubsystem(subsystemId);
//		//resourceCategorys = resourceBiz.listRecursiveCategory(resourceCategory, null);
//		groupResourceCategorys = resourceBiz.mapRecursiveCategory(resourceCategory, null);
		return "listTree";
	}	

	public void setResourceBiz(ResourceBiz resourceBiz) {
		this.resourceBiz = resourceBiz;
	}	
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	

}
