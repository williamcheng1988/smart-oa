package com.chz.smartoa.system.pojo;

import com.chz.smartoa.common.base.BaseDomain;

public class Notice extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	private String rowId;
	//消息接收者
	private String toPeople;
	//抄送对象(仅对邮件方式有效)
	private String ccPeople;
	//主题
	private String title;
	//内容
	private String content;
	//html内容
	private String htmlContent;
	//站内通知次数（-1代表已通知;-2代表不通知）
	private int station = -2;
	//邮件通知次数（-1代表已通知;-2代表不通知）
	private int email =-2;
	//短信通知次数（-1代表已通知;-2代表不通知）
	private int sms = -2;
	//创建时间
	private String createTime;
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getToPeople() {
		return toPeople;
	}
	public void setToPeople(String toPeople) {
		this.toPeople = toPeople;
	}
	public String getCcPeople() {
		return ccPeople;
	}
	public void setCcPeople(String ccPeople) {
		this.ccPeople = ccPeople;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public int getEmail() {
		return email;
	}
	public void setEmail(int email) {
		this.email = email;
	}
	public int getSms() {
		return sms;
	}
	public void setSms(int sms) {
		this.sms = sms;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Notice [rowId=" + rowId + ", toPeople=" + toPeople
				+ ", ccPeople=" + ccPeople + ", title=" + title + ", content="
				+ content + ", htmlContent=" + htmlContent + ", station="
				+ station + ", email=" + email + ", sms=" + sms
				+ ", createTime=" + createTime + "]";
	}
}
