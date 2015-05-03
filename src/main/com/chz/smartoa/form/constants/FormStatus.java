/**
 * 表单/模板 状态
 * @author william
 * 20:46 2014/10/6
 */
package com.chz.smartoa.form.constants;

public enum FormStatus {
	
	NORMAL(0,"正常"),OVERDUE(1,"过期"),DRAFT(2,"草稿"),SUSPEND(3,"禁用"),NONE(1000,"不存在");
	
	public int value;
	public String name;
	private FormStatus(int value,String name){
		this.value=value;
		this.name = name;
	}
	private FormStatus(int value){
		this.value=value;
	}
	/**
	 * 普通方法 ： 获取枚举名称
	 * @param value
	 * @return name
	 */
	public static String getName(int value){
		String name = FormStatus.NONE.name;
		for (FormStatus f : FormStatus.values()) {
			if(f.value==value){
				name = f.name;
				break;
			}
		}
		return name;
	}
}
