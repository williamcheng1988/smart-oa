package com.chz.smartoa.system.service.impl;

import java.util.Date;
import java.util.List;

import com.chz.smartoa.system.dao.AfficheInfoDao;
import com.chz.smartoa.system.pojo.AfficheInfo;
import com.chz.smartoa.system.service.AfficheInfoBiz;

public class AfficheInfoBizImpl implements AfficheInfoBiz{
	
	private AfficheInfoDao afficheInfoDao;
	public AfficheInfoDao getAfficheInfoDao() {
		return afficheInfoDao;
	}
	public void setAfficheInfoDao(AfficheInfoDao afficheInfoDao) {
		this.afficheInfoDao = afficheInfoDao;
	}

	
	
	@Override
	public void insertAffiche(AfficheInfo afficheInfo) {
		afficheInfoDao.insertAffiche(afficheInfo);
	}

	@Override
	public void deleteAffiche(String[] ids) {
		afficheInfoDao.deleteAffiche(ids);
	}

	@Override
	public void updateAffiche(AfficheInfo afficheInfo) {
		afficheInfoDao.updateAffiche(afficheInfo);
	}

	@Override
	public AfficheInfo getAfficheById(Long id) {
		return afficheInfoDao.getAfficheById(id);
	}

	@Override
	public List<AfficheInfo> getAfficheByContion(AfficheInfo afficheInfo,int start,int limit) {
		return afficheInfoDao.getAfficheByContion(afficheInfo,start,limit);
	}
	
	
	@Override
	public Integer getAcheListcount(AfficheInfo afficheInfo) {
		return afficheInfoDao.getAcheListcount(afficheInfo);
	}
	

	@Override
	public List<AfficheInfo> getAfficheForNew() {
		return afficheInfoDao.getAfficheForNew();
	}
	

}
