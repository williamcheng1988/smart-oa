package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.pojo.AfficheInfo;

public interface AfficheInfoBiz {
	
	public void insertAffiche(AfficheInfo afficheInfo);
	
	public void deleteAffiche(String[] ids);
	
	public void updateAffiche(AfficheInfo afficheInfo);
	
	public AfficheInfo getAfficheById(Long id);
	
	public List<AfficheInfo> getAfficheByContion(AfficheInfo afficheInfo,int start,int limit);
	
	public Integer getAcheListcount(AfficheInfo afficheInfo);
	
	public List<AfficheInfo> getAfficheForNew();

}
