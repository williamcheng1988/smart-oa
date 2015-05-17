package com.chz.smartoa.task.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.RuConfDao;
import com.chz.smartoa.task.pojo.RuConf;

public class RuConfDaoImpl extends BaseDaoiBatis implements RuConfDao{
	
	 @Override
	    public String initRuConfs(String executionId,String procdefId) throws DataAccessException {
	    	if (logger.isDebugEnabled()) {
	    		logger.debug("进入initRuConfs(executionId), 输入参数[" + executionId + "]");
	    	}
	    	Map<String, String> params = new HashMap<String, String>();
	    	params.put("executionId", executionId);
	    	params.put("procdefId", procdefId);
	    	getSqlMapClientTemplate().insert("RuConf_initRuTaskConfs", params);
			if (logger.isDebugEnabled()) {
	    		logger.debug("离开initRuConfs(executionId), 返回[" + null + "]");
			}
	    	return null;
	    }
	
	@Override
	public String insertRuConf(List<RuConf> ruConfs, String executionId)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("进入insertRuConf(List<RuConf> ruConfs, String executionId), 输入参数["+ ruConfs + "," + executionId + "]");
		}
		// 如果不为空，循环入库
		int cnt = 0;
		if (ruConfs != null && ruConfs.size() > 0) {
			for (RuConf ruConf : ruConfs) {
				ruConf.setExecution_id_(executionId);
				getSqlMapClientTemplate().insert("RuConf_insertRuConf_", ruConf);
				cnt++;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("离开insertRuConf(List<RuConf> ruConfs, String executionId)!");
		}
		return String.valueOf(cnt);
	}

	@Override
	public void deleteRuConf(String executionId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuConf(String executionId), 输入参数[" + executionId + "]");
		}
        getSqlMapClientTemplate().update("RuConf_deleteRuConf", executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuConf(String executionId)");
		}
	}

	@Override
	public void deleteRuConf(String[] confIds) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuConf(String[] confIds), 输入参数[" + confIds + "]");
		}
        getSqlMapClientTemplate().update("RuConf_deleteRuConfByIds", confIds);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuConf(String[] confIds)");
		}
	}
	
	@Override
	public List<RuConf> listRuConf(String executionId)
			throws DataAccessException {
		return this.listRuConf(executionId,0);
	}
	
	@Override
	public List<Map<String, String>> getRuConfMap(String executionId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getRuConfMap(String executionId), 输入参数[" + executionId+"]");
		}
		List<Map<String, String>> ruConfs = (List<Map<String, String>>)getSqlMapClientTemplate().queryForList("RuConf_mapRuConf_",executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getRuConfMap(String executionId),返回[" + ruConfs + "]");
		} 
		return ruConfs;
	}
	
	@Override
	public int getActionType(Integer confId) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getActionType(String confId), 输入参数[" + confId + "]");
		}
		Integer actionType = (Integer)getSqlMapClientTemplate().queryForObject("RuConf_getActionType_",confId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getActionType(String confId),返回[" + actionType + "]");
		} 
		return actionType;
	}

	
	@Override
	public List<RuConf> listRuConf(String executionId, int sort_num_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listRuConf(String executionId, int sort_num_), 输入参数[" + executionId +","+sort_num_+ "]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("execution_id_", executionId);
		if (sort_num_ > 0) {
			params.put("sort_num_",sort_num_);
		}
		List<RuConf> ruConfs = (List<RuConf>)getSqlMapClientTemplate().queryForList("RuConf_listRuConf_",params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开listRuConf(String executionId, int sort_num_),返回[" + ruConfs + "]");
		} 
		return ruConfs;
	}
	@Override
	public List<RuConf> listRuConfWithStatus(String executionId)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listRuConfWithStatus(String executionId), 输入参数[" + executionId + "]");
		}
		List<RuConf> ruConfs = (List<RuConf>)getSqlMapClientTemplate().queryForList("RuConf_listRuConfWithStatus_",executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开listRuConfWithStatus(String executionId),返回[" + ruConfs + "]");
		} 
		return ruConfs;
	}
}
