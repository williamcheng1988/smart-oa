package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

public class StaffPasswordErrorException extends BaseException {
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public StaffPasswordErrorException(String msg) {
        super(msg);
    }
}
