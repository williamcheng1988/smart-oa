package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

public class StaffLockException extends BaseException {
	private static final long serialVersionUID = 1L;
	private String code;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public StaffLockException(String code,String msg) {
        super(msg);
        this.code = code;
    }

	public String getCode() {
		return code;
	}
}
