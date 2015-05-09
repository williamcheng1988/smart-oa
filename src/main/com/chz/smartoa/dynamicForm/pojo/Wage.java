package com.chz.smartoa.dynamicForm.pojo;

import java.util.List;


/**
 * 员工工资总表
 * @author lenovo
 * @time 15:17 2015/5/2
 */
public class Wage {
	
	private StaffWages total;
	
	private List<StaffBenefits> detail;

	public StaffWages getTotal() {
		return total;
	}

	public void setTotal(StaffWages total) {
		this.total = total;
	}

	public List<StaffBenefits> getDetail() {
		return detail;
	}

	public void setDetail(List<StaffBenefits> detail) {
		this.detail = detail;
	}
}
