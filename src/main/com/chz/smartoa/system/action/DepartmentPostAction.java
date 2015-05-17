package com.chz.smartoa.system.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.pojo.DepartmentPostStaffs;
import com.chz.smartoa.system.pojo.Post;
import com.chz.smartoa.system.service.DepartmentPostBiz;



@Controller
public class DepartmentPostAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DepartmentPostAction.class);
	
	private DepartmentPostBiz departmentPostBiz;
	
	private List<DepartmentPostStaffs> dpsList;
	
	private DepartmentPostStaffs dps;
	
	private Post pt;
	
	private String msg;
	
	private String jsonStr;
	
	private String departmentId;
	
	private String departmentName;
	
	
	
	@SuppressWarnings("rawtypes")
	public String queryDepartmentPost(){
		try {
			if(StringUtils.isNotEmpty(departmentId)){
				dpsList = departmentPostBiz.findListByDepartmentId(departmentId);
				dataGrid = new DataGrid(1,dpsList);
			}else{
				dataGrid = new DataGrid(0,new ArrayList());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	
	

	public DepartmentPostBiz getDepartmentPostBiz() {
		return departmentPostBiz;
	}
	public void setDepartmentPostBiz(DepartmentPostBiz departmentPostBiz) {
		this.departmentPostBiz = departmentPostBiz;
	}

	public List<DepartmentPostStaffs> getDpsList() {
		return dpsList;
	}
	public void setDpsList(List<DepartmentPostStaffs> dpsList) {
		this.dpsList = dpsList;
	}

	public DepartmentPostStaffs getDps() {
		return dps;
	}
	public void setDps(DepartmentPostStaffs dps) {
		this.dps = dps;
	}

	public Post getPt() {
		return pt;
	}
	public void setPt(Post pt) {
		this.pt = pt;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	

}
