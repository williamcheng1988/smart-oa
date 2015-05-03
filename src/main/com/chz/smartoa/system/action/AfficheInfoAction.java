package com.chz.smartoa.system.action;

import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.system.pojo.AfficheInfo;
import com.chz.smartoa.system.service.AfficheInfoBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AfficheInfoAction extends BaseAction{

	private static final long serialVersionUID = -2763673754835092430L;

	private static final Logger logger = Logger.getLogger(AfficheInfoAction.class);
	
	private AfficheInfoBiz afficheInfoBiz;
	
	private List<AfficheInfo> afList;
	
	private AfficheInfo afficheInfo;
	
	private String jsonStr;
	
	private String msg;
	
	private String ids;
	
	private String afficheTitle;
	private String afficheStatus;
	private String startDate;
	private String endDate;
	
	
	
	
	/**
	 * 查询公告数据列表
	 * @return
	 */
	public String queryList(){
		try {
			if(afficheInfo == null){
				if(StringUtils.isNotBlank(afficheTitle) || StringUtils.isNotBlank(afficheStatus) ||
				   StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
					afficheInfo	= new AfficheInfo();	
					afficheInfo.setAfficheTitle(afficheTitle);
					afficheInfo.setAfficheStatus(Integer.valueOf(afficheStatus));
					afficheInfo.setStartDate(startDate);
					afficheInfo.setEndDate(endDate);
				}
			}
			if(afficheInfo != null){
				setPagination(afficheInfo);
				afList = afficheInfoBiz.getAfficheByContion(afficheInfo, afficheInfo.getStart(), afficheInfo.getLimit());
				dataGrid = new DataGrid(afficheInfoBiz.getAcheListcount(afficheInfo),afList);
			}else{
				afficheInfo	= new AfficheInfo();	
				afficheInfo.setAfficheStatus(Integer.valueOf(1));
				afList = afficheInfoBiz.getAfficheByContion(afficheInfo, afficheInfo.getStart(), afficheInfo.getLimit());
				dataGrid = new DataGrid(afficheInfoBiz.getAcheListcount(afficheInfo),afList);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return DATA_GRID;
	}
	
	
	/**
	 * 保存新增的公告记录
	 * @return
	 */
	public String saveAdd(){
		try {
			if(afficheInfo != null){
				afficheInfo.setCreateUser(getLoginStaff().getLoginName());
				afficheInfo.setUpdateUser(getLoginStaff().getLoginName());
				afficheInfoBiz.insertAffiche(afficheInfo);
				msg = "true";
			}else{
				msg = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	/**
	 * 跳转公告记录修改界面
	 * @return
	 */
	public String editPage(){
		try {
			afficheInfo = afficheInfoBiz.getAfficheById(afficheInfo.getId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(afficheInfo != null && afficheInfo.getStartLifedate() != null){
				startDate = format.format(afficheInfo.getStartLifedate());
				endDate = format.format(afficheInfo.getEndLifedate());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "false";
		}
		return "afficheEdit";
	}
	
	
	/**
	 * 保存修改后的公告记录
	 * @return
	 */
	public String saveModify(){
		try {
			AfficheInfo updateAffiche = afficheInfoBiz.getAfficheById(afficheInfo.getId());
			updateAffiche.setAfficheTitle(afficheInfo.getAfficheTitle());
			updateAffiche.setAfficheContent(afficheInfo.getAfficheContent());
			updateAffiche.setAfficheStatus(afficheInfo.getAfficheStatus());
			updateAffiche.setStartLifedate(afficheInfo.getStartLifedate());
			updateAffiche.setEndLifedate(afficheInfo.getEndLifedate());
			updateAffiche.setUpdateUser(getLoginStaff().getLoginName());
			afficheInfoBiz.updateAffiche(updateAffiche);
			msg = "true";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "false";
		}
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	/**
	 * 删除公告记录
	 * @return
	 */
	public String delete(){
		try {
			String[]idArrs = ids.split(",");
			afficheInfoBiz.deleteAffiche(idArrs);
			msg = "true";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "false";
		}
		Gson gson = new GsonBuilder().create();
		jsonStr = gson.toJson(msg);
		return "json";
	}
	
	
	
	/**
	 * 首页显示最新4条公告
	 * @return
	 */
	public String firstPageForNewAffiche(){
		try {
			List<AfficheInfo> afficheInfoList = afficheInfoBiz.getAfficheForNew();
			if(afficheInfoList != null && afficheInfoList.size() >= 1){
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
				for(int i=0;i<afficheInfoList.size();i++){
					if(afficheInfoList.get(i).getCreateDt() != null){
						afficheInfoList.get(i).setUpdateDtStr(format.format(afficheInfoList.get(i).getUpdateDt()));
					}
				}
			}
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(afficheInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	
	/**
	 * 首页查看
	 * @return
	 */
	public String firstPgeeForSeeContent(){
		try {
			AfficheInfo af = afficheInfoBiz.getAfficheById(Long.valueOf(ids));
			Gson gson = new GsonBuilder().create();
			jsonStr = gson.toJson(af.getAfficheContent());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "json";
	}
	
	
	
	

	public AfficheInfoBiz getAfficheInfoBiz() {
		return afficheInfoBiz;
	}

	public void setAfficheInfoBiz(AfficheInfoBiz afficheInfoBiz) {
		this.afficheInfoBiz = afficheInfoBiz;
	}

	public List<AfficheInfo> getAfList() {
		return afList;
	}

	public void setAfList(List<AfficheInfo> afList) {
		this.afList = afList;
	}

	public AfficheInfo getAfficheInfo() {
		return afficheInfo;
	}

	public void setAfficheInfo(AfficheInfo afficheInfo) {
		this.afficheInfo = afficheInfo;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getAfficheTitle() {
		return afficheTitle;
	}
	public void setAfficheTitle(String afficheTitle) {
		this.afficheTitle = afficheTitle;
	}
	public String getAfficheStatus() {
		return afficheStatus;
	}
	public void setAfficheStatus(String afficheStatus) {
		this.afficheStatus = afficheStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
