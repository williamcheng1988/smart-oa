package com.chz.smartoa.system.action;

import java.io.Serializable;

public class ResultEntry implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public ResultEntry() {
		
	}
		
	public ResultEntry(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResultEntry(int code, String msg, Object list) {
		super();
		this.code = code;
		this.msg = msg;
		this.list = list;
	}
	
	private int code;
	private String msg;
	private Object list;
	
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
	public Object getList() {
		if(list==null)
			return "null";
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	
}
