package com.chz.smartoa.dynamicForm.pojo;

import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;
import com.chz.smartoa.dynamicForm.export.ExpColumn;

/**
 * 员工工资总表
 * @author lenovo
 * @time 15:17 2015/5/2
 */
public class StaffWages extends BaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String id;						//
	@ExpColumn("标题")private String topic;					//标题
	@ExpColumn("发放月份")private String month;					//发放月份
	private Integer status;
	private String templateId;				//模板
	@ExpColumn("基本工资合计")private double payBase;               	//基本工资
	@ExpColumn("考勤补贴合计")private double payAttendance;     		//考勤补贴
	@ExpColumn("养老(个人)合计")private double pensionIndividual;       //养老(个人)
	@ExpColumn("养老(单位)合计")private double pensionUnits;            //养老(单位)
	@ExpColumn("住房(个人)合计")private double housingIndividual;       //住房(个人)
	@ExpColumn("住房(单位)合计")private double housingUnits;            //住房(单位)
	@ExpColumn("医疗(个人)合计")private double medicalPersonal;         //医疗(个人)
	@ExpColumn("医疗(单位)合计")private double medicalUnits;            //医疗(单位)
	@ExpColumn("工伤(个人)合计")private double injuryPersonal;          //工伤(个人)
	@ExpColumn("工伤(单位)合计")private double injuryUnits;             //工伤(单位)
	@ExpColumn("失业(个人)合计")private double unemploymentPersonal;    //失业(个人)
	@ExpColumn("失业(单位)合计")private double unemploymentUnits;       //失业(单位)
	@ExpColumn("生育(个人)合计")private double fertilityPersonal;       //生育(个人)
	@ExpColumn("生育(单位)合计")private double fertilityUnits;          //生育(单位)
	@ExpColumn("所得税总和合计")private double incomeTax;               	//所得税
	@ExpColumn("考勤扣款合计")private double attendanceChargeback;    	//考勤扣款
	@ExpColumn("实发工资合计")private double realWages;               	//实发工资
	private String remark;                  //备注
	@ExpColumn("外勤费用合计")private double fieldCost;               //外勤费用
    @ExpColumn("现金工资合计")private double moneyWages;              //现金工资
	@ExpColumn("人力成本合计")private double payHrcost;				//人力成本
	@ExpColumn("填写人")private String createUser;				//
    @ExpColumn("填写时间")private Date createDate;                //
    @ExpColumn("更新人")private String updateUser;              //
    @ExpColumn("更新时间")private Date updateDate;                //
    
    private String createDateFrom;
    private String createDateTo;
    
    public StaffWages(){}
    
    public StaffWages(String id){
    	this.id = id;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(String createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public String getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(String createDateTo) {
		this.createDateTo = createDateTo;
	}
}
