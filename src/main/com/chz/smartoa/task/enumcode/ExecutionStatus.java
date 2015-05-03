package com.chz.smartoa.task.enumcode;

/**
 * 实例状态
 * @author wesson
 */
public enum ExecutionStatus {
	/**
	 * 0:暂存,1:审批中2:审批通过,3:审批不通过,4:任务作废
	 */
	
	Cache(0),
	Approving(1),
	Complete(2),
	NoPass(3),
	Invalid(4);
	
	private int val;
	private ExecutionStatus(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
}
