package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.pojo.Post;

public interface PostBiz {
	
	public void insertPost(Post pt);
	
	public List<Post> findAllPost();
}
