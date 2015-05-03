package com.chz.smartoa.task.enumcode;

public enum RecodeType {
	User(3001),
	System(3002);
	
	private int val;
	private RecodeType(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
}
