/**

 * @author: huangdhui
 */
package com.chz.smartoa.common.email;

/**
 * 邮件类型枚举类.
 * <ul>
 * <li>PLAIN: 简单类型邮件，即邮件正文只包含有文本信息（可带附件）。</li>
 * <li>SIMPLE: HTML类型邮件，即邮件正文由HTML信息构成（可带附件）。</li>
 * </ul>
 */

public enum EmailType {
    /**
     * 邮件类型.
     */
    PLAIN, HTML;
}
