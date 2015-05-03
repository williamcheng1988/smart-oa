package com.chz.smartoa.task.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.RuTask;
import com.chz.smartoa.task.pojo.RuTaskVo;


/**
 * RuTaskDao
 * @author wesson
 *
 */
public interface RuTaskDao {
	/**
	 * 插入待办任务
	 * @param ruTask
	 * @return
	 * @throws DataAccessException
	 */
    String insertRuTask(RuTask ruTask) throws DataAccessException ;
    /**
     * 删除任务
     * @param id 任务Id
     * @throws DataAccessException
     */
    void deleteRuTask(String id) throws DataAccessException;
    /**
     * 删除任务
     * @param ExecutionId 流程实例ID
     * @throws DataAccessException
     */
    void deleteRuTaskByExecutionId(String ExecutionId) throws DataAccessException;
    
    /**
     * 删除当前步骤所有任务
     * @param ExecutionId 流程实例ID
     * @throws DataAccessException
     */
    void deleteRuTaskByTaskNum(String ExecutionId) throws DataAccessException;
    
    /**
     * 删除任务
     * @param ExecutionId 流程实例ID
     * @param confId 流程节点ID
     * @throws DataAccessException
     */
	void deleteRuTaskByConfId(String ExecutionId, Integer confId) throws DataAccessException;

    /**
     * 更新任务
     * @param ruTask
     * @throws DataAccessException
     */
    void updateRuTask(RuTask ruTask) throws DataAccessException;
    
    /**
     * 任务委托
     * @param fromUser 委托人
     * @param toUser 被委托人
     * @throws DataAccessException
     */
    void delegation(String fromUser,String toUser) throws DataAccessException;
    
    /**
     * 查询要委托的流程实例ID集合
     * @param fromUser
     * @return
     * @throws DataAccessException
     */
    List<String> delegationTaskList(String fromUser) throws DataAccessException;
    
    /**
     * 任务委托撤销
     * @param delegationId 委托ID
     * @throws DataAccessException
     */
    void delegationCancel(String delegationId) throws DataAccessException;
    
    /**
     * 查询草稿
     * @param owner
     * @return
     * @throws DataAccessException
     */
    List<GeExecution> listDrafts(String owner) throws DataAccessException;
    
    /**
     * 查询任务
     * @param id
     * @return
     * @throws DataAccessException
     */
    RuTask findRuTask(String id) throws DataAccessException ;
    /**
     * 待办任务条数
     * @param params
     * @return
     * @throws DataAccessException
     */
    Integer getRuTaskCount(Map<String, Object> params) throws DataAccessException ;
    /**
     * 查询待办任务属性
     * @param taskId
     * @param user
     * @return 任务属性
     * @throws DataAccessException
     */
    Map<String, Object> getTodoTask(String taskId,String user) throws DataAccessException;
    /**
     * 查询待办任务列表
     * @param RuTaskVo
     * @return
     * @throws DataAccessException
     */
    List<RuTaskVo> listTodoTask(RuTaskVo task) throws DataAccessException;
    /**
     * 查询待办任务列表
     * @param RuTaskVo
     * @return
     * @throws DataAccessException
     */
    List<RuTaskVo> listTodoTask(Map<String, Object> params) throws DataAccessException;
    /**
     * 待办任务条数
     * @param RuTaskVo
     * @return
     * @throws DataAccessException
     */
    Integer listTodoTaskCount(RuTaskVo task) throws DataAccessException;
    
    /**
     * 查询该任务实例当前待办任务条数
     * @param executionId
     * @return 条数
     * @throws DataAccessException
     */
    Integer getTodoTaskCountForExecution(String executionId) throws DataAccessException;
}
