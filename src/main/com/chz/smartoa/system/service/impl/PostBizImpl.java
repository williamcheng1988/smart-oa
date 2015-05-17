package com.chz.smartoa.system.service.impl;

import java.util.List;

import com.chz.smartoa.system.dao.PostDao;
import com.chz.smartoa.system.pojo.Post;
import com.chz.smartoa.system.service.PostBiz;

public class PostBizImpl implements PostBiz{

	private PostDao postDao;
	public PostDao getPostDao() {
		return postDao;
	}
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}


	@Override
	public void insertPost(Post pt) {
		postDao.insertPost(pt);
	}
	
	
	@Override
	public Post findPostById(String postId) {
		return postDao.findPostById(postId);
	}
	
	
	@Override
	public List<Post> findAllPost() {
		return postDao.findAllPost();
	}
	

}
