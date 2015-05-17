package com.chz.smartoa.system.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.system.pojo.Post;
import com.chz.smartoa.system.service.PostBiz;



@Controller
public class PostAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(PostAction.class);
	
	private PostBiz postBiz;
	
	private Post pt;
	
	private String msg;
	
	private String jsonStr;
	
	
	
	
	

	public PostBiz getPostBiz() {
		return postBiz;
	}
	public void setPostBiz(PostBiz postBiz) {
		this.postBiz = postBiz;
	}
	
	public Post getPt() {
		return pt;
	}
	public void setPt(Post pt) {
		this.pt = pt;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	
	

}
