package com.chz.smartoa.delegation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.chz.smartoa.delegation.dao.DelegationDao;
import com.chz.smartoa.delegation.dao.DelegationLogDao;
import com.chz.smartoa.delegation.pojo.Delegation;
import com.chz.smartoa.delegation.pojo.DelegationLog;
import com.chz.smartoa.delegation.pojo.DelegationLogVo;
import com.chz.smartoa.delegation.service.DelegationBiz;
import com.chz.smartoa.task.service.RuntimeService;

/**
 * 授权管理
 * @author wesson
 */
public class DelegationBizImpl implements DelegationBiz{
	
	private DelegationDao delegationDao;
	private DelegationLogDao delegationLogDao;
	
	private RuntimeService runtimeService;

	@Override
	public void insertDelegation(Delegation delegation)
			throws DataAccessException {
		if(delegation.getStatus() == null){
			delegation.setStatus(1);
		}
		delegationDao.insertDelegation(delegation);
		
		//如果时间为当天，刚将当前所有待办任务授权给被授权用户
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sf.format(Calendar.getInstance().getTime());
		if(today.equals(delegation.getStartDate())){
			runtimeService.delegation(delegation.getFromUser(), delegation.getToUser());
		}
	}

	@Override
	public void deleteDelegation(String delegationId) throws DataAccessException,ParseException {
		String[] array = null;
		if(StringUtils.isNotEmpty(delegationId))
			array = delegationId.split(",");
		else
			return;
		for (String id : array) {
			Delegation d = delegationDao.findDelegation(id);
			d.setStatus(0);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = Calendar.getInstance().getTime();
			if(now.before(sf.parse(d.getStartDate()+" 00:00:00"))){
				//删除委托
				delegationDao.deleteDelegation(id);
				return;
			}
			if(now.after(sf.parse(d.getStartDate()+" 00:00:00")) && now.before(sf.parse(d.getEndDate()+" 23:59:59"))){
				d.setEndDate("now");
			}
			delegationDao.updateDelegation(d);
			//取消正在运行中的授权任务
			runtimeService.delegationCancel(id);
		}
	}

	@Override
	public void updateDelegation(Delegation delegation) throws DataAccessException {
		delegationDao.updateDelegation(delegation);
	}

	@Override
	public Delegation findDelegation(String delegationId) throws DataAccessException {
		return delegationDao.findDelegation(delegationId);
	}
	
	@Override
	public Delegation isExists(Delegation delegation)
			throws DataAccessException {
		Delegation param = new Delegation();
		param.setFromUser(delegation.getFromUser());
		param.setStartDate(delegation.getStartDate());
		param.setEndDate(delegation.getEndDate());
		param.setStatus(1);
		List<Delegation> list =  listDelegation(param);
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Delegation> listDelegation(Delegation delegation)
			throws DataAccessException {
		return delegationDao.listDelegation(delegation);
	}
	@Override
	public Integer listDelegationCount(Delegation delegation)
			throws DataAccessException {
		return delegationDao.listDelegationCount(delegation);
	}

	@Override
	public void insertDelegationLog(DelegationLog delegationLog)
			throws DataAccessException {
		delegationLogDao.insertDelegationLog(delegationLog);
	}

	@Override
	public List<DelegationLogVo> listDelegationLog(DelegationLog delegationLog)
			throws DataAccessException {
		return delegationLogDao.listDelegationLog(delegationLog);
	}
	@Override
	public Integer listDelegationLogCount(DelegationLog delegationLog)
			throws DataAccessException {
		return delegationLogDao.listDelegationLogCount(delegationLog);
	}
	
	@Override
	public List<DelegationLogVo> listDelegationUndoLog(
			DelegationLog delegationLog) throws DataAccessException {
		return delegationLogDao.listDelegationUndoLog(delegationLog);
	}
	
	@Override
	public Integer listDelegationUndoLogCount(DelegationLog delegationLog)
			throws DataAccessException {
		return delegationLogDao.listDelegationUndoLogCount(delegationLog);
	}

	
	public void setDelegationDao(DelegationDao delegationDao) {
		this.delegationDao = delegationDao;
	}
	public void setDelegationLogDao(DelegationLogDao delegationLogDao) {
		this.delegationLogDao = delegationLogDao;
	}
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
}
