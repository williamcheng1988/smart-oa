package com.chz.smartoa.task.enumcode;

/**
 * 流程异常编号
 * @author wesson
 */
public enum TaskError {

	NotFoundUserByRole(3001),
	NotFoundReProcdef(3002),
	NotFoundReConf(3003),
	TaskInvalid(3004),
	NotFoundProject(3005),
	ApproveResultInvalid(3006),
	NotFoundUserByPost(3007);
	
	private int val;
	private TaskError(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
}


