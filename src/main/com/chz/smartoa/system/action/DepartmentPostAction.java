package com.chz.smartoa.system.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.pojo.DepartmentPostStaffs;
import com.chz.smartoa.system.pojo.Post;
import com.chz.smartoa.system.service.DepartmentBiz;
import com.chz.smartoa.system.service.DepartmentPostBiz;
import com.chz.smartoa.system.service.OperateLogBiz;



@Controller
public class DepartmentPostAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DepartmentPostAction.class);
	
	private DepartmentPostBiz departmentPostBiz;
	
	private DepartmentBiz departmentBiz;
	
	private OperateLogBiz operateLogBiz;
	
	private List<DepartmentPostStaffs> dpsList;
	
	private DepartmentPostStaffs dps;
	
	private Post pt;
	
	private String msg;
	
	private String jsonStr;
	
	private String departmentId;
	
	private String departmentName;
	
	private String postId;
	
	private String staffIds;
	
	private String mainId;
	
	
	
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
	
	
	
	public String insetDepartmentPostPage(){
		return "toAdd";
	}
	
	
	public String saveDepartmentPost(){
		try {
			DepartmentPostStaffs departmentPost = departmentPostBiz.getDepartmentPostByDepIdAndPostId(dps.getDepartmentId(), dps.getPostId());
			if(departmentPost != null){
				msg = "exist";
			}else{
				dps.setCreateUser(getLoginStaff().getCreateUser());
				departmentPostBiz.insertDepartmentPost(dps);
				operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getDepartmentId()),String.valueOf(dps.getStaffIds()), "新增用户岗位成功");
				msg ="true";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getDepartmentId()),String.valueOf(dps.getStaffIds()), "新增用户岗位失败");
			e.printStackTrace();
			msg ="false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	public String toEditPage(){
		dps = departmentPostBiz.getDepartmentPostById(Long.valueOf(mainId));
		Department department = departmentBiz.findDepartment(dps.getDepartmentId());
		if(department != null){
			departmentName = department.getDepartmentName();
		}
		postId = dps.getPostId();
		staffIds = dps.getStaffIds();
		return "toModify";
	}
	
	
	public String saveUpdate(){
		try {
			if(dps.getId() != null){
				DepartmentPostStaffs uDps = departmentPostBiz.getDepartmentPostById(dps.getId());
				if(uDps != null){
					uDps.setPostId(dps.getPostId());
					uDps.setStaffIds(dps.getStaffIds());
					departmentPostBiz.updateDepartmentPost(uDps);
					operateResult = new OperateResult(1, "岗位信息修改成功！");
					operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getId()),String.valueOf(dps.getStaffIds()), "修改用户岗位成功");
				}else{
					operateResult = new OperateResult(-1, "岗位信息修改失败！");
					operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getId()),String.valueOf(dps.getStaffIds()), "修改用户岗位失败");
				}
			}else{
				operateResult = new OperateResult(-1, "岗位信息修改失败！");
				operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getId()),String.valueOf(dps.getStaffIds()), "修改用户岗位失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			operateResult = new OperateResult(-1, "岗位信息修改失败！");
			operateLogBiz.info(OperateLogType.DEP_POST_STAFF_MANAGE, String.valueOf(dps.getId()),String.valueOf(dps.getStaffIds()), "修改用户岗位失败");
		}
		return OPER_RESULT;
	}
	

	public DepartmentPostBiz getDepartmentPostBiz() {
		return departmentPostBiz;
	}
	public void setDepartmentPostBiz(DepartmentPostBiz departmentPostBiz) {
		this.departmentPostBiz = departmentPostBiz;
	}

	public DepartmentBiz getDepartmentBiz() {
		return departmentBiz;
	}
	public void setDepartmentBiz(DepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}

	public OperateLogBiz getOperateLogBiz() {
		return operateLogBiz;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
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

	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getStaffIds() {
		return staffIds;
	}
	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	

}
