package com.chz.smartoa.task.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.HiTaskDao;
import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.HiTask;
import com.chz.smartoa.task.pojo.HiTaskVo;

/**
 * HiTaskDao接口实现类.
 * @author wesson
 *
 */
public class HiTaskDaoImpl extends BaseDaoiBatis implements HiTaskDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(HiTaskDaoImpl.class);
    
    public String insertHiTask(HiTask hiTask) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertHiTask(HiTask), 输入参数[" + hiTask + "]");
    	}
    	getSqlMapClientTemplate().insert("HiTask_insertHiTask", hiTask);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertHiTask(HiTask), 返回[" + null + "]");
		}
    	return null;
    }
    
    public HiTask findHiTask(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findHiTask(HiTask), 输入参数[" + pk + "]");
		}
        HiTask hiTask = (HiTask) getSqlMapClientTemplate().queryForObject("HiTask_findHiTask", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findHiTask(HiTask), 返回[" + hiTask + "]");
		}
        return hiTask;
    }
    
    @Override
    public List<HiTaskVo> listHiTaskForIndex(HiTask hiTask)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listHiTaskForIndex(HiTask), 输入参数[" + hiTask + "]");
		}
    	List<HiTaskVo> list = getSqlMapClientTemplate().queryForList("RuTask_listHiTaskForIndex", hiTask);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listHiTaskForIndex(HiTask), 返回[" + list + "]");
		}
        return list;
    }
    
    @Override
    public List<HiTaskVo> listHiTask(GeExecution exectuion) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listHiTask(exectuion), 输入参数[" + exectuion + "]");
		}
		List<HiTaskVo> list = getSqlMapClientTemplate().queryForList("HiTask_listHiTask", exectuion,exectuion.getStart(),exectuion.getLimit());
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listHiTask(exectuion), 返回[" + list + "]");
		}
        return list;
    }  
    
    @Override
    public Integer listHiTaskCount(GeExecution exectuion) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listHiTaskCount(exectuion), 输入参数[" + exectuion + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("HiTask_listHiTaskCount", exectuion);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listHiTaskCount(exectuion), 返回[" + count + "]");
		}
        return count;
    }  
    
    @Override
    public Integer getApproveResultCnt(Map<String, Object> params)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入getApproveResultCnt(Map<String, Object> params), 输入参数[" + params + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("HiTask_getApproveResultCnt", params);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开getApproveResultCnt(Map<String, Object> params), 返回[" + count + "]");
		}
        return count;
    }
    
    @Override
    public List<ApproveResult> listApproveResult(Map<String, Object> params)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listApproveResult(Map<String, Object> params), 输入参数[" + params + "]");
		}
    	List<ApproveResult> list = getSqlMapClientTemplate().queryForList("HiTask_listApproveResult", params);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listApproveResult(Map<String, Object> params), 返回[" + list + "]");
		}
		return list;
    }
}
