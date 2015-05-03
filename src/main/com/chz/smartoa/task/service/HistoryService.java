package com.chz.smartoa.task.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.HiTask;
import com.chz.smartoa.task.pojo.HiTaskVo;
import com.chz.smartoa.task.pojo.RuTask;

/**
 * 流程历史
 * @author wesson
 */
public interface HistoryService{
	/**
	 * 插入已办结
	 * @param execution
	 * @param ruTask
	 * @param approveResult
	 * 	                  用户记录：11：同意，12：不同意，13：退回修改，14:完成征询      25：转办、26:征询
	 */
	void insertTaskHistory(GeExecution execution,RuTask ruTask,ApproveResult approveResult) throws DataAccessException;
	/**
	 * 插入已办结
	 * @param execution
	 * @param ruTask
	 * @param resultCode
	 *        系统记录：21：作废、22：跳过、23：阅处、24：收阅、27:完成修改
	 */
	void insertTaskHistory(GeExecution execution,RuTask ruTask,int resultCode) throws DataAccessException;
	/**
	 * 插入已办结
	 * @param execution
	 * @param ruTask
	 * @param resultCode
	 *        系统记录：28:新增
	 */
	void insertTaskHistory(GeExecution execution) throws DataAccessException;
	/**
	 * 查询跟踪任务
	 * @param excutionId
	 * @return
	 * @throws DataAccessException
	 */
//    Map<String, Object> findTaskHistory(String excutionId) throws DataAccessException;
	
	/**
     * 查询任务跟踪列表
     * @param hiTask
     * @return
     * @throws DataAccessException
     */
    List<HiTaskVo> listHiTaskForIndex(HiTask hiTask) throws DataAccessException;
    /**
     * 查询任务跟踪列表
     * @param hiTask
     * @return
     * @throws DataAccessException
     */
    List<HiTaskVo> listHiTask(GeExecution exectuion) throws DataAccessException;
    /**
     * 查询任务跟踪总数
     * @param hiTask
     * @return
     * @throws DataAccessException
     */
     Integer listHiTaskCount(GeExecution exectuion) throws DataAccessException;
	
	/**
	 * 查询流程审批意见
	 * @param params
	 */
	List<ApproveResult> listApproveResult(Map<String, Object> params);
	
	/**
	 * 是否已审批过
	 * @param params
	 * @return
	 */
	boolean getIsApproved(Map<String, Object> params);
	
}
