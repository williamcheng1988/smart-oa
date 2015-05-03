package com.chz.smartoa.system.service.impl;

import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.system.pojo.Staff;

/**
 * 日志环境.
 * @author huangdhui
 *
 */
public class OperateLogContext{

	public String getOperatorId() {
		Staff staff = LoginUtils.getLoginStaff();
		if (staff != null) {
			return staff.getLoginName();
		}
		return null;
	}

	public String getOperatorName() {
		Staff staff = LoginUtils.getLoginStaff();
		if (staff != null) {
			return staff.getLoginName();
		}
		return null;
	}

	public String getClientIp() {
		String clientIp = LoginUtils.getClientIp();
        return clientIp;
        
	}
}
