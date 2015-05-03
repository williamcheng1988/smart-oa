package com.chz.smartoa.delegation.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * Delegation
 * 
 * @author wesson
 */
public class Delegation extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	/**
	 * ID.
	 */
	private String delegationId;
	/**
	 * 委托人.
	 */
	private String fromUser;
	/**
	 * 委托人名称.
	 */
	private String fromUserName;
	/**
	 * 被委托人.
	 */
	private String toUser;
	/**
	 * 被委托人名称.
	 */
	private String toUserName;
	/**
	 * 开始时间.
	 */
	private String startDate;
	/**
	 * 结束时间.
	 */
	private String endDate;
	/**
	 * 状态 0：已撤消，1：有效;
	 */
	private Integer status;
	/**
	 * 委托时间.
	 */
	private String createDate;
	/**
	 * 创建人.
	 */
	private String createor;
	
	/**
	 * 修改时间
	 */
	private String updateDate;
	
	//待办条数
	private int todoCount;
	//已办条数
	private int historyCount;

	public String getCreateor() {
		return this.createor;
	}
	public void setCreateor(String createor) {
		this.createor = createor;
	}
	public String getStartDate() {
		return this.startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getToUser() {
		return this.toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getFromUserName() {
		return this.fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getEndDate() {
		return this.endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDelegationId() {
		return delegationId;
	}
	public void setDelegationId(String delegationId) {
		this.delegationId = delegationId;
	}
	public String getFromUser() {
		return this.fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getToUserName() {
		return this.toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusStr() throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if ("1".equals(String.valueOf(status))){
				if(Calendar.getInstance().getTime().after(sf.parse(this.endDate))){
					return "已过期";
				}else{
					return "有效";
				}
			}else {
				return "已撤销";
			}
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("createor:").append(createor).append(",");
		sb.append("startDate:").append(startDate).append(",");
		sb.append("toUser:").append(toUser).append(",");
		sb.append("fromUserName:").append(fromUserName).append(",");
		sb.append("endDate:").append(endDate).append(",");
		sb.append("delegationId:").append(delegationId).append(",");
		sb.append("fromUser:").append(fromUser).append(",");
		sb.append("createDate:").append(createDate).append(",");
		sb.append("toUserName:").append(toUserName).append(",");
		sb.append("status:").append(status).append(",");
		sb.append("}");
		return sb.toString();
	}

}
