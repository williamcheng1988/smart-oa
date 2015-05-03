package com.chz.smartoa.dynamicForm.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * FormTemplateJsonContent .
 * 
 * @author William Cheng
 */
public class FormTemplateJsonContent {
	private List<Section> sections = new ArrayList<Section>();
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}