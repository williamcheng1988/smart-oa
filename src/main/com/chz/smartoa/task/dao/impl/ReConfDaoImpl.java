package com.chz.smartoa.task.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.ReConfDao;
import com.chz.smartoa.task.pojo.ReConf;

public class ReConfDaoImpl extends BaseDaoiBatis implements ReConfDao{
	@Override
	public String insertReConf(List<ReConf> reConfs, String reProcdefId)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("进入insertReProcdef(ReProcdef reProcdef), 输入参数["+ reConfs + "," + reProcdefId + "]");
		}
		// 如果不为空，循环入库
		int cnt = 0;
		if (reConfs != null && reConfs.size() > 0) {
			for (ReConf reConf : reConfs) {
				reConf.setProcdef_id_(reProcdefId);
				getSqlMapClientTemplate().insert("ReConf_insertReConf_", reConf);
				cnt++;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("离开insertReProcdef(ReProcdef reProcdef)!");
		}
		return String.valueOf(cnt);
	}

	@Override
	public void deleteReConf(String procdef_id_) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteReConf(String procdef_id_), 输入参数[" + procdef_id_ + "]");
		}
        getSqlMapClientTemplate().update("ReConf_deleteReConf", procdef_id_);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteReConf(String procdef_id_)");
		}
	}

	@Override
	public List<ReConf> listReConf(String procdef_id_)
			throws DataAccessException {
		return this.listReConf(procdef_id_,0);
	}
	
	@Override
	public int getActionType(Integer confId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getActionType(String confId), 输入参数[" + confId + "]");
		}
		Integer actionType = (Integer)getSqlMapClientTemplate().queryForObject("ReConf_getActionType_",confId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getActionType(String confId),返回[" + actionType + "]");
		} 
		return actionType;
	}

	
	@Override
	public List<ReConf> listReConf(String procdef_id_, int sort_num_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listReProcdef(ReProcdef reProcdef), 输入参数[" + procdef_id_ + "]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("procdef_id_", procdef_id_);
		if (sort_num_ > 0) {
			params.put("sort_num_",sort_num_);
		}
		List<ReConf> reConfs = (List<ReConf>)getSqlMapClientTemplate().queryForList("ReConf_listReConf_",params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdef(ReProcdef reProcdef),返回[" + reConfs + "]");
		} 
		return reConfs;
	}
	
}
