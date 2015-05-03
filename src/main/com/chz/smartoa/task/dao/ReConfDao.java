package com.chz.smartoa.task.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chz.smartoa.task.pojo.ReConf;

/**
 * 流程定义配置明细
 * @author wesson
 */
public interface ReConfDao{
	/**
	 * 新增流程明细列表
	 * @param reConfs
	 * @param reProcdefId
	 * @return
	 * @throws DataAccessException
	 */
	String insertReConf(List<ReConf> reConfs,String reProcdefId) throws DataAccessException;
    /**
     * 删除流程明细
     * @param procdef_id_
     * @throws DataAccessException
     */
    void deleteReConf(String procdef_id_) throws DataAccessException ;
    /**
     * 查询流程明细列表
     * @param procdef_id_
     * @return
     * @throws DataAccessException
     */
    List<ReConf> listReConf(String procdef_id_) throws DataAccessException;
    /**
	 * 查询流程明细列表
	 * @param procdef_id_
	 * @param sort_num_ 节点num
	 * @return ReConf list
	 */
	public List<ReConf> listReConf(String procdef_id_, int sort_num_) throws DataAccessException;
    
    /**
	 * 通过confId查询审批类型
	 * @param confId
	 * @return 1:审批（并行） 2.审批异或 3.阅处 4.传阅
	 */
	public int getActionType(Integer confId) throws DataAccessException;
	
}
