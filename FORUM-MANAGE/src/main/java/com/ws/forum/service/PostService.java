package com.ws.forum.service;

import com.ws.forum.pojo.Post;
import com.ws.forum.vo.PageObject;

public interface PostService {

	PageObject<Post> findPageObjects(String keyword, Integer pageCurrent);

	int deleteObjects(Integer[] ids);
	
}
