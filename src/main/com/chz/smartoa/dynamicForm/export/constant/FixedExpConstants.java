package com.chz.smartoa.dynamicForm.export.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedExpConstants {
	
	public static Map<String,String> TRAVELEXPENSE = new LinkedHashMap<String,String>();
	public static Map<String,String> WEEKLYPUBLICATION = new LinkedHashMap<String,String>();
	
	public static Map<String,String> STAFF_BEN = new LinkedHashMap<String,String>();
	public static Map<String,String> STAFF_WAGE = new LinkedHashMap<String,String>();
	
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
		
	
    	STAFF_BEN.put("loginName","用户名");
    	STAFF_BEN.put("realName","姓名");
    	STAFF_BEN.put("payBase","基本工资合计");
    	STAFF_BEN.put("payAttendance","考勤补贴合计");
    	STAFF_BEN.put("pensionIndividual","养老(个人)");
    	STAFF_BEN.put("pensionUnits","养老(单位)");
    	STAFF_BEN.put("housingIndividual","住房(个人)");
    	STAFF_BEN.put("housingUnits","住房(单位)");
    	STAFF_BEN.put("medicalPersonal","医疗(个人)");
    	STAFF_BEN.put("medicalUnits","医疗(单位)");
    	STAFF_BEN.put("injuryPersonal","工伤(个人)");
    	STAFF_BEN.put("injuryUnits","工伤(单位)");
    	STAFF_BEN.put("unemploymentPersonal","失业(个人)");
    	STAFF_BEN.put("unemploymentUnits","失业(单位)");
    	STAFF_BEN.put("fertilityPersonal","生育(个人)");
    	STAFF_BEN.put("fertilityUnits","生育(单位)");
    	STAFF_BEN.put("incomeTax","所得税总和");
    	STAFF_BEN.put("attendanceChargeback","考勤扣款");
    	STAFF_BEN.put("realWages","实发工资");
    	STAFF_BEN.put("fieldCost","外勤费用");
    	STAFF_BEN.put("moneyWages","现金工资");
    	STAFF_BEN.put("payHrcost","人力成本");
    	
    	
    	STAFF_WAGE.put("topic","标题");
    	STAFF_WAGE.put("month","发放月份");
    	STAFF_WAGE.put("payBase","基本工资合计");
    	STAFF_WAGE.put("payAttendance","考勤补贴合计");
    	STAFF_WAGE.put("pensionIndividual","养老(个人)合计");
    	STAFF_WAGE.put("pensionUnits","养老(单位)合计");
    	STAFF_WAGE.put("housingIndividual","住房(个人)合计");
    	STAFF_WAGE.put("housingUnits","住房(单位)合计");
    	STAFF_WAGE.put("medicalPersonal","医疗(个人)合计");
    	STAFF_WAGE.put("medicalUnits","医疗(单位)合计");
    	STAFF_WAGE.put("injuryPersonal","工伤(个人)合计");
    	STAFF_WAGE.put("injuryUnits","工伤(单位)合计");
    	STAFF_WAGE.put("unemploymentPersonal","失业(个人)合计");
    	STAFF_WAGE.put("unemploymentUnits","失业(单位)合计");
    	STAFF_WAGE.put("fertilityPersonal","生育(个人)合计");
    	STAFF_WAGE.put("fertilityUnits","生育(单位)合计");
    	STAFF_WAGE.put("incomeTax","所得税总和合计");
    	STAFF_WAGE.put("attendanceChargeback","考勤扣款合计");
    	STAFF_WAGE.put("realWages","实发工资合计");
    	STAFF_WAGE.put("fieldCost","外勤费用合计");
    	STAFF_WAGE.put("moneyWages","现金工资合计");
    	STAFF_WAGE.put("payHrcost","人力成本合计");
    	STAFF_WAGE.put("createUser","填写人");
    	STAFF_WAGE.put("createDate","填写时间");
    	STAFF_WAGE.put("updateUser","更新人");
    	STAFF_WAGE.put("updateDate","更新时间");
	}
}
