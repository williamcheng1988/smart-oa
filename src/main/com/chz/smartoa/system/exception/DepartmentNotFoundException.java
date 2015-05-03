package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 被删除组织不存在.
 * @author huangdhui
 *
 */
public class DepartmentNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}
