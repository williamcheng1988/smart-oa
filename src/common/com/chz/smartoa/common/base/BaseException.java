package com.chz.smartoa.common.base;

/**
 * 业务逻辑异常.
 * @author huangdhui
 *
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = -7611410034297878874L;

	/**
     * 构造.
     * @param msg 错误信息.
     */
    public BaseException(String msg) {
        super(msg);
    }
    
    /**
     * 构造.
     * @param msg 错误信息
     * @param t 前一异常
     */
    public BaseException(String msg, Throwable t) {
        super(msg, t);
        this.setStackTrace(t.getStackTrace());
    }
}
