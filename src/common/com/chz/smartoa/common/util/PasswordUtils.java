package com.chz.smartoa.common.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class PasswordUtils {
	/**
	 * logger.
	 */
	private static final Logger logger = Logger.getLogger(PasswordUtils.class);

	private static XMLConfiguration xmlConfiguration;
	private final static String REMIN_Key = "ReminDate";

	public static void init(String confFileName) throws Exception {
		logger.debug("init password config : " + confFileName);
			try {
				xmlConfiguration = new XMLConfiguration(confFileName);
			} catch (ConfigurationException e) {
				logger.error("配置文件[" + confFileName + "]不存在");
				throw new Exception("配置文件[" + confFileName + "]不存在");
			}
		
	}

	public static int getRemidDay() {
		return Integer.valueOf(xmlConfiguration.getString(REMIN_Key, "7"));
	}

	public static String getExpirDate() {
		return xmlConfiguration.getString("expirDate", "90");
	}

	public static String gethistoryTimes() {
		return xmlConfiguration.getString("historyTimes", "3");
	}

	public static boolean validate(String passWord) {
		// 必须包含特殊字符
		String symbol = "-\\\\~`!@#$%^&*()_+={}\\[\\]|\"':;<>,.?/"; // 符合号集合
		if (!passWord.matches(".*?[" + symbol + "]+.*?")) {
			// System.out.println("必须包含特殊字符");
			// logger.debug("必须包含特殊字符");
			return false;
		}
		// 必须包含数字
		if (!passWord.matches(".*?[\\d]+.*?")) {
			// System.out.println("必须包含数字");
			return false;
		}
		// 必须包含小写字母
		if (!passWord.matches(".*?[a-z]+.*?")
				|| !passWord.matches(".*?[A-Z]+.*?")) {
			// System.out.println("必须包含大小写字母");
			return false;
		}

		if (passWord.length() >= 16 && passWord.length() <= 24) {
			return true;
		} else {
			return false;
		}
	}

	public static XMLConfiguration getXmlConfiguration() {
		return xmlConfiguration;
	}

	public static void setXmlConfiguration(XMLConfiguration xmlConfiguration) {
		PasswordUtils.xmlConfiguration = xmlConfiguration;
	}

}
