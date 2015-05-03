package com.chz.smartoa.task.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.common.ibatis.BaseDaoiBatis;
import com.chz.smartoa.task.dao.RuTaskDao;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.pojo.RuTaskVo;

/**
 * RuTaskDao接口实现类.
 * @author wesson
 *
 */
public class RuTaskDaoImpl extends BaseDaoiBatis implements RuTaskDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(RuTaskDaoImpl.class);

    public String insertRuTask(RuTask ruTask) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入insertRuTask(RuTask), 输入参数[" + ruTask + "]");
    	}
    	getSqlMapClientTemplate().insert("RuTask_insertRuTask", ruTask);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开insertRuTask(RuTask), 返回[" + null + "]");
		}
    	return null;
    }

    public void deleteRuTask(String id)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuTask(String id), 输入参数[" + id + "]");
		}
		Map<String,String> params = new HashMap<String, String>();
    	params.put("taskId",id);
        getSqlMapClientTemplate().update("RuTask_deleteRuTask", params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuTask(String id)");
		}
    }
    
    @Override
    public void deleteRuTaskByConfId(String executionId, Integer confId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuTaskByConfId(String ExecutionId, Integer confId), 输入参数["+executionId+","+confId+"]");
		}
    	Map<String,String> params = new HashMap<String, String>();
    	params.put("executionId", executionId);
    	params.put("confId", confId.toString());
        getSqlMapClientTemplate().update("RuTask_deleteRuTask",params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuTaskByConfId(String executionId, Integer confId)");
		}
    }
    
    @Override
    public void deleteRuTaskByExecutionId(String executionId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuTaskByExecutionId(String executionId), 输入参数[" + executionId + "]");
		}
    	Map<String,String> params = new HashMap<String, String>();
    	params.put("executionId", executionId);
        getSqlMapClientTemplate().update("RuTask_deleteRuTask", params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuTaskByExecutionId(String executionId)");
		}
    }

    @Override
    public void deleteRuTaskByTaskNum(String executionId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入deleteRuTaskByTaskNum(String executionId), 输入参数[" + executionId + "]");
		}
        getSqlMapClientTemplate().update("RuTask_deleteRuTaskByTuskNum", executionId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开deleteRuTaskByTaskNum(String executionId)");
		}
    }
    
    @Override
    public void updateRuTask(RuTask ruTask) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("进入updateRuTask(RuTask), 输入参数[" + ruTask + "]");
		}
    	getSqlMapClientTemplate().update("RuTask_updateRuTask", ruTask);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开updateRuTask(RuTask)");
		}
    }
    
    @Override
    public void delegation(String fromUser, String toUser)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入delegation(fromUser,toUser), 输入参数[" + fromUser+","+toUser + "]");
		}
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("fromUser", fromUser);
    	params.put("toUser", toUser);
    	getSqlMapClientTemplate().update("RuTask_delegationTask", params);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开delegation(fromUser，toUser)");
		}
    	
    }
    
    @Override
    public List<String> delegationTaskList(String fromUser)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入delegationTaskList(fromUser), 输入参数[" + fromUser+"]");
		}
    	List<String> list =  getSqlMapClientTemplate().queryForList("RuTask_delegationTaskList", fromUser);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开delegationTaskList(fromUser)");
		}
		return list;
    }
    
    @Override
    public void delegationCancel(String delegationId) throws DataAccessException {
    	if (logger.isDebugEnabled()) {
    		logger.debug("进入delegationCancel(fromUser), 输入参数[" + delegationId + "]");
		}
    	getSqlMapClientTemplate().update("RuTask_delegationCancel", delegationId);
		if (logger.isDebugEnabled()) {
    		logger.debug("离开delegationCancel(RuTask)");
		}
    	
    }
    
    @Override
    public RuTask findRuTask(String pk) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入findRuTask(RuTask), 输入参数[" + pk + "]");
		}
        RuTask ruTask = (RuTask) getSqlMapClientTemplate().queryForObject("RuTask_findRuTask", pk);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开findRuTask(RuTask), 返回[" + ruTask + "]");
		}
        return ruTask;
    }
    
    
    @Override
    public Integer getRuTaskCount(Map<String, Object> params)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入getRuTaskCount(Map<String, Object> params), 输入参数[" + params + "]");
		}
    	Integer count = (Integer) getSqlMapClientTemplate().queryForObject("RuTask_findRuTaskCount", params);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开getRuTaskCount(Map<String, Object> params), 返回[" + count + "]");
		}
        return count;
    }
    @Override
    public Map<String,Object> getTodoTask(String taskId,String user)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入getTodoTask(String taskId,String user), 输入参数[" + taskId + "]");
		}
    	Map<String,String> params = new HashMap<String, String>();
    	params.put("taskId", taskId);
    	params.put("user", user);
    	Map<String, Object> resultMap = (Map<String, Object>)getSqlMapClientTemplate().queryForObject("RuTask_getTodoTask",params);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开getTodoTask(String taskId,String user), 返回[" + resultMap + "]");
		}
        return resultMap;
    }
    public List<RuTaskVo> listTodoTask(RuTaskVo task) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listRuTask(RuTask), 输入参数[" + task + "]");
		}
        List<RuTaskVo> list = getSqlMapClientTemplate().queryForList("RuTask_listTodoTask");
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRuTask(RuTask), 返回[" + list + "]");
		}
        return list;
    } 
    
    @Override
    public List<GeExecution> listDrafts(String owner) throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入draftList(String owner), 输入参数[" + owner + "]");
		}
        List<GeExecution> list = getSqlMapClientTemplate().queryForList("GeExecution_draftList",owner);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开draftList(String owner), 返回[" + list + "]");
		}
        return list;
    }
    
    @Override
    public List<RuTaskVo> listTodoTask(Map<String, Object> params)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入listRuTask(Map), 输入参数[" + params + "]");
		}
        List<RuTaskVo> list = getSqlMapClientTemplate().queryForList("RuTask_listTodoTaskByMap",params);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRuTask(Map), 返回[" + list + "]");
		}
        return list;
    }
    
    public Integer listTodoTaskCount(RuTaskVo task) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("进入listRuTaskCount(RuTask), 输入参数[" + task + "]");
		}
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("RuTask_listTodoTaskCount", task);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开listRuTaskCount(RuTask), 返回[" + count + "]");
		}
        return count;
    }  
    
    @Override
    public Integer getTodoTaskCountForExecution(String executionId)
    		throws DataAccessException {
    	if (logger.isDebugEnabled()) {
        	logger.debug("进入getTodoTaskCountForExecution(String executionId), 输入参数[" + executionId + "]");
		}
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("RuTask_getTodoTaskCountForExecution", executionId);
		if (logger.isDebugEnabled()) {
        	logger.debug("离开getTodoTaskCountForExecution(String executionId), 返回[" + count + "]");
		}
        return count;
    }
}
