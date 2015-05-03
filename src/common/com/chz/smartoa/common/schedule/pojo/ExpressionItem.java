package com.chz.smartoa.common.schedule.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * . Fix usage.
 *	[*] - used to select all values within a field. 
 *		  For example, "*" in the minute field means "every minute"
 *	[?] - useful when you need to specify something in one of 
 *		  the two fields in which the character is allowed, but not the other  
 */
public class ExpressionItem {
	
	/**
	 * 字段类型定义
	 */
	public enum ITEM_TYPE{
		SECONDS,		// 秒
		MINTURE,		// 分
		HOUR,			// 时
		DAY,			// 日
		MONTH,			// 月
		WEEK,			// 周
		YEAR			// 年
	}
	
	private ITEM_TYPE type;
	
	private List<ExpressionItem> 	subs 	= new ArrayList<ExpressionItem>();
	
	/**
	 * values[0] : the base value or begin value
	 * values[1] : if it is during, it's end value
	 */
	private String[]				values 	= new String[2];
	
	/**
	 * Step if exist.
	 */
	private int						step	= Integer.MAX_VALUE;
	
	/**
	 * The last day only for day field
	 */
	private boolean 				last	= false;
	
	/**
	 * The work day, only for day field
	 */
	private boolean 				work	= false;
	
	/**
	 * The number of order
	 */
	private int						index	= Integer.MAX_VALUE;
	
	public ExpressionItem(ITEM_TYPE type){
		this.type = type;
	}

	public ExpressionItem(ITEM_TYPE type,String value){
		this.type = type;
		parse(value);
	}
	
	public void parse(String express){
		
		// check if has sub items
		String[] items = express.split("[,]");
		if(items.length > 0){
			String _value = items[0];
			
			int beginIndex =0;
			
			boolean isInteral = false;
			boolean isDuring = false;
			boolean isIndex	= false;
			
			for(int i=0;i<_value.length();i++){
				if(_value.charAt(i) == '/'){
					String itemvalue = _value.substring(beginIndex, i);

					if(this.values[0] == null){
						this.values[0] = itemvalue;
					}else if(this.values[1] == null){
						this.values[1] = itemvalue;
					}
					isInteral = true;
					beginIndex = i+1;
				}else if(_value.charAt(i)=='-'){
					
					String itemvalue = _value.substring(beginIndex, i);
					if(this.values[0] == null){
						this.values[0] = itemvalue;
					}else if(this.values[1] == null){
						this.values[1] = itemvalue;
					}

					isDuring = true;
					beginIndex = i+1;
				}else if(_value.charAt(i)=='#'){
					String itemvalue = _value.substring(beginIndex, i);
					if(this.values[0] == null){
						this.values[0] = itemvalue;
					}else if(this.values[1] == null){
						this.values[1] = itemvalue;
					}
					isIndex = true;
					beginIndex = i+1;
				}else if(_value.charAt(i)=='L' && this.type==ITEM_TYPE.WEEK){
					String itemvalue = _value.substring(beginIndex, i);
					if(this.values[0] == null){
						this.values[0] = itemvalue;
					}else if(this.values[1] == null){
						this.values[1] = itemvalue;
					}

					this.last = true;
					beginIndex = i+1;
				}else if(_value.charAt(i)=='W' && this.type==ITEM_TYPE.DAY){
					String itemvalue = _value.substring(beginIndex, i);
					if(this.values[0] == null){
						this.values[0] = itemvalue;
					}else if(this.values[1] == null){
						this.values[1] = itemvalue;
					}
					
					this.work = true;
					beginIndex = i+1;
				}
			}
			
			if( isInteral){
				this.step = Integer.parseInt(_value.substring(beginIndex));
			}else if(isDuring){
				this.values[1] = _value.substring(beginIndex);
			}else if(isIndex){
				this.index = Integer.parseInt(_value.substring(beginIndex));
			}else{
				if(_value.substring(beginIndex).length()>0){
					this.values[0] = _value;
				}
			}
			
		}
		
		// loop values
		for(int idx=1;idx<items.length;idx++){
			ExpressionItem subItem = new ExpressionItem(this.type);
			subItem.parse(items[idx]);
			subs.add(subItem);
		}
		
	}
	
	public String toExpress(){

		if(this.values[0] == null){
			return null;
		}
		
		String express = this.values[0];
		
		if(this.values[1] != null){
			express = express + "-" + this.values[1];
		}
		
		if(this.step != Integer.MAX_VALUE){
			express = express + "/" + String.valueOf(this.step);
		}
		
		if(this.last){
			express = express + "L";
		}

		if(this.work){
			express = express + "W";
		}
		
		if(this.index != Integer.MAX_VALUE){
			express = express + "#" + String.valueOf(this.index);
		}

		for(ExpressionItem sub : subs){
			String subExpress =  sub.toExpress();
			if(subExpress != null){
				express = express + "," + subExpress;
			}
		}
		
		return express;
	}

	public ITEM_TYPE getType() {
		return type;
	}

	public void setType(ITEM_TYPE type) {
		this.type = type;
	}

	public List<ExpressionItem> getSubs() {
		return subs;
	}

	public void setSubs(List<ExpressionItem> subs) {
		this.subs = subs;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
