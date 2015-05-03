package com.chz.smartoa.system.service;

import java.sql.SQLException;
import java.util.List;

import com.chz.smartoa.system.pojo.Notice;

public interface NoticeBiz {
	/**
	 * 新增记录
	 * @param obj 参数对象
	 */
	public String insert(Notice notice) throws SQLException;
	/**
	 * 删除记录
	 * @param rowId
	 */
	public void delete(String rowId) throws SQLException;
	/**
	 * 查询记录
	 * @param notice
	 */
	public List<Notice> list(Notice notice) throws SQLException;
	/**
	 * 标记发送成功
	 * @param notice
	 */
	public void updateNoticeSendSuccess(Notice notice) throws SQLException;
	/**
	 * 修改发送次数
	 * @param notice
	 */
	public void updateNoticeSendCount(Notice notice) throws SQLException;
}
