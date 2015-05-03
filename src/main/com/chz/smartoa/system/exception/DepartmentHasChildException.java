package com.chz.smartoa.system.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * 被删除组织不允许有子组织.
 * @author huangdhui
 *
 */
public class DepartmentHasChildException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public DepartmentHasChildException(String msg) {
        super(msg);
    }
}
