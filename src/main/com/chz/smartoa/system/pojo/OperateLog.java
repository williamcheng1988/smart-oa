package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * OperateLog对象.
 * 
 * @author huangdhui
 * 
 */
public class OperateLog extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作日志ID.
	 */
	private String logId;
	/**
	 * 被操作实体ID.
	 */
	private String entityId;
	/**
	 * 操作结果.
	 */
	private String operateResult;
	/**
	 * 客户端IP.
	 */
	private String clientIp;
	/**
	 * 操作员ID.
	 */
	private String operatorId;
	/**
	 * 操作说明.
	 */
	private String description;
	/**
	 * 操作时间.
	 */
	private String operateDate;
	/**
	 * 被操作实体名称.
	 */
	private String entityName;
	/**
	 * 操作员名称.
	 */
	private String operatorName;
	/**
	 * 日志类型:1.登录日志;2.操作日志
	 */
	private String logType;

	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getOperateResult() {
		return this.operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("logId:").append(logId).append(",");
		sb.append("entityId:").append(entityId).append(",");
		sb.append("operateResult:").append(operateResult).append(",");
		sb.append("clientIp:").append(clientIp).append(",");
		sb.append("operatorId:").append(operatorId).append(",");
		sb.append("description:").append(description).append(",");
		sb.append("operateDate:").append(operateDate).append(",");
		sb.append("entityName:").append(entityName).append(",");
		sb.append("operatorName:").append(operatorName).append(",");
		sb.append("logType:").append(logType).append(",");
		sb.append("}");
		return sb.toString();
	}

}
