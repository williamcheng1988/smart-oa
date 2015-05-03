package com.chz.smartoa.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.dao.OperateLogDao;
import com.chz.smartoa.system.pojo.OperateLog;
import com.chz.smartoa.system.service.OperateLogBiz;

public class OperateLogBizImpl implements OperateLogBiz {
	
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(OperateLogBizImpl.class);

    /*** 操作员ID常量.*/
    String OPERATOR_ID = "{operator_id}";

    /*** 操作员名称常量.*/
    String OPERATOR_NAME = "{operator_name}";

    /*** 客户端IP常量.*/
    String CLIENT_IP = "{client_ip}";

    /*** 被操作实体ID常量.*/
    String ENTITY_ID = "{entity_id}";

    /*** 被操作实体名称常量.*/
    String ENTITY_NAME = "{entity_name}";

    /*** 业务日志数据访问对象.*/
    private OperateLogDao operateLogDao = null;

    /*** 业务日志环境.*/
    private OperateLogContext operateLogContext = null;    
    
	public void setOperateLogContext(OperateLogContext operateLogContext) {
		this.operateLogContext = operateLogContext;
	}

	/**
	 * 记录操作日志.
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 */
	public void info(OperateLogType logType, String entityId, String entityName,String operateResult) {
//		try {
			info(logType, entityId, entityName, operateResult, null);
//		} catch (Exception e) {
//			logger.error("插入操作日志失败："+e);
//		}
	}


	/**
	 * 记录操作日志.
	 * @param httpSession 会话 
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 * @param description 描述
	 */
	public void info(OperateLogType logType, String entityId, String entityName, String operateResult,String description) {
		// 使用缺省内置日志环境
		info(operateLogContext, logType, entityId, entityName, operateResult, description);
	}
	
	/**
	 * 记录操作日志.
	 * @param logType 日志类型
	 * @param entityId 被操作实体ID
	 * @param entityName 被操作实体名称
	 * @param operateResult 操作结果
	 * @param otherInfoMap 其它信息 
	 */	
	public void info(OperateLogContext context,OperateLogType logType, String entityId, String entityName,
			String operateResult,String description) {
        if (StringUtils.isEmpty(logType.toString()) || StringUtils.isEmpty(entityId) 
        		|| StringUtils.isEmpty(entityName) || StringUtils.isEmpty(operateResult)) {
            logger.error("输入参数为空, logType=" + logType + "entityId=" + entityId + "entityName=" + entityName + "operationResult=" + operateResult);
            return;
        }

        //防止字段过长报错
        if(entityId!=null&&entityId.length()>64){
        	entityId = entityId.substring(0,63);
        }        
        if(entityName!=null&&entityName.length()>64){
        	entityName = entityName.substring(0,63);
        }

        // 构造日志对象基本信息（操作时间以数据库记录时间为准）
        OperateLog operateLog = new OperateLog();
        operateLog.setLogType(logType.toString());
        operateLog.setEntityId(entityId);
        operateLog.setEntityName(entityName);
        operateLog.setOperateResult(operateResult);
        operateLog.setDescription(description);
        operateLog.setOperateDate(null);

        // 回调业务日志环境接口对象
        String operatorId = context.getOperatorId();
        String operatorName = context.getOperatorName();
        String clientIp = context.getClientIp();
        
        operateLog.setOperatorId(operatorId);
        operateLog.setOperatorName(operatorName);
        operateLog.setClientIp(clientIp);
      
        if (operateLog.getOperatorId() == null || operateLog.getOperatorName() == null || operateLog.getClientIp() == null) {
            logger.error("没有找到操作者信息！");
            return;
        }
        this.operateLogDao.insertOperateLog(operateLog);
	}

	public void setOperateLogDao(OperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
	
    public List<OperateLog> listOperateLog(OperateLog operateLog) throws DataAccessException {
    	List<OperateLog> list = operateLogDao.listOperateLog(operateLog);
    	return list;
    }
    
    @Override
    public List<OperateLog> listLoginLog(OperateLog operateLog)
    		throws DataAccessException {
    	return  operateLogDao.listLoginLog(operateLog);
    }
    
    public int listOperateLogCount(OperateLog operateLog) throws DataAccessException {
    	int count = operateLogDao.listOperateLogCount(operateLog);
    	return count;
    }  
    
    @Override
    public int listLoginLogCount(OperateLog operateLog)
    		throws DataAccessException {
    	return operateLogDao.listLoginLogCount(operateLog);
    }
}
