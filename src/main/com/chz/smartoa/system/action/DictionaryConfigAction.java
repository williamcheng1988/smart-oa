package com.chz.smartoa.system.action;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.base.TreeData;
import com.chz.smartoa.system.constant.LogType;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.service.DictionaryConfigBiz;
import com.chz.smartoa.system.service.OperateLogBiz;


@Controller
public class DictionaryConfigAction extends BaseAction{

	private static final long serialVersionUID = -7926348553603995177L;
	
	private String dictionaryKey = "dictionaryConfig_";
	
	private static final Logger logger = Logger.getLogger(DictionaryConfigAction.class);
	
	private DictionaryConfigBiz dictionaryConfigBiz;
	
	private OperateLogBiz operateLogBiz;
	
	private DictionaryConfig config;
	
	private List<DictionaryConfig> dictionaryList;
	
	private String fileId;
	
	private String dictionaryId;
	
	private String parentDictionaryName;
	
	private String jsonStr;
	
	private String level;     // 节点等级
	
	private String dicType;   // 显示方式
	
	private String configType;
	
	// 打印信息
	private String msg;

	
	@SuppressWarnings("rawtypes")
	public String list(){
		try {
			if(StringUtils.isNotEmpty(dictionaryId)){
				dictionaryList = dictionaryConfigBiz.findDictionarytCfgByParentId(Integer.valueOf(dictionaryId));
				dataGrid = new DataGrid(1,dictionaryList);
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	/**
	 * 字典管理主菜单
	 * @return
	 */
	public String generateTree(){
		List<TreeData> tree = new ArrayList<TreeData>();
		List<DictionaryConfig> parentList = dictionaryConfigBiz.findDictionarytCfgByParentId(LogType.MAXNODE_PARENTID);
		List<DictionaryConfig> sonList1 = null;
		for(DictionaryConfig dc : parentList){
			TreeData data = new TreeData(String.valueOf(dc.getId()),dc.getDictionaryName());
			sonList1 = dictionaryConfigBiz.findDictionarytCfgByParentId(dc.getId());
			if(sonList1 != null && sonList1.size() >= 1){
				List<TreeData> tree1 = new ArrayList<TreeData>();
				for(DictionaryConfig son1 : sonList1){
					TreeData data1 = new TreeData(String.valueOf(son1.getId()),son1.getDictionaryName());
					tree1.add(data1);
				}
				data.setChildren(tree1);
				if(data.getId().equals(dictionaryId)){
					data.setChecked(true);
				}
			}
			tree.add(data);
		}
		treeData = tree;
		return TREE_DATA;
	}

	
	/**
	 * 公共信息类字典信息(附件管理加载菜单使用)
	 * @return
	 */
	public String treeForPublic(){
		List<TreeData> tree = new ArrayList<TreeData>();
		List<DictionaryConfig> parentList = dictionaryConfigBiz.getDictionaryByDicType(Integer.valueOf(1));
		List<DictionaryConfig> sonList1 = null;
		for(DictionaryConfig dc : parentList){
			TreeData data = new TreeData(String.valueOf(dc.getId()),dc.getDictionaryName());
			sonList1 = dictionaryConfigBiz.findDictionarytCfgByParentId(dc.getId());
			if(sonList1 != null && sonList1.size() >= 1){
				List<TreeData> tree1 = new ArrayList<TreeData>();
				for(DictionaryConfig son1 : sonList1){
					TreeData data1 = new TreeData(String.valueOf(son1.getId()),son1.getDictionaryName());

					tree1.add(data1);
				}
				data.setChildren(tree1);
			}
			tree.add(data);
		}
		treeData = tree;
		return TREE_DATA;
	}
	
	
	/**
	 * 技术类字典信息(附件管理加载菜单使用)
	 * @return
	 */
	public String treeForTechnology(){
		List<TreeData> tree = new ArrayList<TreeData>();
		List<DictionaryConfig> parentList = dictionaryConfigBiz.getDictionaryByDicType(Integer.valueOf(2));
		List<DictionaryConfig> sonList1 = null;
		for(DictionaryConfig dc : parentList){
			TreeData data = new TreeData(String.valueOf(dc.getId()),dc.getDictionaryName());
			sonList1 = dictionaryConfigBiz.findDictionarytCfgByParentId(dc.getId());
			if(sonList1 != null && sonList1.size() >= 1){
				List<TreeData> tree1 = new ArrayList<TreeData>();
				for(DictionaryConfig son1 : sonList1){
					TreeData data1 = new TreeData(String.valueOf(son1.getId()),son1.getDictionaryName());
					tree1.add(data1);
				}
				data.setChildren(tree1);
			}
			tree.add(data);
		}
		treeData = tree;
		return TREE_DATA;
	}
	

	/**
	 * 保存字典
	 * @return
	 */
	public String saveInsert(){
		try {
			int order_no = 1;
			List<DictionaryConfig> list = dictionaryConfigBiz.findDictionarytCfgByParentId(LogType.MAXNODE_PARENTID);   
			if(list != null && list.size() >= 1){
				order_no = list.get(list.size()-1).getOrderNo() + 1;
			}
			int allcount = dictionaryConfigBiz.getDictionarytAllCount();
			String myKey = 	dictionaryKey + (allcount + 1);
			config.setDictionaryKey(myKey);                // 自定义key
			config.setLevel(Integer.valueOf(1));           // 一级节点
			config.setConfigType(Integer.valueOf(3));      // 手动配置
			config.setOrderNo(Integer.valueOf(order_no));  // 设置存放顺序
			config.setIsValid(Integer.valueOf(1));         // 默认为有效
			config.setParentId(LogType.MAXNODE_PARENTID);  // 默认父节点为-1      
			dictionaryConfigBiz.insertDictionaryConfig(config);
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, myKey,config.getDictionaryName(), "字典新增成功");
			msg = "true";
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, null,config.getDictionaryName(), "字典新增失败");
			logger.error(e.getMessage());
			e.printStackTrace();
			msg = "false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	/**
	 * 进入添加字典项界面
	 * @return
	 */
	public String toInsertSub()
	{
		try {
			if(StringUtils.isNotEmpty(dictionaryId)){
				Integer parentId = Integer.valueOf(dictionaryId);
				config = dictionaryConfigBiz.findDictionarytCfgById(parentId);
				int levelstr = config.getLevel() + 1;
				level = String.valueOf(levelstr);
				configType = config.getConfigType().toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "toInsertSub";
	}
	
	
	/**
	 * 保存添加的子字典项
	 * @return
	 */
	public String saveSubInsert(){
		try {
			int order_no = 1;
			Integer parentId = Integer.valueOf(dictionaryId);
			List<DictionaryConfig> list = dictionaryConfigBiz.findDictionarytCfgByParentId(parentId);   
			if(list != null && list.size() >= 1){
				order_no = list.get(list.size()-1).getOrderNo() + 1;
			}
			int allcount = dictionaryConfigBiz.getDictionarytAllCount();
			String myKey = 	dictionaryKey + (allcount + 1);
			config.setDictionaryKey(myKey);                // 自定义key
			config.setConfigType(Integer.valueOf(configType));
			config.setLevel(Integer.valueOf(level));
			config.setOrderNo(Integer.valueOf(order_no));  // 设置存放顺序
			config.setIsValid(Integer.valueOf(1));         // 默认为有效
			config.setParentId(parentId);                  // 设置父节点
			dictionaryConfigBiz.insertDictionaryConfig(config);
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, myKey,config.getDictionaryName(), "字典项新增成功");
			msg ="true";
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, null,config.getDictionaryName(), "字典项新增失败");
			logger.error(e.getMessage());
			e.printStackTrace();
			msg ="false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	
	/**
	 * 进入编辑界面
	 * @return
	 */
	public String toEdit(){
		try {
			config = dictionaryConfigBiz.findDictionarytCfgById(config.getId());
			level = config.getLevel().toString();
			configType = config.getConfigType().toString();
			if("2".equals(level) && "1".equals(configType)){
				dicType = config.getDicType().toString();
			}
			Integer parentId = config.getParentId();
			if(parentId != null && parentId != -1){
				DictionaryConfig c = dictionaryConfigBiz.findDictionarytCfgById(parentId);
				if(c != null){
					dictionaryList = dictionaryConfigBiz.findDictionarytCfgByParentId(c.getParentId());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "toEdit";
	}
	
	
	/**
	 * 保存更新
	 * @return
	 */
	public String saveUpdate(){
		try {
			DictionaryConfig cfg = dictionaryConfigBiz.findDictionarytCfgById(Integer.valueOf(fileId));
			cfg.setDictionaryName(config.getDictionaryName());
			if(StringUtils.isNotEmpty(dictionaryId) && (!dictionaryId.equals("-1"))){
				//cfg.setParentId(config.getParentId());
				cfg.setIsValid(config.getIsValid());
				if(config.getDicType() != null){
					cfg.setDicType(config.getDicType());
				}
				cfg.setCompanyName(config.getCompanyName());
				cfg.setFileTypeNo(config.getFileTypeNo());
				cfg.setIsPublic(config.getIsPublic());
			}
			dictionaryConfigBiz.updateDictionaryConfig(cfg);
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, fileId,config.getDictionaryName(), "字典项修改成功");
			operateResult = new OperateResult(1, "字典项修改成功！");
		} catch (Exception e) {
			operateLogBiz.info(OperateLogType.DICTIONARY_MANAGE, fileId,config.getDictionaryName(), "字典项修改失败");
			logger.error(e.getMessage());
			e.printStackTrace();
			operateResult = new OperateResult(1, "字典项修改成功！");
		}
		return OPER_RESULT;
	}
	
	
	
	public DictionaryConfigBiz getDictionaryConfigBiz() {
		return dictionaryConfigBiz;
	}

	public void setDictionaryConfigBiz(DictionaryConfigBiz dictionaryConfigBiz) {
		this.dictionaryConfigBiz = dictionaryConfigBiz;
	}

	public OperateLogBiz getOperateLogBiz() {
		return operateLogBiz;
	}

	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	public DictionaryConfig getConfig() {
		return config;
	}

	public void setConfig(DictionaryConfig config) {
		this.config = config;
	}
	
	public List<DictionaryConfig> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<DictionaryConfig> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	public String getParentDictionaryName() {
		return parentDictionaryName;
	}

	public void setParentDictionaryName(String parentDictionaryName) {
		this.parentDictionaryName = parentDictionaryName;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

