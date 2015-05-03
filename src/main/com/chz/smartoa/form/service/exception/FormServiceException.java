/**
 * 
 */
package com.chz.smartoa.form.service.exception;

import com.chz.smartoa.common.base.BaseException;

/**
 * @author william
 * @tiem 1:48 2014/11/21
 */
public class FormServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3363837924613217341L;

	/**
	 * @param msg
	 */
	public FormServiceException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public FormServiceException(String msg, Throwable t) {
		super(msg, t);
	}

}
