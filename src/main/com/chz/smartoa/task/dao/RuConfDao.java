package com.chz.smartoa.task.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.RuConf;

/**
 * 运行中的流程步骤Dao
 * @author wesson
 */
public interface RuConfDao{
	
	/**
	 * 根据流程定义初始化任务配置
	 * @param executionId 流程实例ID
	 * @param procdefId 流程定义ID
	 * @return
	 * @throws DataAccessException
	 */
    String initRuConfs(String executionId,String procdefId) throws DataAccessException ;
	/**
	 * 新增流程明细列表
	 * @param ruConfs
	 * @param executionId
	 * @return
	 * @throws DataAccessException
	 */
	String insertRuConf(List<RuConf> ruConfs,String executionId) throws DataAccessException;
    /**
     * 删除流程明细
     * @param executionId
     * @throws DataAccessException
     */
    void deleteRuConf(String executionId) throws DataAccessException ;
    /**
     * 删除流程明细
     * @param executionId
     * @throws DataAccessException
     */
    void deleteRuConf(String[] confIds) throws DataAccessException ;
    /**
     * 查询流程明细列表
     * @param executionId
     * @return
     * @throws DataAccessException
     */
    List<RuConf> listRuConf(String executionId) throws DataAccessException;
    /**
     * 查询流程明细
     * @param executionId
     * @return
     * @throws DataAccessException
     */
    List<Map<String, String>> getRuConfMap(String executionId) throws DataAccessException;
    /**
	 * 查询流程明细列表
	 * @param executionId
	 * @param sort_num_ 节点num
	 * @return RuConf list
	 */
	public List<RuConf> listRuConf(String executionId, int sort_num_) throws DataAccessException;
	
	 /**
	 * 查询流程明细列表,且判断节点是否可编辑
	 * @param executionId
	 * @return RuConf list
	 */
	public List<RuConf> listRuConfWithStatus(String executionId) throws DataAccessException;
	
	
    
    /**
	 * 通过confId查询审批类型
	 * @param confId
	 * @return 1:审批（并行） 2.审批异或 3.阅处 4.传阅
	 */
	public int getActionType(Integer confId) throws DataAccessException;
	
}
