package com.chz.smartoa.system.dao;

import java.util.List;

import com.chz.smartoa.system.pojo.Post;

public interface PostDao {

	public void insertPost(Post pt);
	
	public Post findPostById(String postId);
	
	public List<Post> findAllPost();
	
}
