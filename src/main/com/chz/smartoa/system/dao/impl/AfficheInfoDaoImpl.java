package com.chz.smartoa.system.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.chz.smartoa.system.dao.AfficheInfoDao;
import com.chz.smartoa.system.pojo.AfficheInfo;


public class AfficheInfoDaoImpl extends SqlMapClientDaoSupport implements AfficheInfoDao{
	

	@Override
	public void insertAffiche(AfficheInfo afficheInfo) {
		getSqlMapClientTemplate().insert("ache_insertAche", afficheInfo);
	}

	@Override
	public void deleteAffiche(String[]ids) {
		if(ids != null && ids.length >= 1){
			for(int i=0;i<ids.length;i++){
				getSqlMapClientTemplate().delete("ache_deleteAche",Long.valueOf(ids[i]));
			}
		}
		
	}

	@Override
	public void updateAffiche(AfficheInfo afficheInfo) {
		getSqlMapClientTemplate().update("ache_updateAche", afficheInfo);
	}

	@Override
	public AfficheInfo getAfficheById(Long id) {
		AfficheInfo afficheInfo = (AfficheInfo) getSqlMapClientTemplate().queryForObject("ache_findAcheById", id);
		return afficheInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AfficheInfo> getAfficheByContion(AfficheInfo afficheInfo,int start,int limit) {
		List<AfficheInfo> aList = (List<AfficheInfo>)getSqlMapClientTemplate().queryForList("ache_findAcheByQueryCondition", afficheInfo,start, limit);
		return aList;
	}
	
	@Override
	public Integer getAcheListcount(AfficheInfo afficheInfo) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("ache_listAcheCount", afficheInfo);
		return count;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfficheInfo> getAfficheForNew() {
		List<AfficheInfo> aList = (List<AfficheInfo>)getSqlMapClientTemplate().queryForList("ache_getAcheListForNew");
		return aList;
	}

	

}
