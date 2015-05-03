package com.chz.smartoa.common.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chz.smartoa.common.email.EmailHelper;
import com.chz.smartoa.common.log.LogHelper;
import com.chz.smartoa.common.schedule.ScheduleManager;
import com.chz.smartoa.common.schedule.TaskLoader;
import com.chz.smartoa.common.schedule.dao.TaskDao;
import com.chz.smartoa.common.util.VelocityUtils;
import com.chz.smartoa.fileUpload.util.PerpertiesTool;
import com.chz.smartoa.global.ResourceMgr;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.task.Handler.NoticeHelper;

/**
 * 初始化类.
 * @author huangdhui
 */
public class StartupServlet extends HttpServlet
{
	private static final long serialVersionUID = -1090892755361527699L;
	
	public final static String FS = System.getProperty("file.separator");
	
	protected static String home = "";

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("-------------------------now start system!------------------------------");

        String systemName = config.getInitParameter("systemName");
        System.out.println("system name is:" + systemName);

        /** **设置服务器运行路径***** */
        String path = null;
        File file = new File("..");
        try {
            path = file.getCanonicalPath();
        } catch (IOException ex) {

        }
        home = path + FS + "conf" + FS + systemName;
        System.out.println("home dir=" + home);
       
        /**初始化日志系统 */
        initLog(config);
        
        ServerInfo.wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        //加载权限验证资源
        initResource();
        //加载定时任务
        initTimerTask();
        //初始化邮件
        initEmail(config);
        //初始化通知模板
        initNoticeTemplate(config);
        //初始化模板工具
        initVelocityUtils(config);
        //读取配置文件
        initSysConfig(config);
    }
    private void initEmail(ServletConfig config)
    {
        
        try
        {
        	 System.out.println("Init email begin ...");
        	 String emailConfig = home + config.getInitParameter("emailConfig");
             System.out.println("emailConfig = " + emailConfig);
             EmailHelper.init(emailConfig);
             System.out.println("init email task successed !");
        }
        catch (Exception e)
        {
            System.out.println("Init email config failed!");
            e.printStackTrace();
        }
    }
    
    
    
    private void initLog(ServletConfig config)
    {
        
    	 System.out.println("Init log begin ...");
    	 String logConfig = home + config.getInitParameter("logConfig");
         String logRefreshTime = config.getInitParameter("logRefresh");
         System.out.println("logconfig = " + logConfig);
         System.out.println("logRefreshTime = " + logRefreshTime);
         if (logRefreshTime == null)
             logRefreshTime = "60";
         LogHelper.setLogRootPath(home);
         try {
//           // 加载XML配置文件(每N秒扫描一次配置文件,如果log4j.xml有更新,自动加载)
//           DOMConfigurator.configureAndWatch(logConfig, Integer.parseInt(logRefreshTime) * 1000);
             DOMConfigurator.configure(logConfig);
         }
         catch (Exception e)
         {
             System.out.println("Init log config failed !");
             e.printStackTrace();
         }
         System.out.println("Init log config successed !");
    }
    
    private void initResource()
    {
        
        try
        {
            System.out.println("Load authority resource begin ...");
            ResourceMgr.getInstance().init();
            System.out.println("Load authority resource successed !");
        }
        catch (Exception e1)
        {
            System.out.println("Load authority resource failed !");
            e1.printStackTrace();
        }
    }
    
    private void initTimerTask()
    {
        try
        {
    		System.out.println("Load timer task begin ...");
        	TaskLoader.start((TaskDao)ServerInfo.wac.getBean("taskDao"));
    		ScheduleManager.start();
    		System.out.println("Load timer task successed !");
        }
        catch (Exception e1)
        {
            System.out.println("Load timer task failed !");
            e1.printStackTrace();
        }
    }
    
    private void initNoticeTemplate(ServletConfig config)
    {
        try
        {
        	 System.out.println("Init notice template begin ...");
        	 String noticeTemplate = home + config.getInitParameter("noticeTemplate");
             System.out.println("noticeTemplate = " + noticeTemplate);
             NoticeHelper.init(noticeTemplate);
             System.out.println("init notice template successed !");
        }
        catch (Exception e)
        {
            System.out.println("Init notice template config failed!");
            e.printStackTrace();
        }
    }
    
    private void initVelocityUtils(ServletConfig config)
    {
        try
        {
        	 System.out.println("Init velocity utils begin ...");
        	 String vmPath = home + config.getInitParameter("vmPath");
             System.out.println("vmPath = " + vmPath);
             VelocityUtils.init(vmPath);
             System.out.println("Init velocity utils successed!");
        }
        catch (Exception e)
        {
            System.out.println("Init notice template config failed!");
            e.printStackTrace();
        }
    }
    
    
    // 读取系统配置
	private void initSysConfig(ServletConfig config)
    {
        try
        {
        	 System.out.println("Init sysconfig properties begin ...");
        	 String sysConfigProperties = home + config.getInitParameter("sysConfigProperties"); // 待确认配置所在地
             System.out.println("sysConfigProperties = " + sysConfigProperties);
             PerpertiesTool.init(sysConfigProperties);
             System.out.println("init sysconfig properties successed !");
        }
        catch (Exception e)
        {
            System.out.println("Init sysconfig properties failed!");
            e.printStackTrace();
        }
    }
    

}
