package com.chz.smartoa.task.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.ReProcdefDao;
import com.chz.smartoa.task.pojo.ReProcdef;

public class ReProcdefDaoImpl extends BaseDaoiBatis implements ReProcdefDao{
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(ReProcdefDaoImpl.class);

	@Override
	public String insertReProcdef(ReProcdef reProcdef)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertReProcdef(ReProcdef reProcdef), 输入参数[" + reProcdef + "]");
		}   
		//生成ID
		String id  = String.valueOf(UUID.randomUUID());
		reProcdef.setProcdef_id_(id);
		getSqlMapClientTemplate().insert("ReProcdef_insertReProcdef_", reProcdef);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertReProcdef(ReProcdef reProcdef),返回[" + id + "]");
		} 
		return id;
	}

	@Override
	public void updateReProcdefStatus(String procdef_id_,int status)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateReProcdefStatus(String procdef_id_,int status), 输入参数[" + procdef_id_+","+status + "]");
		}   
		Map<String, String> params = new HashMap<String, String>();
		params.put("procdef_id_", procdef_id_);
		params.put("procdef_status_", String.valueOf(status));
		getSqlMapClientTemplate().update("ReProcdef_updateReProcdefStatus_", params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateReProcdefStatus(String procdef_id_,int status)!");
		} 
	}

	@Override
	public void updateReProcdef(ReProcdef reProcdef) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateReProcdef(ReProcdef reProcdef), 输入参数[" + reProcdef + "]");
		}   
		getSqlMapClientTemplate().update("ReProcdef_updateReProcdef_", reProcdef);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateReProcdef(ReProcdef reProcdef)!");
		} 
	}

	@Override
	public ReProcdef findReProcdefById(String procdef_id_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入findReProcdef(String procdef_id_), 输入参数[" + procdef_id_ + "]");
		}
		ReProcdef reProcdef = (ReProcdef)getSqlMapClientTemplate().queryForObject("ReProcdef_findReProcdef_", procdef_id_);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdef(String procdef_id_),返回[" + reProcdef + "]");
		} 
		return reProcdef;
	}
	
	@Override
	public List<Map<String, String>> findReProcdefConf(String procdef_id_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入findReProcdefConf(String procdef_id_), 输入参数[" + procdef_id_ + "]");
		}
		List<Map<String, String>> map = (List<Map<String, String>>)getSqlMapClientTemplate().queryForList("ReProcdef_findReProcdefConf_", procdef_id_);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdefConf(String procdef_id_),返回[" + map + "]");
		} 
		return map;
	}
	
	@Override
	public ReProcdef findReProcdefByName(String procdefName)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入findReProcdefByName(String procdefName), 输入参数[" + procdefName + "]");
		}
		ReProcdef reProcdef = (ReProcdef)getSqlMapClientTemplate().queryForObject("ReProcdef_findReProcdefByName_", procdefName);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdefByName(String procdefName),返回[" + reProcdef + "]");
		} 
		return reProcdef;
	}
	
	@Override
	public ReProcdef findReProcdefByExecutionId(String executionId)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入findReProcdefByExecutionId(String executionId), 输入参数[" + executionId + "]");
		}
		ReProcdef reProcdef = (ReProcdef)getSqlMapClientTemplate().queryForObject("ReProcdef_findReProcdefByExecutionId", executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdefByExecutionId(String executionId),返回[" + reProcdef + "]");
		} 
		return reProcdef;
	}

	@Override
	public int findReProcdefCountByName(String name_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入findReProcdefCountByName(String name_), 输入参数[" + name_ + "]");
		}
		Object obj = getSqlMapClientTemplate().queryForObject("ReProcdef_findReProcdefCount_", name_);
		int cnt = 0;
		if(obj != null){
			cnt = (Integer)obj;
		}
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdefCountByName(String name_),返回[" + cnt + "]");
		} 
		return cnt;
	}

	@Override
	public List<ReProcdef> listReProcdef(ReProcdef reProcdef)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listReProcdef(ReProcdef reProcdef), 输入参数[" + reProcdef + "]");
		}
		//生成ID
		List<ReProcdef> reProcdefs = (List<ReProcdef>)getSqlMapClientTemplate().queryForList("ReProcdef_listReProcdef_",reProcdef,reProcdef.getStart(),reProcdef.getLimit());
		if (logger.isDebugEnabled()) {
    		logger.debug("离开findReProcdef(ReProcdef reProcdef),返回[" + reProcdefs + "]");
		} 
		return reProcdefs;
	}

	@Override
	public Integer listReProcdefCount(ReProcdef reProcdef)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入listReProcdefCount(ReProcdef reProcdef), 输入参数[" + reProcdef + "]");
		}
		//生成ID
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("ReProcdef_listReProcdefCount_", reProcdef);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开listReProcdefCount(ReProcdef reProcdef),返回[" + count + "]");
		} 
		return count;
	}

	@Override
	public int getCompleteRemind(String procdef_id_) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getCompleteRemind(String procdef_id_), 输入参数[" + procdef_id_ + "]");
		}
		Integer completeRemind = (Integer)getSqlMapClientTemplate().queryForObject("ReProcdef_getCompleteRemind_", procdef_id_);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getCompleteRemind(String procdef_id_),返回[" + completeRemind + "]");
		} 
		return completeRemind;
	}

	@Override
	public int getUplink(String procdef_id_) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入etUplink(String procdef_id_), 输入参数[" + procdef_id_ + "]");
		}
		Integer uplink = (Integer)getSqlMapClientTemplate().queryForObject("ReProcdef_getUplink_", procdef_id_);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开etUplink(String procdef_id_),返回[" + uplink + "]");
		} 
		return uplink;
	}
	
	@Override
	public int getIsManager(String procdef_id_, String manager_)
			throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入getIsManager(String procdef_id_,String userName), 输入参数[" + procdef_id_ +","+manager_+ "]");
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("procdef_id_", procdef_id_);
		params.put("manager_", manager_);
		Integer uplink = (Integer)getSqlMapClientTemplate().queryForObject("ReProcdef_getIsManager", params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开getIsManager(String procdef_id_, String userName),返回[" + uplink + "]");
		} 
		return uplink;
	}
}
