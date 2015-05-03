package com.chz.smartoa.task.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.chz.smartoa.task.dao.ReConfDao;
import com.chz.smartoa.task.dao.ReProcdefDao;
import com.chz.smartoa.task.pojo.ReConf;
import com.chz.smartoa.task.pojo.ReProcdef;
import com.chz.smartoa.task.service.RepositoryService;
	
/**
 * 流程定义
 * @author wesson
 */
public class RepositoryServiceImpl implements RepositoryService{
	
	private static final Logger logger = Logger.getLogger(RepositoryServiceImpl.class);
	
	private ReProcdefDao reProcdefDao_;
	private ReConfDao reConfDao_;
	
	@Override
	public String insertReProcdef(ReProcdef reProcdef) {
		return reProcdefDao_.insertReProcdef(reProcdef);
	}
	@Override
	public void updateReProcdef(ReProcdef reProcdef) {
		reProcdefDao_.updateReProcdef(reProcdef);
	}
	@Override
	public void updateReProcdefStatus(String procdef_id_,int status) {
		reProcdefDao_.updateReProcdefStatus(procdef_id_,status);
	}
	@Override
	public int findReProcdefCnt(String name) {
		return reProcdefDao_.findReProcdefCountByName(name);
	}
	@Override
	public List<ReProcdef> listReProcdef(ReProcdef reProcdef) {
		return reProcdefDao_.listReProcdef(reProcdef);
	}
	@Override
	public int listReProcdefCount(ReProcdef reProcdef) {
		return reProcdefDao_.listReProcdefCount(reProcdef);
	}
	
	@Override
	public List<ReConf> listReConf(String procdef_id_) {
		return reConfDao_.listReConf(procdef_id_);
	}
	@Override
	public String insertReConfs(List<ReConf> reConfs,String reProcdefId) {
		ReProcdef reProcdef = reProcdefDao_.findReProcdefById(reProcdefId);
		logger.debug(reProcdef.getName_()+":"+reProcdef.getVersion_()+",status:"+reProcdef.getProcdef_status_());
		if(reProcdef.getProcdef_status_() == 0){//初始化状态
			reConfDao_.deleteReConf(reProcdefId);
		}else{//新建一个版本
			int version = reProcdefDao_.findReProcdefCountByName(reProcdef.getName_());
			reProcdef.setProcdef_status_(0);
			reProcdef.setVersion_(String.valueOf(++version));
			reProcdefId = reProcdefDao_.insertReProcdef(reProcdef);
		}
		return reConfDao_.insertReConf(reConfs, reProcdefId);
	}
	@Override
	public ReProcdef findReProcdef(String reProcdef_id_) {
		return reProcdefDao_.findReProcdefById(reProcdef_id_);
	}
	@Override
	public List<Map<String, String>> listReporcdefConf(String reProcdef_id_) {
		return reProcdefDao_.findReProcdefConf(reProcdef_id_);
	}
	
	@Override
	public ReProcdef findReProcdefByName(String procdefName) {
		return reProcdefDao_.findReProcdefByName(procdefName);
	}
	@Override
	public ReProcdef findReProcdefByExecutionId(String executionId) {
		return reProcdefDao_.findReProcdefByExecutionId(executionId);
	}
	@Override
	public int getCompleteRemind(String procdef_id_) {
		return reProcdefDao_.getCompleteRemind(procdef_id_);
	}
	@Override
	public int getUplink(String procdef_id_) {
		return reProcdefDao_.getUplink(procdef_id_);
	}
	@Override
	public int getActionType(Integer confId) {
		return reConfDao_.getActionType(confId);
	}
	@Override
	public List<ReConf> listReConf(String procdef_id_, int sort_num_) {
		return reConfDao_.listReConf(procdef_id_, sort_num_);
	}
	@Override
	public int getIsManager(String procdef_id_,String manager) {
		return reProcdefDao_.getIsManager(procdef_id_, manager);
	}
	
	public void setReProcdefDao_(ReProcdefDao reProcdefDao_) {
		this.reProcdefDao_ = reProcdefDao_;
	}
	public void setReConfDao_(ReConfDao reConfDao_) {
		this.reConfDao_ = reConfDao_;
	}
	
}
