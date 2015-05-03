package com.chz.smartoa.dynamicForm.export;

import javax.servlet.http.HttpServletResponse;

import com.chz.smartoa.dynamicForm.export.exception.ExportErrorException;
import com.chz.smartoa.dynamicForm.pojo.FormTemplate;

public interface Exportor {
	TableModel export(HttpServletResponse response, FormTemplate formTemplate,boolean doReport)
            throws ExportErrorException;
}
