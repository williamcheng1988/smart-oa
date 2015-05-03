package com.chz.smartoa.task.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.ApproveResult;
import com.chz.smartoa.task.pojo.GeExecution;
import com.chz.smartoa.task.pojo.HiTask;
import com.chz.smartoa.task.pojo.HiTaskVo;


/**
 * HiTaskDao
 * @author wesson
 *
 */
public interface HiTaskDao {
	/**
	 * 插入办结记录
	 * @param hiTask
	 * @return
	 * @throws DataAccessException
	 */
    String insertHiTask(HiTask hiTask) throws DataAccessException;
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
     * 查询审批结果
     * @param params
     * @return
     * @throws DataAccessException
     */
    List<ApproveResult> listApproveResult(Map<String, Object> params) throws DataAccessException;
    /**
     * 查询审批记录条数
     * @param params
     * @return
     * @throws DataAccessException
     */
    Integer getApproveResultCnt(Map<String, Object> params) throws DataAccessException;
}
