package com.chz.smartoa.task.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.ReConf;
import com.chz.smartoa.task.pojo.ReProcdef;
import com.chz.smartoa.task.pojo.RuConf;

/**
 * 管理 流程定义、控制发布 操作
 * @author wesson
 */
public interface RepositoryService{
	/**
	 * 新增流程定义
	 * @param reProcdef
	 * @return
	 */
	String insertReProcdef(ReProcdef reProcdef);
	/**
	 * 修改流程
	 * @param reProcdef
	 * @return
	 */
	void updateReProcdef(ReProcdef reProcdef);
	/**
	 * 修改流程状态
	 * @param reProcdef
	 * @return
	 */
	void updateReProcdefStatus(String procdef_id_,int status);
	/**
	 * 通过流程名称查询流程条数
	 * @param name
	 * @return
	 */
	int findReProcdefCnt(String name);
	/**
	 * 流程定义列表
	 * @param reProcdef
	 * @return
	 */
	List<ReProcdef> listReProcdef(ReProcdef reProcdef);
	/**
	 * 流程配置
	 * @param reProcdef
	 * @return
	 */
	List<ReConf> listReConf(String procdef_id_);
	
	/**
	 * 新增流程配置
	 * @param reConfs
	 * @return
	 */
	String insertReConfs(List<ReConf> reConfs,String reProcdefId);
	
	/**
	 * 流程条数
	 * @param reProcdef
	 * @return
	 */
	int listReProcdefCount(ReProcdef reProcdef);
	
	/**
	 * 查询流程定义
	 * @param reProcdef_id_
	 * @return
	 */
	ReProcdef findReProcdef(String reProcdef_id_);
	/**
	 * 查询流程定义明细
	 * @param executionId
	 * @return
	 */
	List<Map<String, String>> listReporcdefConf(String executionId);
	 /**
     * 查询最新版本流程
     * @param procdefName
     * @return
     * @throws DataAccessException
     */
    ReProcdef findReProcdefByName(String procdefName);
    /**
     * 查询最新版本流程
     * @param executionId
     * @return
     * @throws DataAccessException
     */
    ReProcdef findReProcdefByExecutionId(String executionId);
	/**
	 * 查询当前节点流程明细
	 * @param executionId
	 * @param sort_num_
	 * @return
	 */
	List<RuConf> listRuConf(String executionId,int sort_num_);
	/**
	 * 查询当前节点流程明细
	 * @param executionId
	 * @param sort_num_
	 * @return
	 */
	List<RuConf> listRuConfWithStatus(String executionId);
	/**
	 * 通过confId查询审批类型
	 * @param confId
	 * @return 1:审批（并行） 2.审批异或 3.阅处 4.传阅
	 */
	int getActionType(Integer confId);
	/**
	 * 关联审批（之前有审批过的，自动审批）
	 * @param confId
	 * @return 0：不关联，1:关联
	 */
	int getUplink(String procdef_id_);
	/**
	 * 完成提醒
	 * @param procdef_id_
	 * @return 0：不提醒 ，1：提醒
	 */
	int getCompleteRemind(String procdef_id_);
	/**
	 * 是否是流程管理员  1是，0不是
	 * @param procdef_id_
	 * @param 用户名
	 * @return
	 */
	int getIsManager(String procdef_id_,String manager);
	/**
	 * 保存运行时的流程节点
	 * @param executionId
	 * @param procdefId
	 * @return
	 */
	void insertRuConfs(String executionId,String procdefId);
	/**
	 *  保存运行时的流程节点
	 * @param confs
	 * @param executionId
	 */
	void insertRuConfs(List<RuConf> confs,String executionId);
	/**
	 * 删除运行时的可修改的流程节点
	 * @param executionId
	 * @param procdefId
	 * @return
	 */
	void deleteRuConfs(String[] confIds);
}
