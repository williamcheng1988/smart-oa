package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

public class StaffLockException extends BaseException {
	private static final long serialVersionUID = 1L;
	private String code;

	/**
     * ����.
     * @param msg ������Ϣ.
     */
    public StaffLockException(String code,String msg) {
        super(msg);
        this.code = code;
    }

	public String getCode() {
		return code;
	}
}
