package com.chz.smartoa.dynamicForm.export.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedExpConstants {
	
	public static Map<String,String> TRAVELEXPENSE = new LinkedHashMap<String,String>();
	public static Map<String,String> WEEKLYPUBLICATION = new LinkedHashMap<String,String>();
	
	static{
		TRAVELEXPENSE.put("xf_f_fycdxm", "费用承担项目");
		TRAVELEXPENSE.put("xf_f_fixedName_xmjl", "项目经理");
		TRAVELEXPENSE.put("xf_f_skr", "收款人");
		TRAVELEXPENSE.put("xf_f_skzh", "收款帐号");
		TRAVELEXPENSE.put("xf_f_zj", "职级");
		TRAVELEXPENSE.put("xf_f_BeginDate~dy_clone", "前往日期");
		TRAVELEXPENSE.put("xf_f_EndDate~dy_clone", "返回日期");
		TRAVELEXPENSE.put("xf_f_LeavePlace~dy_clone", "出发地");
		TRAVELEXPENSE.put("xf_f_ArrivePlace~dy_clone", "到达地");
		TRAVELEXPENSE.put("xf_f_Days~dy_clone", "天数");
		TRAVELEXPENSE.put("xf_f_Fee1~dy_clone", "机车船费");
		TRAVELEXPENSE.put("xf_f_Fee2~dy_clone", "机场/码头/火车站往返费");
		TRAVELEXPENSE.put("xf_f_Fee3~dy_clone", "市内交通");
		TRAVELEXPENSE.put("xf_f_Fee4~dy_clone", "住宿费");
		TRAVELEXPENSE.put("xf_f_Fee5~dy_clone", "伙食补助");
		TRAVELEXPENSE.put("xf_f_Fee6~dy_clone", "交通补助");
		TRAVELEXPENSE.put("xf_f_Fee7~dy_clone", "住宿补助");
		TRAVELEXPENSE.put("xf_f_Fee8~dy_clone", "其他");
		TRAVELEXPENSE.put("xf_f_Total~dy_clone", "合计");
		
		WEEKLYPUBLICATION.put("xf_f_xmqj","周报期间");
		WEEKLYPUBLICATION.put("xf_f_gszbbt","工时周报标题");
		WEEKLYPUBLICATION.put("xf_f_zyjb","专业级别");
		WEEKLYPUBLICATION.put("xf_f_bzgzcg","本周工作成果");
		WEEKLYPUBLICATION.put("xf_f_xzgzjh","下周工作计划	");	
		WEEKLYPUBLICATION.put("xf_f_workTopic~dy_clone","工作事项");
		WEEKLYPUBLICATION.put("xf_f_Day1~dy_clone","周一");
		WEEKLYPUBLICATION.put("xf_f_Day2~dy_clone","周二");
		WEEKLYPUBLICATION.put("xf_f_Day3~dy_clone","周三");
		WEEKLYPUBLICATION.put("xf_f_Day4~dy_clone","周四");
		WEEKLYPUBLICATION.put("xf_f_Day5~dy_clone","周五");
		WEEKLYPUBLICATION.put("xf_f_Day6~dy_clone","周六");
		WEEKLYPUBLICATION.put("xf_f_Day7~dy_clone","周日");
		WEEKLYPUBLICATION.put("xf_f_Total~dy_clone","合计");
		WEEKLYPUBLICATION.put("xf_f_Fee~dy_clone","工时费用");
		WEEKLYPUBLICATION.put("xf_f_cusCode~dy_clone","客户简称");
	}
}
