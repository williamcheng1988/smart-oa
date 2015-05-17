package com.chz.smartoa.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.chz.smartoa.system.dao.PostDao;
import com.chz.smartoa.system.pojo.DepartmentPostStaffs;
import com.chz.smartoa.system.pojo.Post;

public class PostDaoImpl extends SqlMapClientDaoSupport implements PostDao{

	@Override
	public void insertPost(Post pt) {
		getSqlMapClientTemplate().insert("pt_insertPost", pt);
	}

	
	@Override
	public Post findPostById(String postId) {
		
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Post> findAllPost() {
		List<Post> postList = (List<Post>)getSqlMapClientTemplate().queryForList("pt_findAllPost");
		return postList;
	}

}
