package com.chz.smartoa.system.action;

import java.io.Serializable;

public class OperateResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public OperateResult() {
		
	}
		
	public OperateResult(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	private int code;
	private String msg;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
