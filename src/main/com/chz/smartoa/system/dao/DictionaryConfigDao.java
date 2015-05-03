package com.chz.smartoa.system.dao;

import java.util.List;

import com.chz.smartoa.dynamicForm.pojo.NameValPair;
import com.chz.smartoa.system.pojo.DictionaryConfig;


public interface DictionaryConfigDao {

	public void insertDictionaryConfig(DictionaryConfig cfg);
	
	public void updateDictionaryConfig(DictionaryConfig cfg);
	
	public DictionaryConfig findDictionarytCfgById(Integer id);
	
	public DictionaryConfig findDictionarytCfgByKey(String key);
	
	public List<DictionaryConfig> findDictionarytCfgByParentId(Integer parentId);
	
	public List<DictionaryConfig> findCfgByParentIdForIsvalid(Integer parentId);
	
	public List<DictionaryConfig> findDictionaryByKey(String filetypeKey);
	
	public List<DictionaryConfig> getDictionaryByDicType(Integer dicType);
	
	public List<NameValPair> listDictionaryFirstLevel();
	
	public int getDictionarytAllCount();
}
