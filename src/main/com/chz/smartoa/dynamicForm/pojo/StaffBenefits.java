package com.chz.smartoa.dynamicForm.pojo;

import java.util.Date;

import com.chz.smartoa.common.base.BaseDomain;

public class StaffBenefits extends BaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String loginName;
	private double basicWage;
	private double attendanceSubsidies;
	private double pensionIndividual;
	private double pensionUnits;
	private double housingIndividual;
	private double housingUnits;
	private double medicalPersonal;
	private double medicalUnits;
	private double injuryPersonal;
	private double injuryUnits;
	private double unemploymentPersonal;
	private double unemploymentUnits;
	private double fertilityPersonal;
	private double fertilityUnits;
	private double incomeTax;
	private double attendanceChargeback;
	private double realWages;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public double getBasicWage() {
		return basicWage;
	}
	public void setBasicWage(double basicWage) {
		this.basicWage = basicWage;
	}
	public double getAttendanceSubsidies() {
		return attendanceSubsidies;
	}
	public void setAttendanceSubsidies(double attendanceSubsidies) {
		this.attendanceSubsidies = attendanceSubsidies;
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
	}public String getCreateUser() {
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
}
