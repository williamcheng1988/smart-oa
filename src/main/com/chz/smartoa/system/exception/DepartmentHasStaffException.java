package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 被删除组织不允许有成员.
 * @author huangdhui
 *
 */
public class DepartmentHasStaffException extends BaseException {
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public DepartmentHasStaffException(String msg) {
        super(msg);
    }
}
