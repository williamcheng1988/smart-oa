package com.chz.smartoa.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.dynamicForm.pojo.NameValPair;
import com.chz.smartoa.system.dao.DictionaryConfigDao;
import com.chz.smartoa.system.pojo.DictionaryConfig;

/**
 * 
 * @author Zhao
 *
 */
public class DictionaryConfigDaoImpl extends SqlMapClientDaoSupport implements DictionaryConfigDao{
	
	private static final Logger logger = Logger.getLogger(DictionaryConfigDaoImpl.class);

	@Override
	public void insertDictionaryConfig(DictionaryConfig cfg) {
		getSqlMapClientTemplate().insert("dictionarytCfg_insertDictionarytCfg", cfg);
	}


	@Override
	public void updateDictionaryConfig(DictionaryConfig cfg) {
		getSqlMapClientTemplate().update("dictionarytCfg_updateDictionarytCfg", cfg);
	}

	@Override
	public DictionaryConfig findDictionarytCfgById(Integer id) {
		DictionaryConfig cfg = (DictionaryConfig) getSqlMapClientTemplate().queryForObject("dictionarytCfg_findDictionarytCfgById", id);
		if (logger.isDebugEnabled()) {
        	logger.debug("findDictionarytCfgById(Integer), [" + id + "]");
		}
		return cfg;
	}

	@Override
	public DictionaryConfig findDictionarytCfgByKey(String key) {
		DictionaryConfig cfg = (DictionaryConfig) getSqlMapClientTemplate().queryForObject("dictionarytCfg_findDictionarytCfgByKey", key);
		if (logger.isDebugEnabled()) {
        	logger.debug("findDictionarytCfgById(String), [" + key + "]");
		}
		return cfg;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryConfig> findDictionarytCfgByParentId(Integer parentId) {
		List<DictionaryConfig> cfgList = (List<DictionaryConfig>)getSqlMapClientTemplate().queryForList("dictionarytCfg_findDictionarytCfgByParentId", parentId);
		return cfgList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryConfig> findCfgByParentIdForIsvalid(Integer parentId) {
		List<DictionaryConfig> cfgList = (List<DictionaryConfig>)getSqlMapClientTemplate().queryForList("dictionarytCfg_findCfgByParentIdForValid", parentId);
		return cfgList;
	}
	

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<DictionaryConfig> findDictionaryByKey(String filetypeKey) {
		List<DictionaryConfig> dcList = null;
		DictionaryConfig dc = this.findDictionarytCfgByKey(filetypeKey);
		if(dc != null){
			if(1 == dc.getConfigType()){
				// '手动配置'查询子项列表
				dcList = this.findCfgByParentIdForIsvalid(dc.getId()); 
			}else if(2 == dc.getConfigType()){
				// '自定义sql'查询列表
				dcList = (List<DictionaryConfig>)getSqlMapClientTemplate().queryForList("dictionarytCfg_findDictionarytCfgBySql",dc.getSqlValue());
			}else if(3 == dc.getConfigType()){
				List<DictionaryConfig> parentList = this.findCfgByParentIdForIsvalid(dc.getId()); 
				if(parentList != null && parentList.size() >= 1){
					dcList = new ArrayList<DictionaryConfig>();
					for(int i=0;i<parentList.size();i++){
						DictionaryConfig cg = parentList.get(i);
						dcList.add(cg);
						List<DictionaryConfig> sonList = this.findCfgByParentIdForIsvalid(cg.getId()); 
						if(sonList != null && sonList.size() >= 1){
							for(int j=0;j<sonList.size();j++){
								dcList.add(sonList.get(j));
							}
						}
					}
				}
			}else{
				dcList = new ArrayList<DictionaryConfig>();
				dcList.add(dc);
			}
		}
		return dcList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryConfig> getDictionaryByDicType(Integer dicType) {
		DictionaryConfig cg = new DictionaryConfig();
		cg.setDicType(dicType);
		List<DictionaryConfig> cfgList = (List<DictionaryConfig>)getSqlMapClientTemplate().queryForList("dictionarytCfg_findDictionarytCfgByDicType", cg);
		return cfgList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NameValPair> listDictionaryFirstLevel() {
		return (List<NameValPair>)getSqlMapClientTemplate().queryForList("dictionarytCfg_listNameValue");
	}


	@Override
	public int getDictionarytAllCount() {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("dictionarytCfg_getDictionarytAllCount");
		return count;
	}

}
