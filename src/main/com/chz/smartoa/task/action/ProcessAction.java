package com.chz.smartoa.task.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.task.pojo.ReConf;
import com.chz.smartoa.task.pojo.ReProcdef;
import com.chz.smartoa.task.service.RepositoryService;

public class ProcessAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ProcessAction.class);
	
	private String procdefId;
	private String reConfsStr;
	
	//流程配置行号
	private String lineNos = "";
	
	private int status;
	//流程定义
	private ReProcdef reProcdef = new ReProcdef();
	//流程定义列表
	private List<ReProcdef> reProcdefs;
	
	private List<DictionaryConfig> userSelect;
	private List<DictionaryConfig> roleSelect;
	
	private List<ReConf> reConfs = new ArrayList<ReConf>();
	
	private RepositoryService repositoryService;
	
	//操作日志
	private OperateLogBiz operateLogBiz;
	
	public String listPage(){
		return "list";
	}
	
	/**
	 * 流程列表
	 * @return
	 */
	public String list() {
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		setPagination(reProcdef);
		// 得到分页结果
		reProcdefs = repositoryService.listReProcdef(reProcdef);
		// 得到总记录数, 调用基类方法设置上一页下一页等属性（firstPage,nextPage等）		
		dataGrid = new DataGrid(repositoryService.listReProcdefCount(reProcdef),reProcdefs);
		return DATA_GRID;
	}
	
	
	/**
	 * 流程新增
	 * @return
	 */
	public String insert() {
		if(reProcdef!=null&&!StringUtils.isEmpty(reProcdef.getProcdef_id_())){
			return update();
		}
		int cnt = repositoryService.findReProcdefCnt(reProcdef.getName_());
		if(cnt>0){
			operateResult = new OperateResult(-1,"该流程名称已存在,请修改流程名称后提交！");
			return OPER_RESULT;
		}
		reProcdef.setVersion_(String.valueOf(++cnt));
		String id = repositoryService.insertReProcdef(reProcdef);
		operateResult = new OperateResult(1,"新增流程定义成功");
		operateLogBiz.info(OperateLogType.PROCESS_MANAGE, id, "流程："+reProcdef.getName_(),"新增成功");
		return OPER_RESULT;
	}
	
	/**
	 * 新增流程明细
	 * @return
	 */
	public String insertReconf() {
		if(isdo != 1){ //新增页面
			reProcdef = repositoryService.findReProcdef(reProcdef.getProcdef_id_());
			reConfs = repositoryService.listReConf(reProcdef.getProcdef_id_());
			
			if(reConfs.size()==0){
				reConfs = null;
				lineNos = "1,";
			}else{
				for (int i = 1; i <= reConfs.size(); i++) {
					lineNos += i+",";
				}
			}
			return "beforeInsertReconf";
		}
		List<ReConf> confs = putReconfToList();
		if(confs.size() > 0){
			repositoryService.insertReConfs(confs,procdefId);
		}
		operateResult = new OperateResult(1,"流程配置成功！");
		reProcdef = repositoryService.findReProcdef(procdefId);
		operateLogBiz.info(OperateLogType.PROCESS_MANAGE, procdefId, "编辑流程配置"+reProcdef.getName_(),"配置流程成功");
		return OPER_RESULT;
	}

	public List<ReConf> putReconfToList(){
		List<ReConf> list = new ArrayList<ReConf>();
		Map<String, String[]> parasMap = (Map<String, String[]>)super.getHttpServletRequest().getParameterMap();
		try {
			String[]  nos = lineNos.split(",");
			for (String i : nos) {
				if(i==null||"".equals(i.trim())){
					continue;
				}
				ReConf conf = new ReConf();
				conf.setSort_num_(Integer.parseInt(parasMap.get("sort_num_"+i)[0]));
				conf.setTask_desc_(parasMap.get("task_desc_"+i)[0]);
				conf.setAction_type_(Integer.parseInt(parasMap.get("action_type_"+i)[0]));
				conf.setAction_obj_type_(Integer.parseInt(parasMap.get("action_obj_type_"+i)[0]));
				conf.setAction_obj_(parasMap.get("action_obj_"+i)[0]);
				//转办
				if (parasMap.containsKey("is_turn_"+i)) {
					conf.setIs_turn_(Integer.parseInt(parasMap.get("is_turn_"+i)[0].trim()));
				}else{
					conf.setIs_turn_(0);
				}
				//征询
				if (parasMap.containsKey("is_ask_"+i)) {
					conf.setIs_ask_(Integer.parseInt(parasMap.get("is_ask_"+i)[0].trim()));
				}else{
					conf.setIs_ask_(0);
				}
				//过期时间
				conf.setExpiry_days_(Integer.parseInt(parasMap.get("expiry_days_"+i)[0]));
				//到达提醒
				if (parasMap.containsKey("arrive_remind_"+i)) {
					conf.setArrive_remind_(Integer.parseInt(parasMap.get("arrive_remind_"+i)[0].trim()));
				}else{
					conf.setArrive_remind_(0);
				}
				//过期提醒
				if (parasMap.containsKey("expiry_remind_"+i)) {
					conf.setExpiry_remind_(Integer.parseInt(parasMap.get("expiry_remind_"+i)[0].trim()));
				}else{
					conf.setExpiry_remind_(0);
				}
				list.add(conf);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return list;
	}
	
	/**
	 * 流程修改
	 * @return
	 */
	public String update() {
		repositoryService.updateReProcdef(reProcdef);
		operateResult = new OperateResult(1,"该流程修改成功！");
		operateLogBiz.info(OperateLogType.PROCESS_MANAGE, reProcdef.getProcdef_id_(), "修改流程定义:"+reProcdef.getName_(),"修改成功");
		return OPER_RESULT;
	}
	
	/**
	 * 修改流程状态
	 * @return
	 */
	public String updateStatus() {
		repositoryService.updateReProcdefStatus(procdefId,status);
		reProcdef = repositoryService.findReProcdef(procdefId);
		operateLogBiz.info(OperateLogType.PROCESS_MANAGE, procdefId, "修改流程:"+reProcdef.getName_()+",状态为："+status,"修改流程状态成功");
		return OPER_RESULT;
	}
	
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}
	public List<DictionaryConfig> getUserSelect() {
		return userSelect;
	}
	public List<DictionaryConfig> getRoleSelect() {
		return roleSelect;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ReProcdef getReProcdef() {
		return reProcdef;
	}
	public void setReProcdef(ReProcdef reProcdef) {
		this.reProcdef = reProcdef;
	}
	public List<ReProcdef> getReProcdefs() {
		return reProcdefs;
	}
	public List<ReConf> getReConfs() {
		return reConfs;
	}
	public void setReConfsStr(String reConfsStr) {
		this.reConfsStr = reConfsStr;
	}
	public String getLineNos() {
		return lineNos;
	}
	public void setLineNos(String lineNos) {
		this.lineNos = lineNos;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
}
