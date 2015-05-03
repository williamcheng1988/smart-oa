package com.chz.smartoa.dynamicForm.pojo;

import java.util.ArrayList;
import java.util.List;

public class Section{
	private String type;
	private String tag;
	private String text;
	private String row;
	private String col;
	private List<Merge> merge = new ArrayList<Merge>();
	private List<Field> fields = new ArrayList<Field>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public List<Merge> getMerge() {
		return merge;
	}
	public void setMerge(List<Merge> merge) {
		this.merge = merge;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}