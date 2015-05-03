package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 用户登录时用户不存在异常.
 * @author huangdhui
 *
 */

public class StaffLoginNameErrorException extends BaseException {
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public StaffLoginNameErrorException(String msg) {
        super(msg);
    }
}
