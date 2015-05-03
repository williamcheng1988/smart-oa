package com.chz.smartoa.task.Handler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.email.EmailHelper;

public class NoticeHelper {
	
	private static NoticeHelper noticeTemplate = null;
	
	private NoticeHelper(){
		super();
	}
	
	public static NoticeHelper getInstance(){
		if(noticeTemplate == null){
			noticeTemplate = new NoticeHelper();
		}
		return noticeTemplate;
	}
	
	/** 
     * logger.
     */
    private static final Logger logger = Logger.getLogger(EmailHelper.class);
    
	private static Properties properties = null;
    
	public static void init(String confFileName) throws Exception {
		try {
			if (properties == null) {
				properties = new Properties();
				properties.load(new BufferedInputStream(new FileInputStream(confFileName)));
			}
		} catch (Exception e) {
			logger.error("加载配置文件[" + confFileName + "]失败："+e);
			throw new Exception("加载配置文件[" + confFileName + "]失败："+e);
		}
	}

    public String getChsValue(String key) {
        try {
            return new String(((String) properties.get(key)).getBytes("ISO8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
        	logger.error(key+"读取失败!", e);
        }
        return "";
    }
    
    /**
     * 任务到达提醒邮件模板
     */
//    public static String EMAIL_TASK_ARRIVE = getChsValue("email.taskArrive");
    
    
}
