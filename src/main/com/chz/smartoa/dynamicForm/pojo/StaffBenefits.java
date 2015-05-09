package com.chz.smartoa.dynamicForm.pojo;

import com.chz.smartoa.common.base.BaseDomain;
import com.chz.smartoa.dynamicForm.export.ExpColumn;

/**
 * 员工工资明细表
 * @author lenovo
 * @time 15:17 2015/5/2
 */
public class StaffBenefits extends BaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;						//
	private String uuid;					//
	@ExpColumn("用户名")private String loginName;				//
	@ExpColumn("姓名")private String realName;				//真实姓名
	@ExpColumn("基本工资")private double payBase;               	//基本工资
	@ExpColumn("考勤补贴")private double payAttendance;     		//考勤补贴
	@ExpColumn("养老(个人)")private double pensionIndividual;       //养老(个人)
	@ExpColumn("养老(单位)")private double pensionUnits;            //养老(单位)
	@ExpColumn("住房(个人)")private double housingIndividual;       //住房(个人)
	@ExpColumn("住房(单位)")private double housingUnits;            //住房(单位)
	@ExpColumn("医疗(个人)")private double medicalPersonal;         //医疗(个人)
	@ExpColumn("医疗(单位)")private double medicalUnits;            //医疗(单位)
	@ExpColumn("工伤(个人)")private double injuryPersonal;          //工伤(个人)
	@ExpColumn("工伤(单位)")private double injuryUnits;             //工伤(单位)
	@ExpColumn("失业(个人)")private double unemploymentPersonal;    //失业(个人)
	@ExpColumn("失业(单位)")private double unemploymentUnits;       //失业(单位)
	@ExpColumn("生育(个人)")private double fertilityPersonal;       //生育(个人)
	@ExpColumn("生育(单位)")private double fertilityUnits;          //生育(单位)
	@ExpColumn("所得税")private double incomeTax;               //所得税
	@ExpColumn("考勤扣款")private double attendanceChargeback;    //考勤扣款
	@ExpColumn("实发工资")private double realWages;               //实发工资
	@ExpColumn("备注")private String remark;                  //备注
	@ExpColumn("外勤费用")private double fieldCost;               //外勤费用
    @ExpColumn("现金工资")private double moneyWages;              //现金工资
	@ExpColumn("人力成本")private double payHrcost;				//人力成本
    
    public StaffBenefits(){}
    
    public StaffBenefits(String id){
    	this.id = id;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public double getPayBase() {
		return payBase;
	}
	public void setPayBase(double payBase) {
		this.payBase = payBase;
	}
	public double getPayAttendance() {
		return payAttendance;
	}
	public void setPayAttendance(double payAttendance) {
		this.payAttendance = payAttendance;
	}
	public double getPensionIndividual() {
		return pensionIndividual;
	}
	public void setPensionIndividual(double pensionIndividual) {
		this.pensionIndividual = pensionIndividual;
	}
	public double getPensionUnits() {
		return pensionUnits;
	}
	public void setPensionUnits(double pensionUnits) {
		this.pensionUnits = pensionUnits;
	}
	public double getHousingIndividual() {
		return housingIndividual;
	}
	public void setHousingIndividual(double housingIndividual) {
		this.housingIndividual = housingIndividual;
	}
	public double getHousingUnits() {
		return housingUnits;
	}
	public void setHousingUnits(double housingUnits) {
		this.housingUnits = housingUnits;
	}
	public double getMedicalPersonal() {
		return medicalPersonal;
	}
	public void setMedicalPersonal(double medicalPersonal) {
		this.medicalPersonal = medicalPersonal;
	}
	public double getMedicalUnits() {
		return medicalUnits;
	}
	public void setMedicalUnits(double medicalUnits) {
		this.medicalUnits = medicalUnits;
	}
	public double getInjuryPersonal() {
		return injuryPersonal;
	}
	public void setInjuryPersonal(double injuryPersonal) {
		this.injuryPersonal = injuryPersonal;
	}
	public double getInjuryUnits() {
		return injuryUnits;
	}
	public void setInjuryUnits(double injuryUnits) {
		this.injuryUnits = injuryUnits;
	}
	public double getUnemploymentPersonal() {
		return unemploymentPersonal;
	}
	public void setUnemploymentPersonal(double unemploymentPersonal) {
		this.unemploymentPersonal = unemploymentPersonal;
	}
	public double getUnemploymentUnits() {
		return unemploymentUnits;
	}
	public void setUnemploymentUnits(double unemploymentUnits) {
		this.unemploymentUnits = unemploymentUnits;
	}
	public double getFertilityPersonal() {
		return fertilityPersonal;
	}
	public void setFertilityPersonal(double fertilityPersonal) {
		this.fertilityPersonal = fertilityPersonal;
	}
	public double getFertilityUnits() {
		return fertilityUnits;
	}
	public void setFertilityUnits(double fertilityUnits) {
		this.fertilityUnits = fertilityUnits;
	}
	public double getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(double incomeTax) {
		this.incomeTax = incomeTax;
	}
	public double getAttendanceChargeback() {
		return attendanceChargeback;
	}
	public void setAttendanceChargeback(double attendanceChargeback) {
		this.attendanceChargeback = attendanceChargeback;
	}
	public double getRealWages() {
		return realWages;
	}
	public void setRealWages(double realWages) {
		this.realWages = realWages;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getFieldCost() {
		return fieldCost;
	}
	public void setFieldCost(double fieldCost) {
		this.fieldCost = fieldCost;
	}
	public double getMoneyWages() {
		return moneyWages;
	}
	public void setMoneyWages(double moneyWages) {
		this.moneyWages = moneyWages;
	}
	public double getPayHrcost() {
		return payHrcost;
	}
	public void setPayHrcost(double payHrcost) {
		this.payHrcost = payHrcost;
	}
}
