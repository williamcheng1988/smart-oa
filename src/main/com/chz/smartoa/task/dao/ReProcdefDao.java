package com.chz.smartoa.task.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.ReProcdef;
	
/**
 * 流程定义
 * @author wesson
 */
public interface ReProcdefDao{
		/**
		 * 新增流程定义
		 * @param reProcdef
		 * @return
		 * @throws DataAccessException
		 */
		String insertReProcdef(ReProcdef reProcdef) throws DataAccessException ;
	    /**
	     * 修改流程定义状态  初始化→启用←→挂起
	     * @param procdef_id_
	     * @param status
	     * @throws DataAccessException
	     */
	    void updateReProcdefStatus(String procdef_id_,int status) throws DataAccessException ;
	    /**
	     * 新增流程定义
	     * @param reProcdef
	     * @throws DataAccessException
	     */
	    void updateReProcdef(ReProcdef reProcdef) throws DataAccessException ;
	    /**
	     * 查询流程定义
	     * @param procdef_id_
	     * @return
	     * @throws DataAccessException
	     */
	    ReProcdef findReProcdefById(String procdef_id_) throws DataAccessException; 
	    
	    /**
	     * 查询流程定义明细
	     * @param procdef_id_
	     * @return
	     * @throws DataAccessException
	     */
	    List<Map<String, String>> findReProcdefConf(String procdef_id_) throws DataAccessException; 
	    
	    /**
	     * 查询最新版本流程
	     * @param procdefName
	     * @return
	     * @throws DataAccessException
	     */
	    public ReProcdef findReProcdefByName(String procdefName)throws DataAccessException;
	    /**
	     * 查询最新版本流程
	     * @param executionId
	     * @return
	     * @throws DataAccessException
	     */
	    public ReProcdef findReProcdefByExecutionId(String executionId)throws DataAccessException;
	    
	    /**
	     * 查询同名流程定义个数
	     * @param name_
	     * @return
	     * @throws DataAccessException
	     */
	    int findReProcdefCountByName(String name_) throws DataAccessException;
	    /**
	     * 查询流程定义列表
	     * @param reProcdef
	     * @return
	     * @throws DataAccessException
	     */
	    List<ReProcdef> listReProcdef(ReProcdef reProcdef) throws DataAccessException ;
	    /**
	     * 查询流程定义总数
	     * @param reProcdef
	     * @return
	     * @throws DataAccessException
	     */
	    Integer listReProcdefCount(ReProcdef reProcdef) throws DataAccessException ;
		/**
		 * 查询完成提醒标记
		 * @param procdef_id_
		 * @return  0：不提醒 ，1：提醒
		 * @throws DataAccessException
		 */
	    public int getCompleteRemind(String procdef_id_)  throws DataAccessException ;
	    /**
	     * 查询关联审批（之前有审批过的，自动审批）
	     * @param procdef_id_
	     * @return 0：不关联，1:关联
	     * @throws DataAccessException
	     */
		public int getUplink(String procdef_id_)  throws DataAccessException ;
		 /**
	     * 查询userName是否为procdef_id_管理呗
	     * @param procdef_id_ 流程ID
	     * @param manager 管理员
	     * @return 0：不是，1:是
	     * @throws DataAccessException
	     */
		public int getIsManager(String procdef_id_,String manager)  throws DataAccessException ;
		
		
}
