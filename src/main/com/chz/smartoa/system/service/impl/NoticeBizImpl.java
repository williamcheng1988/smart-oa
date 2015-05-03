package com.chz.smartoa.system.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.chz.smartoa.common.dao.BaseDao;
import com.chz.smartoa.system.pojo.Notice;
import com.chz.smartoa.system.service.NoticeBiz;

public class NoticeBizImpl implements NoticeBiz{
	
	private BaseDao baseDao;
	
	@Override
	public String insert(Notice notice) throws SQLException {
		String rowId  = String.valueOf(UUID.randomUUID());
		notice.setRowId(rowId);
		baseDao.insert("Notice_insertNotice", notice);
		return rowId;
	}

	@Override
	public void delete(String rowId) throws SQLException {
		baseDao.delete("Notice_deleteNotice", rowId);
	}

	@Override
	public List<Notice> list(Notice notice) throws SQLException {
		@SuppressWarnings("unchecked")
		List<Notice> notices = (List<Notice>) baseDao.queryForList("Notice_listNotice", notice);
		return notices;
	}

	@Override
	public void updateNoticeSendSuccess(Notice notice) throws SQLException {
		baseDao.update("Notice_updateNoticeSendSuccess", notice);
	}

	@Override
	public void updateNoticeSendCount(Notice notice) throws SQLException {
		baseDao.update("Notice_updateNoticeSendCount", notice);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
