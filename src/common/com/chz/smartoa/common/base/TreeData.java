package com.chz.smartoa.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeData implements Serializable{
	
	public static String OPEN = "open";
	public static String CLOSED = "closed";
	private static final long serialVersionUID = 1L;
	
	public TreeData() {
		
	}
	
	public TreeData(String id,String text) {
		this.id = id;
		this.text = text;
	}
	
	public TreeData(String id,String text,boolean checked) {
		this.id = id;
		this.text = text;
		this.checked = checked;
	}
	
	private String id;
	private String text;
	//是否关闭：open/closed
	private String state;
	//是否选中：true/false
	private boolean checked;
	//显示图标
	private String iconCls;
	
	private List<Map<String,?>> attributes;
	private List<TreeData> children;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Map<String, ?>> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Map<String, ?>> attributes) {
		this.attributes = attributes;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public List<TreeData> getChildren() {
		return children;
	}
	public void setChildren(List<TreeData> children) {
		this.children = children;
	}
}
