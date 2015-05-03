package com.chz.smartoa.velocity.template;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.pojo.DictionaryConfig;
import com.chz.smartoa.system.service.DictionaryConfigBiz;

public class OptionTag extends Directive{
	
	private DictionaryConfigBiz dictionaryConfigBiz;
	
	public OptionTag() {
		super();
		dictionaryConfigBiz = (DictionaryConfigBiz) ServerInfo.wac.getBean("dictionaryConfigBiz");
	}

	@Override
	public String getName() {
		return "optionTag";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		int size = node.jjtGetNumChildren();
		//字典KEY
		SimpleNode keyNode = (SimpleNode) node.jjtGetChild(0); 
		String key = (String)keyNode.value(context);    
		//默认值
		String defaultVal = null;
		if(size >= 2 && node.jjtGetChild(1)!=null){
			SimpleNode defaultValNode = (SimpleNode) node.jjtGetChild(1); 
			defaultVal = (String)defaultValNode.value(context);    
		}
		//筛选条件
		Map<String, Object> paramMap = null;
		String filterParams = null;
		if(size >= 3 && node.jjtGetChild(2)!=null){
			SimpleNode filterParamsNode = (SimpleNode) node.jjtGetChild(2); 
			filterParams = (String)filterParamsNode.value(context);  
			paramMap = getParams(filterParams);
		}
		//加载字典
		List<DictionaryConfig> dictionarys = dictionaryConfigBiz.findDictionaryByKey(key);
		StringBuffer option = new StringBuffer();
		if(dictionarys != null && dictionarys.size() > 0)
		for (DictionaryConfig dic:dictionarys) {
			option.append("<option value='").append(dic.getDictionaryKey()).append("'");
			if(StringUtils.isNotEmpty(defaultVal)&&defaultVal.equals(dic.getDictionaryKey())){
				option.append("checked");
			}
			option.append(">").append(dic.getDictionaryName()).append("</option>");
		}
		writer.write(option.toString());  
		return false;
	}
	
	private Map<String, Object> getParams(String filterParams){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(filterParams)){
			String[] strArray = filterParams.split(";");
			for (String param : strArray) {
				int index = param.indexOf("=");
				String key = param.substring(0, index);
				String value = param.substring(index+1);
				paramMap.put(key, value);
			}
		}
		return paramMap;
	}
}
