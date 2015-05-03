package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.dynamicForm.pojo.NameValPair;
import com.chz.smartoa.system.dao.DictionaryConfigDao;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.service.DictionaryConfigBiz;



public class DictionaryConfigBizImpl implements DictionaryConfigBiz{
	
	private DictionaryConfigDao dictionaryConfigDao;

	public DictionaryConfigDao getDictionaryConfigDao() {
		return dictionaryConfigDao;
	}
	public void setDictionaryConfigDao(DictionaryConfigDao dictionaryConfigDao) {
		this.dictionaryConfigDao = dictionaryConfigDao;
	}

	
	// 新增
	@Override
	public void insertDictionaryConfig(DictionaryConfig cfg) {
		dictionaryConfigDao.insertDictionaryConfig(cfg);
	}


	// 修改
	@Override
	public void updateDictionaryConfig(DictionaryConfig cfg) {
		dictionaryConfigDao.updateDictionaryConfig(cfg);
	}

	// 根据唯一id获取字典对应唯一数据
	@Override
	public DictionaryConfig findDictionarytCfgById(Integer id) {
		return dictionaryConfigDao.findDictionarytCfgById(id);
	}

	// 根据唯一key获取字典对应唯一数据
	@Override
	public DictionaryConfig findDictionarytCfgByKey(String key) {
		return dictionaryConfigDao.findDictionarytCfgByKey(key);
	}

	/**
	 * 根据父类ID查找子项集合(查询所有)
	 */
	@Override
	public List<DictionaryConfig> findDictionarytCfgByParentId(Integer parentId) {
		return dictionaryConfigDao.findDictionarytCfgByParentId(parentId);
	}
	
	
	/**
	 * 根据父类ID查找子项集合(只查询有效的)
	 */
	@Override
	public List<DictionaryConfig> findCfgByParentIdForIsvalid(Integer parentId) {
		return dictionaryConfigDao.findCfgByParentIdForIsvalid(parentId);
	}
	
	
	/**
	 * 根据ID获取具体对象记录，然后根据该对象的配置类型来查找字典列表数据
	 */
	@Override
	public List<DictionaryConfig> findDictionaryByKey(String filetypeKey) {
		return dictionaryConfigDao.findDictionaryByKey(filetypeKey);
	}
	
	// 根据显示类型获取‘当前节点下’存在子节点的‘节点’
	@Override
	public List<DictionaryConfig> getDictionaryByDicType(Integer dicType) {
		return dictionaryConfigDao.getDictionaryByDicType(dicType);
	}
	
	@Override
	public List<NameValPair> listDictionaryFirstLevel() {
		return dictionaryConfigDao.listDictionaryFirstLevel();
	}
	@Override
	public int getDictionarytAllCount() {
		return dictionaryConfigDao.getDictionarytAllCount();
	}
	
	
	@Override
	public List<DictionaryConfig> getDictionaryConfigAllForIsValid() {
		List<DictionaryConfig> list = this.findCfgByParentIdForIsvalid(93);
		if(list == null){
    		return null;
    	}
		for (DictionaryConfig d:list) {
			List<DictionaryConfig> subList = findCfgByParentIdForIsvalid(d.getId());
			d.setDictionaryList(subList);
		}
		return list;
	}
}
