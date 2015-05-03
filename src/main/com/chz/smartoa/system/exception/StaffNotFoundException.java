package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 账户不存在
 * @author huangdhui
 *
 */
public class StaffNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public StaffNotFoundException(String msg) {
        super(msg);
    }
}
