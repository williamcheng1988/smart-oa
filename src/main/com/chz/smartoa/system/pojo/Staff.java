package com.chz.smartoa.system.pojo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chz.smartoa.common.base.BaseDomain;

/**
 * Staff对象.
 * @author huangdhui
 */
public class Staff extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	public static String STAFF_STATUS_INACTIVE = "0";// 待审核
	public static String STAFF_STATUS_NORMAL = "1"; // 正常
	public static String STAFF_STATUS_LOCK = "2"; // 锁定
	public static String STAFF_STATUS_LOGOUT = "3"; // 锁定
	
	
	/**
	 * 登录名.
	 */
	private String loginName;
	/**
	 * 组织ID.
	 */
	private String departmentId;
	/**
	 * 成员姓名.
	 */
	private String realName;
	/**
	 * .
	 */
	private Integer payAttendance;
	/**
	 * .
	 */
	private Integer payHour;
	/**
	 * 成员状态：0：待激活，1：正常，2：锁定.
	 */
	private String status;
	/**
	 * 邮编.
	 */
	private String zip;
	/**
	 * 密码连续输入错误的次数.
	 */
	private String pwdErrCount;
	/**
	 * 成员创建者.
	 */
	private String createUser;
	/**
	 * 性别：MALE-男性；FEMALE-女性；UNKNOW-未知.
	 */
	private String sex;
	/**
	 * 邮件地址.
	 */
	private String email;
	/**
	 * 密码（经过加密）.
	 */
	private String password;
	/**
	 * 成员帐号过期时间.
	 */
	private String expireDate;
	/**
	 * 学历.
	 */
	private String lastDegree;
	/**
	 * .
	 */
	private Integer payBase;
	/**
	 * .
	 */
	private Integer payHrcost;
	/**
	 * 地址.
	 */
	private String address;
	
	/**
	 * 成员最后修改时间.
	 */
	private String lastUpdateDate;
	/**
	 * 电话.
	 */
	private String telephone;
	/**
	 * .
	 */
	private String lockDate;
	/**
	 * .
	 */
	private String passwordExpireDate;
	/**
	 * 手机号码.
	 */
	private String mobile;
	/**
	 * 专业.
	 */
	private String speciality;
	/**
	 * 成员创建时间.
	 */
	private String createDate;
	
	/**
	 * 权限
	 */
	private Permission permission;	

	/**
	 * 所属组织
	 */
	private Department department;
	
	private String id;
	private String stateOpert;
	
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDepartmentId() {
		if(StringUtils.isEmpty(departmentId)){
			 return "root";
		}else{
			return this.departmentId;
		}
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getPayAttendance() {
		return this.payAttendance;
	}

	public void setPayAttendance(Integer payAttendance) {
		this.payAttendance = payAttendance;
	}

	public Integer getPayHour() {
		return this.payHour;
	}

	public void setPayHour(Integer payHour) {
		this.payHour = payHour;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPwdErrCount() {
		return pwdErrCount;
	}

	public void setPwdErrCount(String pwdErrCount) {
		this.pwdErrCount = pwdErrCount;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		if(password != null){
			this.password = password.trim();
		}
	}

	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getLastDegree() {
		return this.lastDegree;
	}

	public void setLastDegree(String lastDegree) {
		this.lastDegree = lastDegree;
	}

	public Integer getPayBase() {
		return this.payBase;
	}

	public void setPayBase(Integer payBase) {
		this.payBase = payBase;
	}

	public Integer getPayHrcost() {
		return this.payHrcost;
	}

	public void setPayHrcost(Integer payHrcost) {
		this.payHrcost = payHrcost;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLockDate() {
		return this.lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}

	public String getPasswordExpireDate() {
		return this.passwordExpireDate;
	}

	public void setPasswordExpireDate(String passwordExpireDate) {
		this.passwordExpireDate = passwordExpireDate;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Permission getPermission() {
		if(permission == null){
			return new Permission();
		}
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getId() {
		return loginName;
	}
	public String getStateOpert() {
		return status+","+loginName;
	}
	public void setStateOpert(String stateOpert) {
		this.stateOpert = stateOpert;
	}

	@Override
	public String toString() {
		return "Staff [loginName=" + loginName + ", departmentId="
				+ departmentId + ", realName=" + realName + ", payAttendance="
				+ payAttendance + ", payHour=" + payHour + ", status=" + status
				+ ", zip=" + zip + ", pwdErrCount=" + pwdErrCount
				+ ", createUser=" + createUser + ", sex=" + sex + ", email="
				+ email + ", expireDate="
				+ expireDate + ", lastDegree=" + lastDegree + ", payBase="
				+ payBase + ", payHrcost=" + payHrcost + ", address=" + address
				+ ", lastUpdateDate=" + lastUpdateDate + ", telephone="
				+ telephone + ", lockDate=" + lockDate
				+ ", passwordExpireDate=" + passwordExpireDate + ", mobile="
				+ mobile + ", speciality=" + speciality + ", createDate="
				+ createDate + ", department=" + department + ", roles="
				+ roles + "]";
	}

}
