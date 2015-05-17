package com.chz.smartoa.system.service;

import java.util.List;

import com.chz.smartoa.system.pojo.Post;

public interface PostBiz {
	
	public void insertPost(Post pt);
	
	public Post findPostById(String postId);
	
	public List<Post> findAllPost();
}
