package com.chz.smartoa.common.util;

import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.chz.smartoa.system.constant.TemplateType;

public class VelocityUtils {
	
	/** 
     * logger.
     */
    private static final Logger logger = Logger.getLogger(VelocityUtils.class);
	
	private static VelocityEngine velocityEngine = null;
	
	public static void init(String vmPath) throws Exception {
		velocityEngine = new VelocityEngine();
	    velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vmPath);
	    velocityEngine.setProperty(Velocity.ENCODING_DEFAULT, "GBK");
	    velocityEngine.setProperty(Velocity.INPUT_ENCODING, "GBK");
	    velocityEngine.setProperty(Velocity.OUTPUT_ENCODING, "GBK");      
	    velocityEngine.init(); 
	}
    
    public static String parseVm(TemplateType template,Map<String, Object> params) throws Exception{
    	String path = template.getPath();
		try {
			Template t = velocityEngine.getTemplate(path);
			VelocityContext ctx = new VelocityContext();
	    	if(params != null && params.size() > 0){
	    		for (String key :params.keySet()) {
	    			ctx.put(key, params.get(key));
				}
	    	}
	    	 StringWriter sw = new StringWriter();
	    	 t.merge(ctx, sw);
	    	 return sw.toString();
		} catch (ResourceNotFoundException e) {
			logger.error(path+" not found!"+e);
			throw new Exception(path+" not found!"+e);
		} catch (ParseErrorException e) {
			logger.error(path+" parse error!"+e);
			throw new Exception(path+" parse error!"+e);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
    }
}
