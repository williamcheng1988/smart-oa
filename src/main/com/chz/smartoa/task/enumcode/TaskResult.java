package com.chz.smartoa.task.enumcode;

public enum TaskResult {
	/*
	用户记录：11：同意，12：不同意，13：退回修改，14:完成征询,15:提交,16:阅处,17：传阅
	系统记录：21：作废、22：跳过、23：阅处(此状态作废)、24：收阅、25：转办、26:征询、27:完成修改、29:委托
	 */
	Agree(11),
	Disagree(12),
	Back(13),
	DoneConsult(14),
	Submit(15),
	Read(16),
	Readonly(17),
	
	Invalid(21),
	Skip(22),
	View(24),
	Turn(25),
	Consult(26),
	Modify(27),
	Delegation(28);
	
	private int val;
	private TaskResult(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
}
