package com.chz.smartoa.common.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 所有领域DO基类.
 * 
 * @author huangdhui
 * 
 */
public class BaseDomain implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6040124023440584351L;

	/** 最大行数.	 */
	private static final int MAX_ROWS = 9999999;

	/**
	 * 动态字段. 在ibatis文件中可用“fields.xxx”方式读取动态字段值
	 */
	protected Map fields = null;

	/** 起始行数（oracle物理行号从1开始）.
	 *  起始行数（mysql物理行号从0开始）.
	 * */
	private int start = 0;

	/**
	 * 限制条数（如果不设置结束行，缺省查所有满足条件记录）.
	 */
	private int limit = MAX_ROWS;

	/**
	 * 排序字段(例sp.spCode).
	 */
	private String sort;

	/**
	 * 正序|反序(例ASC).
	 */
	private String order;

	/**
	 * 设置动态字段值.
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param value
	 *            字段值
	 */
	public void setField(String fieldName, Object value) {
		if (fields == null) {
			 fields = new HashMap();
		}
		fields.put(fieldName, value);
	}
	
	/**
	 * 返回动态字段值.
	 * 
	 * @param fieldName
	 *            字段名称
	 * @return 对象
	 */
	public Object getField(String fieldName) {
		if (fields == null) {
			return null;
		}
		return getFields().get(fieldName);
	}

	/*@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}*/
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Map getFields() {
		if (fields == null) {
			fields = new HashMap();
		}
		return fields;
	}

	public void setFields(Map dynamicFields) {
		this.fields = fields;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	/**
	 * 清除属性
	 */
	@SuppressWarnings({ "rawtypes"})
	public static void clear(Class clazz, Object nullInstanse) {
		if (clazz != null && nullInstanse != null) {
			Method[] mds = clazz.getDeclaredMethods();
			for (Method method : mds) {
				if (method.getName().startsWith("set")) {
					try {
						method.invoke(nullInstanse, new Object[] {null});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
