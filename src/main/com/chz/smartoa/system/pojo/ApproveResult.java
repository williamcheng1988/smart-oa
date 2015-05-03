package com.chz.smartoa.system.pojo;

public class ApproveResult {
	/**
	 * 审批结果:11：同意，12：不同意，13：退回修改，14:完成修改
	 */
	private int approveType;
	/**
	 * 审批意见
	 */
	private String approveDesc;
	/**
	 * 审批姓名
	 */
	private String realName;
	/**
	 * 审批时间
	 */
	private String endTime;
	
	public int getApproveType() {
		return approveType;
	}
	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}
	public String getApproveDesc() {
		return approveDesc;
	}
	public void setApproveDesc(String approveDesc) {
		this.approveDesc = approveDesc;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "AproveResult [approveType=" + approveType + ", approveDesc="
				+ approveDesc + ", realName=" + realName + ", endTime="
				+ endTime + "]";
	}
}
