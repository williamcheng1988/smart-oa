package com.chz.smartoa.common.schedule.pojo;

import com.chz.smartoa.common.schedule.UnsupportedExpress;
import com.chz.smartoa.common.schedule.pojo.ExpressionItem.ITEM_TYPE;

/**
 * Field Name 	|Mandatory? 	|Allowed Values 	|Allowed Special Characters 
 * -----------------------------------------------------------------------------
 * Seconds 		|YES 			|0-59 				|, - * / 
 * Minutes 		|YES 			|0-59 				|, - * / 
 * Hours 		|YES 			|0-23 				|, - * / 
 * Day of month |YES 			|1-31 				|, - * ? / L W C 
 * Month 		|YES 			|1-12 or JAN-DEC 	|, - * / 
 * Day of week 	|YES 			|1-7 or SUN-SAT 	|, - * ? / L C # 
 * Year 		|NO 			|empty,1970-2099 	|, - * / 
 */
public class Expression {

	// 表达式项定义
	private ExpressionItem seconds = null;
	private ExpressionItem minutes = null;
	private ExpressionItem hour = null;
	private ExpressionItem day = null;
	private ExpressionItem month = null;
	private ExpressionItem week = null;
	private ExpressionItem year = null;
	
	public Expression(){
		seconds = new ExpressionItem(ITEM_TYPE.SECONDS);
		minutes = new ExpressionItem(ITEM_TYPE.MINTURE);
		hour = new ExpressionItem(ITEM_TYPE.HOUR);
		day = new ExpressionItem(ITEM_TYPE.DAY);
		month = new ExpressionItem(ITEM_TYPE.MONTH);
		week = new ExpressionItem(ITEM_TYPE.WEEK);
		year = new ExpressionItem(ITEM_TYPE.YEAR);
	}
	
	/**
	 * 从字符串构造表达式对象
	 * 
	 * @param express
	 */
	public void parse(String express){
		String[] splits = express.split(" ");
		if(splits.length < 6){
			throw new UnsupportedExpress(express);
		}
		
		seconds.parse(splits[0]);
		minutes.parse(splits[1]);
		hour.parse(splits[2]);
		day.parse(splits[3]);
		month.parse(splits[4]);
		week.parse(splits[5]);
		
		// year is option
		if(splits.length>6){
			year.parse(splits[6]);
		}
		
	}
	
	/**
	 * 根据对象转换成表达式
	 * 
	 * @return
	 */
	public String toExpress(){
		
		StringBuffer express = new StringBuffer();
		
		// second
		String itemValue = seconds.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(itemValue);
		}
		
		itemValue = minutes.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(" ");
			express.append(itemValue);
		}
		
		itemValue = hour.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(" ");
			express.append(itemValue);
		}
		itemValue = day.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(" ");
			express.append(itemValue);
		}
		itemValue = month.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(" ");
			express.append(itemValue);
		}
		itemValue = week.toExpress();
		if(itemValue !=null && itemValue.length() > 0){
			express.append(" ");
			express.append(itemValue);
		}
		itemValue = year.toExpress();
		express.append(" ");
		if(itemValue !=null && itemValue.length() > 0){
			express.append(itemValue);
		}
		
		return express.toString();
	}

	public ExpressionItem getSeconds() {
		return seconds;
	}

	public void setSeconds(ExpressionItem seconds) {
		this.seconds = seconds;
	}

	public ExpressionItem getMinutes() {
		return minutes;
	}

	public void setMinutes(ExpressionItem minutes) {
		this.minutes = minutes;
	}

	public ExpressionItem getHour() {
		return hour;
	}

	public void setHour(ExpressionItem hour) {
		this.hour = hour;
	}

	public ExpressionItem getDay() {
		return day;
	}

	public void setDay(ExpressionItem day) {
		this.day = day;
	}

	public ExpressionItem getMonth() {
		return month;
	}

	public void setMonth(ExpressionItem month) {
		this.month = month;
	}

	public ExpressionItem getWeek() {
		return week;
	}

	public void setWeek(ExpressionItem week) {
		this.week = week;
	}

	public ExpressionItem getYear() {
		return year;
	}

	public void setYear(ExpressionItem year) {
		this.year = year;
	}
	
	
}
