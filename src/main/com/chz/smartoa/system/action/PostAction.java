package com.chz.smartoa.system.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.pojo.Post;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.PostBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
public class PostAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(PostAction.class);
	
	private PostBiz postBiz;
	
	private OperateLogBiz operateLogBiz;
	
	private List<Post> postList;
	
	private Post pt;
	
	private String msg;
	
	private String jsonStr;
	
	private String postId;
	
	private String postName;
	
	
	public String getAllPost(){
		postList = postBiz.findAllPost();
		dataList = postList;
		return DATA_LIST;
	}
	
	
	public String savePost(){
		try {
			if(StringUtils.isNotEmpty(postId)){
				Post post = postBiz.findPostById(postId);
				if(post != null){
					msg = "exist";
				}else{
					Post insertPost = new Post();
					insertPost.setPostId(postId);
					insertPost.setPostName(postName);
					insertPost.setCreateUser(getLoginStaff().getLoginName());
					postBiz.insertPost(insertPost);
					operateLogBiz.info(OperateLogType.POST_MANAGE, postId,postName, "新岗位定义成功");
					msg ="true";
				}
			}else{
				msg ="postIsNull";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			operateLogBiz.info(OperateLogType.POST_MANAGE, postId,postName, "新岗位定义失败");
			e.printStackTrace();
			msg ="false";
		}
		
		operateResult = new OperateResult(1, msg);
		return OPER_RESULT;
	}
	
	
	

	public PostBiz getPostBiz() {
		return postBiz;
	}
	public void setPostBiz(PostBiz postBiz) {
		this.postBiz = postBiz;
	}
	
	public OperateLogBiz getOperateLogBiz() {
		return operateLogBiz;
	}
	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}

	public List<Post> getPostList() {
		return postList;
	}
	public void setPostList(List<Post> postList) {
		this.postList = postList;
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


	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	
}
