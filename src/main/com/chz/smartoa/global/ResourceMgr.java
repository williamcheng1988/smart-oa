
package com.chz.smartoa.global;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.service.ResourceBiz;

/**
 * 菜单资源管理类
 * 
 * @author huangdhui
 * 
 */
public class ResourceMgr
{
    private static final Logger logger = Logger.getLogger(ResourceMgr.class);

    private static ResourceMgr instance;

    private ResourceBiz resourceBiz;
    
    // 查询系统所有操作资源（树形存放存储）
    private List<Resource> resources;
    
    //不需要鉴权地址列表
    private List<String> unAuths = new ArrayList<String>();
    
    //只需要登录鉴权地址列表
    private List<String> loginAuths = new ArrayList<String>();


    private ResourceMgr()
    {

    }

    public static ResourceMgr getInstance()
    {

        if (instance == null)
        {
            instance = new ResourceMgr();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init()
    {
        refresh();
    }

    /**
     * 更新缓存
     * @throws BOException
     */
    public void refresh()
    {
        // 加载所有资源信息
        loadResource();
        // 加载所有资源信息(树形存储)
        loadAllResource();
    }

    
    /**
     * 获取不鉴权列表
     * @return 
     */
    public List<String> getUnAuths() {
        return unAuths;
	}
    
    /**
     * 获取登录鉴权列表
     * @return 
     */
    public List<String> getLoginAuths() {
		return loginAuths;
	}
    

	public List<Resource> getResources() {
		return resources;
	}
	
	public List<Resource> deepCopy() throws IOException, ClassNotFoundException{     
		List<Resource> src = getResources();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();             
        ObjectOutputStream out = new ObjectOutputStream(byteOut);             
        out.writeObject(src);                    
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());             
        ObjectInputStream in =new ObjectInputStream(byteIn);             
        @SuppressWarnings("unchecked")
		List<Resource> dest = (List<Resource>)in.readObject();     
        in.close();
        byteIn.close();
        out.close();
        byteOut.close();
        return dest;         
    }  

	/**
     * 加载资源信息
     */
    private void loadResource()
    {
        logger.debug("into ResourceMgr.loadResource()");
        try
        {
            Resource res = new Resource();
            //不需要鉴权资源
            res.setAuthType(Resource.AUTH_TYPE_UNAUTH);
            List<Resource> unAuthResource = getResourceBiz().listResource(res);
            if(unAuthResource != null){
	    		for(Resource r : unAuthResource){
	    			this.unAuths.add(r.getAddressUrl());
	    		}
            }
            //登录鉴权列表
            res.setAuthType(Resource.AUTH_TYPE_LOGIN_AUTH);
            List<Resource> loginAuthResource = getResourceBiz().listResource(res);
            if(loginAuthResource != null){
	    		for(Resource r : loginAuthResource){
	    			this.loginAuths.add(r.getAddressUrl());
	    		}
            }
        }
        catch (Exception e)
        {
            logger.error("加载资源失败");
        }
        logger.debug("leave ResourceMgr.loadResource()");
    }
    
    /**
     * 加载所有资源信息
     */
    private void loadAllResource()
    {
        logger.debug("into ResourceMgr.loadAllResource()");
        this.resources =  getResourceBiz().getResources("root","1",null);
        logger.debug("leave ResourceMgr.loadAllResource()");
    }

	private ResourceBiz getResourceBiz()
    {
        if (resourceBiz == null)
        {
        	resourceBiz = (ResourceBiz) ServerInfo.wac.getBean("resourceBiz");
        }
        return resourceBiz;
    }
    
}
