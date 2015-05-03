package com.chz.smartoa.common.ibatis;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 处理sql参数
 * @author huangdhui
 *
 */
public class FormatSql {	
	public static String getSql(String sql, Object[] paras) {

		if (paras == null || paras.length == 0) {
			return sql;
		}
		String newSql = new String(sql);

		for (Object obj : paras) {
			String value = "'" +StringEscapeUtils.escapeSql( obj.toString()) + "'";
			newSql = newSql.replaceFirst("\\?", value);
		}		
		return newSql;
	}
}
