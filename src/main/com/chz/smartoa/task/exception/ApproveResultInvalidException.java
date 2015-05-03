package com.chz.smartoa.task.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 通过查询用户列表异常
 * @author weson
 */
public class ApproveResultInvalidException extends BaseException {
	private static final long serialVersionUID = 1L;
	private int code;
	
	public ApproveResultInvalidException(int code,String msg) {
		super(msg);
        this.code = code;
	}
	public int getCode() {
		return code;
	}
}
